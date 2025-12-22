package be.milete.app.news.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.apache.commons.lang3.StringUtils.repeat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NewsArticleConstructorTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankTitleThrowsIllegalArgumentException(String title){
        Executable doCreate = () -> new NewsArticle(title, repeat('d', 500));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);
        assertEquals("title must not be blank", exception.getMessage());
    }

    @Test
    void givenTitleOf41CharactersThrowsIllegalArgumentException(){
        Executable doCreate = () -> new NewsArticle(repeat('t', 41), repeat('d', 500));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);
        assertEquals("title must be max 40 characters", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void givenBlankParagraphThrowsIllegalArgumentException(String paragraph){
        Executable doCreate = () -> new NewsArticle(repeat('t', 40), paragraph);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);
        assertEquals("paragraph must not be blank", exception.getMessage());
    }


    @Test
    void shouldCreateNewsArticle(){
        String title = repeat('t', 40);
        String paragraph = repeat('d', 500);

        NewsArticle newsArticle = new NewsArticle(title, paragraph);

        assertEquals(title, newsArticle.getTitle());
        assertEquals(paragraph, newsArticle.getParagraph());
    }
}
