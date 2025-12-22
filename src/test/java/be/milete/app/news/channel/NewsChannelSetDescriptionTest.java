package be.milete.app.news.channel;

import be.milete.app.news.channel.NewsChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewsChannelSetDescriptionTest {

    private NewsChannel newsChannel;

    @BeforeEach
    void createNewsChannel(){
        newsChannel = new NewsChannel("oldName", "oldDescription");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankDescriptionThrowsIllegalArgumentException(String description){
        Executable doCreate = () -> newsChannel.setDescription(description);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("description must not be blank", exception.getMessage());
    }

    @Test
    void givenDescriptionOf501CharactersThrowsIllegalArgumentException(){
        Executable doCreate = () -> newsChannel.setDescription(repeat("d",501));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("description must be max 500 characters", exception.getMessage());
    }

    @Test
    void givenDescriptionOf500CharactersUpdatesDescription(){
        String newDescription = repeat("n", 500);

        newsChannel.setDescription(newDescription);

        assertEquals(newDescription, newsChannel.getDescription());
    }

    @Test
    void trimsDescription(){
        newsChannel.setDescription("\n\t  newDescription \t");

        assertEquals("newDescription", newsChannel.getDescription());
    }
}
