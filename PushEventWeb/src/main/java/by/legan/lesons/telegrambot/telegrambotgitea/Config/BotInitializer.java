package by.legan.lesons.telegrambot.telegrambotgitea.Config;

import by.legan.lesons.telegrambot.telegrambotgitea.Service.Bot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static by.legan.lesons.telegrambot.telegrambotgitea.Utils.exceptionStackTraceToString;

@Component
@Slf4j

public class BotInitializer {
    @Autowired
    Bot bot;

    @EventListener({ContextRefreshedEvent.class})
    public void Init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
           telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            log.error(exceptionStackTraceToString(e));
        }
    }

}
