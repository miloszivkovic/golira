package listeners;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.mockito.ArgumentCaptor;
import org.testng.Assert;
import org.testng.annotations.Test;
import util.Commands;

import java.util.Optional;

import static org.mockito.Mockito.*;

@Test(singleThreaded = true)
public class RollListenerTest {

    private final RollListener rollListener = spy(new RollListener());

    // TODO: extract repeated code from test methods
    @Test
    public void givenNoIntegersWhenRollThenReturnRandomNumberBetweenMinAndMaxAndMentionUser() {
        //given
        Message message = mock(Message.class);
        when(message.getContent()).thenReturn(Commands.ROLL);

        User user = mock(User.class);
        String userMentionTag = "user-mention-tag";
        when(user.getMentionTag()).thenReturn(userMentionTag);

        MessageAuthor author = mock(MessageAuthor.class);
        when(author.asUser()).thenReturn(Optional.of(user));

        TextChannel channel = spy(mock(TextChannel.class));

        MessageCreateEvent event = mock(MessageCreateEvent.class);
        when(event.getMessage()).thenReturn(message);
        when(event.getMessageAuthor()).thenReturn(author);
        when(event.getChannel()).thenReturn(channel);

        // when
        rollListener.onMessageCreate(event);

        // then
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        verify(channel).sendMessage(argument.capture());
        String messageContent = argument.getValue();

        Assert.assertNotNull(messageContent);

        String[] tokens = messageContent.split(" ");
        Assert.assertEquals(tokens.length, 2);
        Assert.assertEquals(tokens[0], userMentionTag);

        int retVal = Integer.parseInt(tokens[1]);
        Assert.assertTrue(retVal >= RollListener.MIN_VAL && retVal <= RollListener.MAX_DEFAULT_VAL);
    }

    // TODO: implement these tests, at least one using Bot instead of User as message sender

    // givenFirstValLessThanMinWhenRollThenReturnExpectedErrorMessage

    // givenSecondValLessThanMinWhenRollThenReturnExpectedErrorMessage

    // givenFirstValMoreThanMaxWhenRollThenReturnExpectedErrorMessage

    // givenSecondtValMoreThanMaxWhenRollThenReturnExpectedErrorMessage

    // givenUnparsableRollCommandWhenRollThenReturnExpectedErrorMessage

}
