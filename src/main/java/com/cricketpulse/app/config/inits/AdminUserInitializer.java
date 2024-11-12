package com.cricketpulse.app.config.inits;
import com.cricketpulse.app.dto.UserDTO;
import com.cricketpulse.app.enums.ROLE;
import com.cricketpulse.app.exception.UserAlreadyExistsException;
import com.cricketpulse.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.Builder;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/12/2024
 */


@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserService userService;

    public AdminUserInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        UserDTO adminUser = new UserDTO(
                "Admin",
                "User",
                "admin@gmail.com",
                "admin@123",
                ROLE.ADMIN,
                null, // phoneNumber
                null, // address
                null, // gender
                null, // nic
                null, // dob
                null  // profilePic
        );

        try {
            userService.createAdmin(adminUser);
        } catch (UserAlreadyExistsException e) {
            // Log the exception and continue
            System.err.println("Admin user already exists: " + e.getMessage());
        }
    }
}