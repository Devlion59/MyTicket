package org.hse.course.domain.busines;

import lombok.experimental.UtilityClass;

/**
 * Класс для утилит по работе с билетами
 */
@UtilityClass
public class TicketUtils {
    /**
     * @param number число
     * @return сумма цифр числа
     */
    public int getDigitsSum(int number) {
        var result = 0;
        for (var tmpNumber = number; tmpNumber > 0; tmpNumber /= 10) {
            int currentDigit = tmpNumber % 10;
            result += currentDigit;
        }
        return result;
    }
}
