package org.hse.course.domain.application;

import lombok.Value;
import org.hse.course.domain.model.Ticket;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Реализация {@link Evaluator} на основе {@link java.util.stream.Stream}
 */
@Value
public class EvaluatorStreamImpl implements Evaluator {
    Supplier<Stream<Ticket>> ticketStream;

    @Override
    public int evaluate() {
        var stream = ticketStream.get();
        long count =
                stream
                        .parallel()
                        .filter(Ticket::isLucky)
                        .count();
        return (int) count;
    }
}
