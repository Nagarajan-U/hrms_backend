package com.zeerostock.hrms.config;

import com.zeerostock.hrms.model.User; // Ensure this matches your User model import
import com.zeerostock.hrms.repository.UserRepository; // Ensure this matches your UserRepository import
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed HR Account if not present
        if (!userRepository.existsByEmail("hr@zeerostock.com")) {
            User hr = new User();
            hr.setEmail("hr@zeerostock.com");
            hr.setPassword(passwordEncoder.encode("admin123"));
            hr.setName("ROLE_HR");
            // Set any other required fields (e.g., hr.setName("HR Manager"))
            userRepository.save(hr);
            System.out.println(">>> Default HR user seeded!");
        }

        // Seed Employee Account if not present
        if (!userRepository.existsByEmail("john.doe@zeerostock.com")) {
            User employee = new User();
            employee.setEmail("john.doe@zeerostock.com");
            employee.setPassword(passwordEncoder.encode("user123"));
            employee.setName("ROLE_EMPLOYEE");
            userRepository.save(employee);
            System.out.println(">>> Default Employee user seeded!");
        }
    }
}