package org.hse.course.application.datasource;

import org.hse.course.domain.model.Ticket;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты для {@link TicketImpl}
 */
public class TicketImplTest {

    /**
     * Проверяет успешное создание экземпляра
     */
    @Test
    public void testTicketInstantiated() {
        // given
        Ticket ticket = null;

        var n = 100500;
        var digits = 6;

        // when
        ticket = new TicketImpl(n, digits);

        // then
        assertNotNull("Объект должен быть создан", ticket);
        assertEquals("Должен быть создан билет с номером " + n, n, ticket.getNumber());
        assertEquals("Должен быть создан билет с количеством цифр " + digits, digits, ticket.getDigits());
    }

    /**
     * Проверяет поведение констурктора при переданном отрицательном номере билета
     */
    @Test
    public void testNumberIsNegative() {
        // given
        var n = -100500;
        var digits = 6;

        try {
            // when
            var ticket = new TicketImpl(n, digits);

            // then
            fail("Должно быть выброшено исключение");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Должен быть правильный текст исключения", String.format("Занчение number = %d не подходит", n), e.getMessage());
        }
    }

    /**
     * Проверяет поведение конструктора при переданном отрицательном номере билета
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNumberIsNegativeExpected() {
        // given
        var n = -100500;
        var digits = 6;

        // when
        // then
        var ticket = new TicketImpl(n, digits);
    }

    /**
     * Проверяет поведение конструктора при переданном нечётном кодичестве цифр
     */
    @Test
    public void testOddDigits() {
        // given
        var n = 100500;
        var digits = 7;

        try {
            // when
            var ticket = new TicketImpl(n, digits);

            // then
            fail("Должно быть выброшено исключение");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Должен быть правильный текст исключения", String.format("Количество цифр %d нечётно!", digits), e.getMessage());
        }
    }

    /**
     * Проверяет поведение конструктора при переданном номере > максимально возможного
     */
    @Test
    public void testNumberExceedsMaximum() {
        // given
        var n = 100500;
        var digits = 4;

        try {
            // when
            var ticket = new TicketImpl(n, digits);

            // then
            fail("Должно быть выброшено исключение");
        } catch (IllegalArgumentException e) {
            // then
            assertEquals("Должен быть правильный текст исключения", String.format("Номер %d больше максимально возможного %.0f", n, Math.pow(10, digits) - 1), e.getMessage());
        }

    }

}