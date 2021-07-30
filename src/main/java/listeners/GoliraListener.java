package listeners;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import util.Commands;

import java.io.File;

public class GoliraListener implements MessageCreateListener {

    private static final String goliraPath = "golira.jpg";

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        if (Commands.GOLIRA.equalsIgnoreCase(messageCreateEvent.getMessage().getContent().strip())) {
            messageCreateEvent.getChannel().sendMessage(new File(goliraPath));
        }
    }
}
