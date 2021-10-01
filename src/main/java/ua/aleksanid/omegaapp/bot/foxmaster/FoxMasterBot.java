package ua.aleksanid.omegaapp.bot.foxmaster;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.aleksanid.omegaapp.daos.SubscriptionDAO;
import ua.aleksanid.omegaapp.entities.Giveaway;
import ua.aleksanid.omegaapp.entities.Subscription;
import ua.aleksanid.omegaapp.parser.giveaway.GiveawayParser;

import java.io.IOException;
import java.util.List;

@Service
public class FoxMasterBot extends TelegramBot {

    private static final Logger logger = LoggerFactory.getLogger(FoxMasterBot.class);

    private final GiveawayParser giveawayParser;
    private final SubscriptionDAO subscriptionDAO;

    public FoxMasterBot(@Value("${bot.token}") String botToken, GiveawayParser giveawayParser, SubscriptionDAO subscriptionDAO) {
        super(botToken);
        this.subscriptionDAO = subscriptionDAO;
        this.setUpdatesListener(new UpdatesListenerImpl());
        this.giveawayParser = giveawayParser;
    }


    public void sendMessage(String message, Long userId) {
        SendMessage sendMessageRequest = new SendMessage(userId, message);
        sendMessageRequest.parseMode(ParseMode.HTML);

        BaseResponse baseResponse = this.execute(sendMessageRequest);
        logger.info(baseResponse.toString());
    }

    public void executeCommand(FoxBotCommand foxBotCommand, Long userId) {
        switch (foxBotCommand) {
            case GIVEAWAYS:
                sendGiveawayList(userId);
                break;
            case SUBSCRIBE:
                subscribeToNewGiveaways(userId);
                break;
            case UNSUBSCRIBE:
                unsubscribeFromNewGiveaways(userId);
                break;
        }
    }


    private void subscribeToNewGiveaways(Long userId) {
        subscriptionDAO.save(new Subscription(userId));
        sendMessage("You are now subscribed to giveaway feed", userId);
    }

    private void unsubscribeFromNewGiveaways(Long userId) {
        subscriptionDAO.deleteById(userId);
        sendMessage("You are now unsubscribed from giveaway feed", userId);
    }

    private void sendGiveawayList(Long receiverId) {
        try {
            List<Giveaway> giveaways = giveawayParser.getGiveawayList();

            StringBuilder message = new StringBuilder("Active giveaways:\n");

            for (Giveaway giveaway : giveaways) {
                giveawayToMessage(message, giveaway);
            }

            sendMessage(message.toString(), receiverId);

        } catch (IOException e) {
            logger.error("Error while parsing", e);
            sendMessage("Cant get list of giveaways.", receiverId);
        }
    }

    private void giveawayToMessage(StringBuilder message, Giveaway giveaway) {
        message.append("<a href=\"").append(giveaway.getLink()).append("\">Link</a>|").append(giveaway.getName()).append("\n");
    }


    private FoxBotCommand toCommand(String text) {
        for (FoxBotCommand foxBotCommand : FoxBotCommand.values()) {
            if (text.contains(foxBotCommand.getCommand())) {
                return foxBotCommand;
            }
        }
        return null;
    }

    @SneakyThrows
    public void sendNewGiveaways(List<Giveaway> newGiveaways) {
        List<Subscription> subscriptions = subscriptionDAO.findAll();

        StringBuilder message = new StringBuilder("New giveaways:\n");

        for (Giveaway giveaway : newGiveaways) {
            giveawayToMessage(message, giveaway);
        }

        for (Subscription subscription : subscriptions) {
            sendMessage(message.toString(), subscription.getUserId());
            Thread.sleep(100);
        }
    }

    private class UpdatesListenerImpl implements UpdatesListener {
        @Override
        public int process(List<Update> updates) {
            for (Update update : updates) {
                logger.info(update.toString());
                if (update.message().text() != null) {
                    FoxBotCommand foxBotCommand = toCommand(update.message().text());

                    if (foxBotCommand != null) {
                        logger.info("Received command: " + foxBotCommand);
                        executeCommand(foxBotCommand, update.message().from().id());
                    }
                }
            }
            return CONFIRMED_UPDATES_ALL;
        }
    }

}
