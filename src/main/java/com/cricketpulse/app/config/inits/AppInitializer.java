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
                "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/profilePics%2Fadmin%2Fadminuser.png?alt=media&token=9bb9ee2d-f870-4aea-8dd1-3ad83b6d21c8",// specialize
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
                        new Court(1L, "Court-A", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_1.png?alt=media&token=deea385d-4676-4ce6-8746-ed7a64e69576", "Indoor"),
                        new Court(2L, "Court-B", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_2.png?alt=media&token=1b63ead4-d523-4ebf-ad27-fdfd4e1cb053", "Indoor"),
                        new Court(3L, "Court-C", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_3.png?alt=media&token=d43b144e-0937-4382-b35d-9b24a644514e", "Indoor"),
                        new Court(4L, "Court-D", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_4.png?alt=media&token=848c2814-795e-4040-8a96-50a963da95c2", "Indoor"),
                        new Court(5L, "Court-E", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_5.png?alt=media&token=2b624c62-be7f-4787-9a4a-cfde9de39068", "Outdoor"),
                        new Court(6L, "Court-F", "https://firebasestorage.googleapis.com/v0/b/restarantappfilerepo.appspot.com/o/courts%2Fcourt_6.png?alt=media&token=fa395d99-6b4d-4a9c-b668-3a059dc282f4", "Outdoor")
                );
                courtRepository.saveAll(courts);
                System.out.println("Courts initialized");
            }

    }
}