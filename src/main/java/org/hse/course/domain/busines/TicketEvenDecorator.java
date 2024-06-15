package org.hse.course.domain.busines;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;
import org.hse.course.domain.model.Ticket;

/**
 * Добавляет проверку чётности при определении, счастливый билет, либо нет
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TicketEvenDecorator implements Ticket {
    private interface Exclude {
        boolean isLucky();
    }
    private interface Includes {
        int getNumber();
        int getDigits();
    }

    //    @Delegate(types = Includes.class)
    @Delegate(excludes = Exclude.class)
    Ticket wrapee;

    @Override
    public boolean isLucky() {
        return (wrapee.getNumber() % 2 == 0) && wrapee.isLucky();
    }
}
