package by.legan.lesons.telegrambot.telegrambotgitea.Service;

import by.legan.lesons.telegrambot.telegrambotgitea.Config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j

public class Bot extends TelegramLongPollingBot {
    final
    BotConfig config;

    public Bot(BotConfig config) {
        this.config = config;
    }

    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage.SendMessageBuilder builder = SendMessage.builder();
        String messageText;
        String chatId;
        if (update.getMessage() != null) {
            chatId = update.getMessage().getChatId().toString();
            builder.chatId(chatId);
            messageText = update.getMessage().getText();
        } else {
            chatId = update.getChannelPost().getChatId().toString();
            builder.chatId(chatId);
            messageText = update.getChannelPost().getText();
        }
        if (messageText.contains("/Hello")) {
            builder.text("Привет");
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
                log.debug(e.toString());
            }
        }
        if (messageText.contains("/chartId")) {
            builder.text("ID Канала: " + chatId);
            try {
                execute(builder.build());
            } catch (TelegramApiException e) {
                log.debug(e.toString());
            }
        }
    }

    public String getBotUsername () {
        return config.getPushEventBot();
    }

    public String getBotToken () {
       return config.getToken();
    }
}
