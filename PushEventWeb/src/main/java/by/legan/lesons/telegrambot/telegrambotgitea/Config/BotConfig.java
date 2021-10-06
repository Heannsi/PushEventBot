package by.legan.lesons.telegrambot.telegrambotgitea.Config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@Data
@PropertySource("classpath:application.properties")

public class BotConfig {
    @Value("${botUserName}")
    String PushEventBot;

    @Value("${token}")
    String token;

}
