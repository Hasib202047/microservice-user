package com.microservice.userService.studentJPA;

import com.microservice.userService.exception.InvalidException;
import com.microservice.userService.exception.NotFoundException;
import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockTimeoutException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class StudentServiceJpa {
    private final StudentRepositoryJpa studentRepository;
    private final EntityManager entityManager;

    public StudentServiceJpa(StudentRepositoryJpa studentRepository, EntityManager entityManager) {
        this.studentRepository = studentRepository;
        this.entityManager = entityManager;
    }

    public Response createStudent(StudentJpa student)
    {
        if(student.getStudentName().isEmpty())
        {
            throw new InvalidException("Must input Student Name");
        }
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Created",studentRepository.save(student));
    }

    public Response getById(Integer id) {
        return studentRepository.findById(id)
                .map(studentJpa -> ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Found", studentJpa))
                .orElseThrow(() -> new NotFoundException("Student with ID " + id + " not found"));
    }


    public Response getAll() {
        return ResponseBuilder.getSuccessResponse(HttpStatus.OK, "Fetched successfully", studentRepository.findAll());
    }

    @Retryable(
            retryFor = { PessimisticLockingFailureException.class, LockTimeoutException.class, SQLException.class },//correct exception is important otherwise no retry will happen
            maxAttempts = 4, // try up to 4 times
            backoff = @Backoff(delay = 1000) // wait 1 second between tries
    )
    @Transactional
    public Response update(Integer id, String deptName) throws InterruptedException {
        entityManager.createNativeQuery("SET LOCAL lock_timeout = '10000ms';").executeUpdate();//locking database for 10000ms for postgresql. but for other no need to do that
        Optional<StudentJpa>studentJpa = studentRepository.findByStudentId(id);
        if(studentJpa.isPresent())
        {
            Thread.sleep(30000);
            studentJpa.get().setDeptName(deptName);
            return ResponseBuilder.getSuccessResponse(HttpStatus.OK,"Updated",studentRepository.save(studentJpa.get()));
        }else
        {
            return ResponseBuilder.getSuccessResponse(HttpStatus.NOT_FOUND,"student not found",null);
        }
    }

}
