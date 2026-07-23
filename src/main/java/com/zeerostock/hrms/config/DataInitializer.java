package com.zeerostock.hrms.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zeerostock.hrms.model.Role;
import com.zeerostock.hrms.model.User;
import com.zeerostock.hrms.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!userRepository.existsByEmail("hr@zeerostock.com")) {
            User hr = new User();
            hr.setName("HR Manager");
            hr.setEmail("hr@zeerostock.com");
            hr.setPassword(encoder.encode("admin123"));
            hr.setPhone("9876543210");
            hr.setDepartment("Human Resources");
            hr.setDesignation("HR Manager");
            hr.setJoiningDate(LocalDate.now());
            hr.setRole(Role.ROLE_HR);
            userRepository.save(hr);
            // ADD THIS LINE
            System.out.println(">>> ✅ Live HR account created: hr@zeerostock.com / admin123");
        }

        if (!userRepository.existsByEmail("john.doe@zeerostock.com")) {
            User emp = new User();
            emp.setName("John Doe");
            emp.setEmail("john.doe@zeerostock.com");
            emp.setPassword(encoder.encode("user123"));
            emp.setPhone("9876543211");
            emp.setDepartment("Engineering");
            emp.setDesignation("Software Engineer");
            emp.setJoiningDate(LocalDate.now());
            emp.setRole(Role.ROLE_EMPLOYEE);
            userRepository.save(emp);
            // ADD THIS LINE
            System.out.println(">>> ✅ Live Employee account created: john.doe@zeerostock.com / user123");
        }
    }
}