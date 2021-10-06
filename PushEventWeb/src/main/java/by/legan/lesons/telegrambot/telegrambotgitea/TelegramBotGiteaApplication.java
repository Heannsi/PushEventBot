package by.legan.lesons.telegrambot.telegrambotgitea;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@AutoConfigurationPackage

public class TelegramBotGiteaApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TelegramBotGiteaApplication.class)
                .run(args);
    }
}
