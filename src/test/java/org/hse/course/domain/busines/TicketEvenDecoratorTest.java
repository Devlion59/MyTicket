package org.hse.course.domain.busines;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hse.course.domain.model.Ticket;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Тесты для {@link TicketEvenDecorator}
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketEvenDecoratorTest {

    Ticket ticket;

    @Before
    public void init() {
        ticket = Mockito.mock(Ticket.class);
        Mockito.when(ticket.isLucky()).thenReturn(true);
        Mockito.when(ticket.getNumber()).thenReturn(100100);
    }

    /**
     * Декоратор проверяет чётность билетов помимо проверка на "счастливость"
     */
    @Test
    public void testEvenLuckyTicket() {
        // given
        TicketEvenDecorator decorator = new TicketEvenDecorator(ticket);

        // when
        boolean actual = decorator.isLucky();

        // then
        assertTrue("Этот билет счастливый", actual);
        Mockito.verify(ticket, Mockito.times(1)).isLucky();
        Mockito.verify(ticket, Mockito.times(1)).getNumber();
    }

    /**
     * Нечётный билет не может быть счастливым.
     */
    @Test
    public void testOddLuckyTicket() {
        // given
        Mockito.when(ticket.getNumber()).thenReturn(1001);
        TicketEvenDecorator decorator = new TicketEvenDecorator(ticket);

        // when
        boolean actual = decorator.isLucky();

        // then
        assertFalse("Этот билет не счастливый", actual);
        Mockito.verify(ticket, Mockito.never()).isLucky();
        Mockito.verify(ticket, Mockito.times(1)).getNumber();
    }

    /**
     * Не любой чётный билет является счастливым
     */
    @Test
    public void testUnluckyEvenTicket() {
        // given
        Mockito.when(ticket.getNumber()).thenReturn(100500);
        Mockito.when(ticket.isLucky()).thenReturn(false);
        TicketEvenDecorator decorator = new TicketEvenDecorator(ticket);

        // when
        boolean actual = decorator.isLucky();

        // then
        assertFalse("Этот билет не счастливый", actual);
        Mockito.verify(ticket, Mockito.times(1)).isLucky();
        Mockito.verify(ticket, Mockito.times(1)).getNumber();
    }

}