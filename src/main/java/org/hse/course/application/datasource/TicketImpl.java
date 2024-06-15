package org.hse.course.application.datasource;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.With;
import lombok.experimental.ExtensionMethod;
import org.hse.course.domain.busines.TicketUtils;
import org.hse.course.domain.model.Ticket;

/**
 * Сущность Билет
 */
@With
@Value
@Builder
@ExtensionMethod(TicketUtils.class)
public class TicketImpl implements Ticket {
    int number;
    @ToString.Exclude
    int digits;

    public TicketImpl(int number, int digits) {
        if (number < 0) {
            throw new IllegalArgumentException(String.format("Занчение number = %d не подходит", number));
        }
        if (digits % 2 != 0) {
            throw new IllegalArgumentException(String.format("Количество цифр %d нечётно!", digits));
        }
        if (number >= Math.pow(10, digits)) {
            throw new IllegalArgumentException(String.format("Номер %d больше максимально возможного %.0f", number, Math.pow(10, digits) - 1));
        }

        this.number = number;
        this.digits = digits;
    }


    @Override
    public boolean isLucky() {
        var divisor = (int) Math.pow(10, getDigits() / 2);

        var firstHalf = getNumber() / divisor;
        var lastHalf = getNumber() % divisor;

        return firstHalf.getDigitsSum() == lastHalf.getDigitsSum();
    }

}