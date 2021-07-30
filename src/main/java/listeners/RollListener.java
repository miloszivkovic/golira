package listeners;

import exceptions.GoliraException;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import util.Commands;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class RollListener implements MessageCreateListener {

    protected static final int MIN_VAL = 1;
    protected static final int MAX_DEFAULT_VAL = 100;
    protected static final int MAX_VAL = 10_000;

    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        String messageContent = messageCreateEvent.getMessage().getContent().strip();

        Optional<User> optionalUser = messageCreateEvent.getMessageAuthor().asUser();
        String mention = optionalUser.isPresent() ? optionalUser.get().getMentionTag() : messageCreateEvent.getMessageAuthor().getDisplayName();

        if (messageContent.startsWith(Commands.ROLL)) {
            try {
                messageCreateEvent.getChannel().sendMessage(String.format("%s %s", mention, parseRollCommand(messageContent).toString()));
            } catch (GoliraException e) {
                messageCreateEvent.getChannel().sendMessage(String.format("%s %s", mention, e.getMessage()));
            }
        }
    }

    /**
     * Valid roll command is in one of the following formats:
     * 1. -roll
     * 2. -roll X Y
     *
     * 1. returns random integer between 1 and 100
     * 2. returns random integer between X and Y, where X and Y must be between 1 and 10_000
     */
    private Integer parseRollCommand(String messageContent) {

        String[] tokens = messageContent.split(" ");

        if (tokens.length == 1) {
            return ThreadLocalRandom.current().nextInt(MIN_VAL, MAX_DEFAULT_VAL + 1);
        }

        if (tokens.length == 3) {
            int x, y;

            try {
                x = Integer.parseInt(tokens[1]);
                y = Integer.parseInt(tokens[2]);
            } catch (NumberFormatException e) {
                throw new GoliraException("Invalid roll input, type " + Commands.HELP + " for help");
            }

            if (x < MIN_VAL || x > MAX_VAL || y < MIN_VAL || y > MAX_VAL) {
                throw new GoliraException("Numbers allowed: 1 - 10000");
            }

            if (x > y) {
                throw new GoliraException("Second value must be greater than or equal to first");
            }

            return ThreadLocalRandom.current().nextInt(x, y + 1);
        }

        throw new GoliraException("Invalid roll input, type " + Commands.HELP + " for help");
    }
}
