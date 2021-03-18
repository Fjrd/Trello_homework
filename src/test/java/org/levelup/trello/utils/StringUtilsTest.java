package org.levelup.trello.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
public class StringUtilsTest {

    // test<MethodName>_when<InputParameters>_then<MethodReturn>
    // shouldReturnNullIfInputStringIsNull()
    @Test
    @DisplayName("reverse(String): Should throw NPE if input string is null")
    public void testReverse_whenInputIsNull_thenThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> StringUtils.reverse(null));
        // String result = StringUtils.reverse(null);
        // Assertions.assertNull(result); // assertNull бросит AssertionError если значение не является NULL
    }

    @Test
    public void testReverse_whenInputIsEmpty_thenReturnSameString() {
        String emptyString = "";
        String result = StringUtils.reverse(emptyString);

        Assertions.assertEquals(emptyString, result); // сравнивает объекты через equals
        Assertions.assertSame(emptyString, result);   // сравнивает объекты через == (сравнивает ссылки)
    }

    @Test
    public void testReverse_whenUsualString_thenReverseAndReturn() {
        // given
        String original = "qwerty";
        String expectedResult = "ytrewq";

        // when
        String result = StringUtils.reverse(original);

        // then
        Assertions.assertEquals(expectedResult, result);
    }

}