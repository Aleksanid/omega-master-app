package ua.aleksanid.omegaapp.scheduled;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.aleksanid.omegaapp.bot.foxmaster.FoxMasterBot;
import ua.aleksanid.omegaapp.daos.GiveawayDAO;
import ua.aleksanid.omegaapp.entities.Giveaway;
import ua.aleksanid.omegaapp.parser.giveaway.GiveawayParser;
import ua.aleksanid.omegaapp.security.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class GiveawayUpdater {
    private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
    private final GiveawayParser giveawayParser;
    private final GiveawayDAO giveawayDAO;
    private final FoxMasterBot foxMasterBot;

    public GiveawayUpdater(GiveawayParser giveawayParser, GiveawayDAO giveawayDAO, FoxMasterBot foxMasterBot) {
        this.giveawayParser = giveawayParser;
        this.giveawayDAO = giveawayDAO;
        this.foxMasterBot = foxMasterBot;
    }

    @Scheduled(fixedDelay = 30000L)
    private void updateGiveaways() {
        try {
            List<Giveaway> newGiveaways = new ArrayList<>();
            List<Giveaway> giveaways = giveawayParser.getGiveawayList();

            for (Giveaway giveaway : giveaways) {
                if (!giveawayDAO.existsByName(giveaway.getName())) {
                    giveawayDAO.save(giveaway);
                    newGiveaways.add(giveaway);
                }
            }

            if (newGiveaways.size() > 0) {
                foxMasterBot.sendNewGiveaways(newGiveaways);
            }

        } catch (IOException ioException) {
            logger.error("Cant update giveaways due to error", ioException);
        }
    }
}
