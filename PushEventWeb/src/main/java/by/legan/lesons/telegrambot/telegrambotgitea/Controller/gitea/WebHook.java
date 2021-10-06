package by.legan.lesons.telegrambot.telegrambotgitea.Controller.gitea;

import by.legan.lesons.telegrambot.telegrambotgitea.Model.Commit;
import by.legan.lesons.telegrambot.telegrambotgitea.Model.GiteaWebHook;
import by.legan.lesons.telegrambot.telegrambotgitea.Service.Bot;
import by.legan.lesons.telegrambot.telegrambotgitea.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@RestController
@RequestMapping("/api/public/gitea")
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")


public class WebHook {
    Bot bot;

    @Value("${chartId}")
    String chartId;

    @Value("${secret}")
    String secret;

    @Autowired
    public WebHook(Bot bot) {
        this.bot = bot;
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<?> webhook (@RequestBody String json) {
        Gson gson = new Gson();
        GiteaWebHook giteaWebHook = null;
        try {
            giteaWebHook = gson.fromJson(json, GiteaWebHook.class);
        } catch (JsonSyntaxException e) {
            log.error(Utils.exceptionStackTraceToString(e));
            return new ResponseEntity<>(Utils.exceptionStackTraceToString(e), HttpStatus.BAD_REQUEST);
        }
        if (validationWebHookContent(giteaWebHook)) {
            SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();
            messageBuilder.chatId(chartId);

            messageBuilder.parseMode(ParseMode.HTML);
            StringBuilder builder = new StringBuilder();
            builder.append("<b>Проект</b> : " + giteaWebHook.getRepository().getName()+"\n");
            for (Commit commit : giteaWebHook.getCommits()) {
                builder.append("<b>Автор</b> : " + commit.getAuthor().getName()+"\n");
                builder.append("<b>Комментарий</b> : " + commit.getMessage()+"\n");
            }
            builder.append("<a href=\"https://play.google.com/store/apps/details?id=URL_ВАШЕГО_ПРИЛОЖЕНИЯ\">Обновление будет доступно в Play Market через пару минут</a>\n");
            messageBuilder.text(buildToCorrectString(builder));
            try {
                bot.execute(messageBuilder.build());
            } catch (TelegramApiException e) {
                log.error(Utils.exceptionStackTraceToString(e));
                return new ResponseEntity<>(Utils.exceptionStackTraceToString(e), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    private boolean validationWebHookContent(GiteaWebHook giteaWebHook) {
        return giteaWebHook != null &&
                giteaWebHook.getRef().contains(giteaWebHook.getRepository().getDefaultBranch()) &&
                giteaWebHook.getSecret().equals(secret);
    }

    private String buildToCorrectString (StringBuilder builder) {
        return builder.toString()
                .replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("`", "\\`")
                .replace("&nbsp", " ")
                .replace("&frac", " ")
                .replaceAll("\\u003c", "");
    }
}
