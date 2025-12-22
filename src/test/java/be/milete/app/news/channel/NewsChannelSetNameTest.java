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

class NewsChannelSetNameTest {

    private NewsChannel newsChannel;

    @BeforeEach
    void createNewsChannel(){
        newsChannel = new NewsChannel("oldName", "oldDescription");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankNameThrowsIllegalArgumentException(String name){
        Executable doSetName = () -> newsChannel.setName(name);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doSetName);

        assertEquals("name must not be blank", exception.getMessage());
    }

    @Test
    void givenNameOf21CharactersThrowsIllegalArgumentException(){
        Executable doSetName = () -> newsChannel.setName(repeat("n",21));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doSetName);

        assertEquals("name must be max 20 characters", exception.getMessage());
    }

    @Test
    void givenNewNameOf20CharactersUpdatesName(){
        String newName = repeat("n", 20);

        newsChannel.setName(newName);

        assertEquals(newName, newsChannel.getName());
    }

    @Test
    void trimsTheNewName(){
        newsChannel.setName(" \t\nnewName  \t\n");

        assertEquals("newName", newsChannel.getName());
    }
}
