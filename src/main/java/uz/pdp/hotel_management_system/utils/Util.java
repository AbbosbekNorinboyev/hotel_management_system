package uz.pdp.hotel_management_system.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.hotel_management_system.entity.Attachment;
import uz.pdp.hotel_management_system.exception.CustomizedRequestException;
import uz.pdp.hotel_management_system.repository.AttachmentRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Slf4j
public class Util {
    public static String convertObjectToJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new CustomizedRequestException("Error while converting object to string: " + e.getMessage(), 2, 400);
        }
        return jsonString;
    }

    public static String localDateTimeFormatter(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss.SS");

        return localDateTime
                .atZone(ZoneId.of("UTC+5"))
                .toLocalDateTime()
                .format(dateTimeFormatter);
    }

    public static Attachment getUrl(MultipartFile file, AttachmentRepository attachmentRepository) {
        try {
            if (file != null && !file.isEmpty()) {
                Attachment attachment = new Attachment();
                byte[] bytes = file.getBytes();
                String url = Base64.getEncoder().encodeToString(bytes);
                attachment.setUrl(url);
                return attachmentRepository.saveAndFlush(attachment);
            }
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
