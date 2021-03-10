package com.AITU;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

import static com.AITU.Status.CHOSEN_GROUP;
import static com.AITU.Status.NONE;
import static com.AITU.Status.WORKING;
import static com.AITU.StringUtils.convertToEngLetters;
import static com.AITU.StringUtils.convertToSlavicLetters;

public class ScheduleBot extends TelegramLongPollingBot {

    private Status status = Status.NONE;
    private String groups = "";

    @Override
    public String getBotToken() {
        return "1639554980:AAEI62xjcplt8MatBOtrDdd4s6LXv9CYOcs";
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();
                if (inMessage.getText().equals("/help")){
                    sendMsg(inMessage, "/start or /schedule to get started" +
                            "\n/stop or /exit to finish." +
                            "\n developed by Asel Shora, a student of the IT-2004 group.");
                    return;
                }
                if (inMessage.getText().equals("/schedule") || inMessage.getText().equals("/start")) {
                    sendMsg(inMessage, "Specify your course:");
                    System.out.println(update.getMessage().getFrom() + " | starting session");
                    status = WORKING;
                    return;
                } else if (inMessage.getText().equals("/stop") || inMessage.getText().equals("/exit")) {
                    status = NONE;
                    System.out.println(update.getMessage().getFrom() + " | stopping session");
                    return;
                } else if (status == WORKING) {
                    String kurs = inMessage.getText();
                    System.out.println(update.getMessage().getFrom() + " | " + "kurs: " + kurs);
                    groups = ExcelWorker.chooseGroupToDisplay(kurs);
                    groups = convertToEngLetters(groups);
                    sendMsg(inMessage, groups);
                    status = CHOSEN_GROUP;
                    return;
                } if (status == CHOSEN_GROUP){
                    String group = inMessage.getText();

                    group = group.replace('/', ' ');
                    group = group.trim();
                    System.out.println(update.getMessage().getFrom() + " | " + "group: " + group);

                    if (!groups.contains(group)) {
                        sendMsg(inMessage, "A non-existent group has been selected, or you have made a mistake.");
                        status = Status.NONE;
                        return;
                    }

                    String resp = "Schedule: ";
                    group = convertToSlavicLetters(group);
                    System.out.println(update.getMessage().getFrom() + " | " + "group converted: " + group);
                    String scheduleInfo = ExcelWorker.getRowsByGroupName(group);
                    resp+= "\n" + scheduleInfo.trim();
                    sendMsg(inMessage, resp);
                    status = Status.NONE;
                    return;
                } else {
                    status = Status.NONE;
                }
            }
        } catch (TelegramApiException | IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(Message msg, String text) throws TelegramApiException {
        SendMessage outMessage = new SendMessage();
        outMessage.setChatId(msg.getChatId());
        outMessage.setText(text);
        execute(outMessage);
    }

    @Override
    public String getBotUsername() {
        return "AITUSchedule_Bot";
    }
}
