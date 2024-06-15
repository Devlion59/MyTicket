package org.hse.course.domain.model;

import org.hse.course.domain.busines.TicketUtils;

/**
 * Интерфейс предоставляет метод проверки на "счастливость"
 */
public interface Ticket {

    /**
     * @return возвращает признак "счастливости" билета
     */
    default boolean isLucky() {
        var divisor = (int) Math.pow(10, getDigits() / 2);

        var firstHalf = getNumber() / divisor;
        var lastHalf = getNumber() % divisor;

        return TicketUtils.getDigitsSum(firstHalf) == TicketUtils.getDigitsSum(lastHalf);
//        return firstHalf.getDigitsSum() == lastHalf.getDigitsSum();
    }

    /**
     * @return номер билета
     */
    int getNumber();

    /**
     * @return количество цифр в билете
     */
    int getDigits();
}

