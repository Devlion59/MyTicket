package org.hse.course.domain.busines;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.hse.course.domain.model.Ticket;

/**
 * Добавляет проверку кратности пяти при определении, счастливый билет, либо нет
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketMultiplicityOfFiveDecorator implements Ticket {
    private interface Exclude {
        boolean isLucky();
    }

    @Delegate(excludes = Exclude.class)
    Ticket wrapee;

    @Override
    public boolean isLucky() {
        return (wrapee.getNumber() % 5 == 0) && wrapee.isLucky();
    }
}
