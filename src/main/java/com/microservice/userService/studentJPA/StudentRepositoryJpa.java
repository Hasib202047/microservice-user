package com.microservice.userService.studentJPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StudentRepositoryJpa extends JpaRepository<StudentJpa,Integer> {

}
