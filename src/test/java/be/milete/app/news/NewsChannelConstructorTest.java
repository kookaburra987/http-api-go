package be.milete.app.news;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewsChannelConstructorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankNameThrowsIllegalArgumentException(String name){
        Executable doCreate = () -> new NewsChannel(name, "d");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("name must not be blank", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankDescriptionThrowsIllegalArgumentException(String description){
        Executable doCreate = () -> new NewsChannel("n", description);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("description must not be blank", exception.getMessage());
    }

    @Test
    void givenNameOf21CharactersThrowsIllegalArgumentException(){
        Executable doCreate = () -> new NewsChannel(repeat("n",21), "d");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("name must be max 20 characters", exception.getMessage());
    }

    @Test
    void givenDescriptionOf501CharactersThrowsIllegalArgumentException(){
        Executable doCreate = () -> new NewsChannel("n", repeat("d",501));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("description must be max 500 characters", exception.getMessage());
    }

    @Test
    void givenNameOf20CharactersAndDescriptionOf500CharactersCreatesNewsChannel(){
        String name = repeat("n", 20);
        String description = repeat("d", 500);

        NewsChannel newsChannel = new NewsChannel(name, description);

        assertEquals(name, newsChannel.getName());
        assertEquals(description, newsChannel.getDescription());
    }

    @Test
    void trimsNameAndDescription(){
        NewsChannel newsChannel = new NewsChannel(" theName\t\n", "\n\ttheDescription  ");

        assertEquals("theName", newsChannel.getName());
        assertEquals("theDescription", newsChannel.getDescription());
    }
}
