package uz.pdp.hotel_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HotelManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelManagementSystemApplication.class, args);
    }

}

// auth user birinchi yaratiladi
// hotel ikkinchi yaratiladi
// room uchinchi yaratiladi
// payment to'rtinchi yaratiladi
// order beshinchi yaratiladi

// username => abbos, password => 2210
// username => husanboy, password => 2802