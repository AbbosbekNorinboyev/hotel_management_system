package uz.pdp.hotel_management_system.cron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class LogCleanup {
    private static final String LOG_PATH = "C:\\Abbos\\Spring Project\\Test Projects\\HotelManagementSystem\\logs\\HotelManagementSystem.log";

    @Scheduled(cron = "* */10 * * * * ") // har 10 minutda ishlaydi
    public void clearLogFile() {
        File file = new File(LOG_PATH);
        if (file.exists()) {
            try {
                Files.write(file.toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                log.info("✅ Log fayli tozalandi: " + LOG_PATH);
                System.out.println("clearLogFile ishlamoqda...");
            } catch (IOException e) {
                log.error("❌ Log faylni tozalashda xatolik: " + e.getMessage());
            }
        } else {
            log.warn("⚠ Log fayl topilmadi: " + LOG_PATH);
        }
    }
}
