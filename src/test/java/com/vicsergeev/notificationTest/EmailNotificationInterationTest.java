package com.vicsergeev.notificationTest;

import com.vicsergeev.notification.dto.UserEventDTO;
import com.vicsergeev.notification.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.TestPropertySource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = com.vicsergeev.notification.NotificationServiceApplication.class)
@EmbeddedKafka(topics = "user-events")
@TestPropertySource(properties = {
    "spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer",
    "spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer"
})
public class EmailNotificationInterationTest {
    @Autowired
    private KafkaTemplate<String, UserEventDTO> kafkaTemplate;

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender emailSender;
    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public JavaMailSender mockMailSender() {
            return Mockito.mock(JavaMailSender.class);
        }
    }

    @Test
    void sendEmailOnUserCreate() throws InterruptedException {
        UserEventDTO event = new UserEventDTO("CREATE", "test@test.ru", "test user");
        kafkaTemplate.send("user-events", event);

        Thread.sleep(2000);

        verify(emailSender).send(any(SimpleMailMessage.class));
    }
}
