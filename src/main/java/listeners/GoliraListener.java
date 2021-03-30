package listeners;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import util.Commands;

import java.io.File;

public class GoliraListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        Message message = messageCreateEvent.getMessage();
        if (message.getContent().equalsIgnoreCase(Commands.PREFIX + Commands.GOLIRA)) {
            messageCreateEvent.getChannel().sendMessage(new File("golira.jpg"));
        }
    }
}
