package com.microservice.userService.studentJPA;

import com.microservice.userService.response.Response;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentControllerJpa {
    private final StudentServiceJpa studentService;

    public StudentControllerJpa(StudentServiceJpa studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public Response create(@RequestBody StudentJpa student)
    {
        return studentService.createStudent(student);
    }

    @GetMapping("/getById/{id}")
    public Response getById(@PathVariable("id") Integer id)
    {
        return studentService.getById(id);
    }

    @GetMapping("getAll")
    public Response getAll()
    {
        return studentService.getAll();
    }

    @PutMapping("update-dept/{id}")
    public Response updateDept(@PathVariable("id") Integer id, @RequestParam("dept") String dept) throws InterruptedException {
        return studentService.update(id,dept);
    }
}
