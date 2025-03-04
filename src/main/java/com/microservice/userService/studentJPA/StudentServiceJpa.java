package com.microservice.userService.studentJPA;

import com.microservice.userService.exception.InvalidException;
import com.microservice.userService.exception.NotFoundException;
import com.microservice.userService.response.Response;
import com.microservice.userService.response.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceJpa {
    private final StudentRepositoryJpa studentRepository;

    public StudentServiceJpa(StudentRepositoryJpa studentRepository) {
        this.studentRepository = studentRepository;
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

}
