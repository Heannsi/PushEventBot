package by.legan.lesons.telegrambot.telegrambotgitea.Config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@Data
@PropertySource("classpath:application.properties")

public class BotConfig {
    @Value("${PushEventBot}")
    String PushEventBot;

    @Value("${2005570488:AAHp-SFm9DMNWdvG5XaRJc7NfTkxcNiF3dk}")
    String token;

}
