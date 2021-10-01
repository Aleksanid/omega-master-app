package ua.aleksanid.omegaapp.parser.giveaway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ua.aleksanid.omegaapp.entities.Giveaway;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiveawayParser {
    private static final Logger logger = LoggerFactory.getLogger(GiveawayParser.class);

    private GiveawayParser() throws IOException {
        this.getGiveawayList();
    }

    public List<Giveaway> getGiveawayList() throws IOException {
        List<Giveaway> giveaways = new ArrayList<>();
        Document document = Jsoup.connect("https://freesteam.ru/category/active/").get();

        document.select(".post-thumb").forEach((element)->{
            String name = element.select(".entry-title").select("a").text();
            String link = element.select(".entry-title").select("a").attr("href");

            Giveaway giveaway = new Giveaway(name,link);
            giveaways.add(giveaway);
        });
        return giveaways;
    }
}
