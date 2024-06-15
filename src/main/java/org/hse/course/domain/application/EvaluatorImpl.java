package org.hse.course.domain.application;

import lombok.Value;
import org.hse.course.domain.busines.TicketIterator;

import java.util.function.Supplier;

/**
 * Реализация сервиса для вычисления количества шестизначных счастливых билетов
 */
@Value
public class EvaluatorImpl implements Evaluator {
    /**
     * Поставщик итераторов. Метод get должен возвращать итератор, готовый к использованию.
     */
    Supplier<TicketIterator> ticketIterator;

    /**
     * Вычисляет количество шестизначных счастливых билетов
     */
    @Override
    public int evaluate() {
        var iterator = ticketIterator.get();

        var quantity = 0;
        for(;iterator.hasNext();) {
            var ticket = iterator.next();
            if (ticket.isLucky()) {
                quantity++;
            }
        }

        return quantity;
    }
}
