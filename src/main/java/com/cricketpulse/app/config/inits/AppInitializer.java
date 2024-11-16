package com.cricketpulse.app.config.inits;
import com.cricketpulse.app.dto.UserDTO;
import com.cricketpulse.app.entity.Court;
import com.cricketpulse.app.enums.ROLE;
import com.cricketpulse.app.exception.UserAlreadyExistsException;
import com.cricketpulse.app.repository.CourtRepository;
import com.cricketpulse.app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author : Kanchana Kalansooriya
 * @since 11/12/2024
 */


@Component
public class AppInitializer implements CommandLineRunner {

    private final UserService userService;
    private final CourtRepository courtRepository;

    public AppInitializer(UserService userService , CourtRepository courtRepository) {
        this.userService = userService;
        this.courtRepository=courtRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeAdminUsers();
        initializeCourts();

    }

    private void initializeAdminUsers() {
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
                null,// specialize
                null// profilePic
        );

        try {
            userService.createAdmin(adminUser);
        } catch (UserAlreadyExistsException e) {
            // Log the exception and continue
            System.err.println("Admin user already exists: " + e.getMessage());
        }
    }

    private void initializeCourts(){
            if (courtRepository.count() == 0) {
                List<Court> courts = Arrays.asList(
                        new Court(1L, "Court A", "https://th.bing.com/th/id/OIP.4N1xA47wpG_Xwe7dpecMNAHaFj?rs=1&pid=ImgDetMain", "indoor"),
                        new Court(2L, "Court B", "https://th.bing.com/th/id/OIP.9cvDj6uwhnnvnbyLzfhECwHaFj?w=1280&h=960&rs=1&pid=ImgDetMain", "outdoor"),
                        new Court(3L, "Court C", "https://th.bing.com/th/id/OIP.9cvDj6uwhnnvnbyLzfhECwHaFj?w=1280&h=960&rs=1&pid=ImgDetMain", "indoor"),
                        new Court(4L, "Court D", "https://th.bing.com/th/id/OIP.9cvDj6uwhnnvnbyLzfhECwHaFj?w=1280&h=960&rs=1&pid=ImgDetMain", "outdoor"),
                        new Court(5L, "Court E", "https://th.bing.com/th/id/OIP.9cvDj6uwhnnvnbyLzfhECwHaFj?w=1280&h=960&rs=1&pid=ImgDetMain", "indoor"),
                        new Court(6L, "Court F", "https://th.bing.com/th/id/OIP.9cvDj6uwhnnvnbyLzfhECwHaFj?w=1280&h=960&rs=1&pid=ImgDetMain", "outdoor")
                );
                courtRepository.saveAll(courts);
                System.out.println("Courts initialized");
            }

    }
}