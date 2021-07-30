package app;

import listeners.GoliraListener;
import listeners.RollListener;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import util.Config;

import java.io.IOException;

import static util.Config.API_TOKEN;

public class DiscordBotApp {
    public static void main(String[] args) throws IOException {
        Config config = new Config(args[0]);

        DiscordApi api = new DiscordApiBuilder().setToken(config.getStringRequired(API_TOKEN)).login().join();
        api.addListener(new GoliraListener());
        api.addListener(new RollListener());
    }
}
