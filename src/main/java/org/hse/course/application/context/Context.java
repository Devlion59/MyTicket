package org.hse.course.application.context;

import lombok.EqualsAndHashCode;
import org.hse.course.application.datasource.TicketImpl;
import org.hse.course.application.datasource.TicketIteratorImpl;
import org.hse.course.application.datasource.TicketRecordImpl;
import org.hse.course.domain.application.Evaluator;
import org.hse.course.domain.application.EvaluatorImpl;
import org.hse.course.domain.application.EvaluatorStreamImpl;
import org.hse.course.domain.busines.TicketEvenDecorator;
import org.hse.course.domain.busines.TicketMultiplicityOfFiveDecorator;
import org.hse.course.domain.model.Ticket;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Создаёт контекст приложения
 */
public interface Context {
    /**
     * @return экземпляр {@link Context}
     */
    static Context getInstance() {
        return new ContextDefaultImpl();
    }

    /**
     * Возвращает экземпляр объекта нужного типа с нудным именем
     */
    <T> Optional<T> getObjectByName(String name, Class<T> clazz);
}
/**
 * Создаёт контекст приложения
 */
@EqualsAndHashCode
class ContextDefaultImpl implements Context {
    private final Map<String, Evaluator> context;

    public ContextDefaultImpl() {
        this.context = new HashMap<>();
        var evaluator8 = new EvaluatorStreamImpl(ContextDefaultImpl::eightDigitsTicketStreamSupplier);
        context.put("evaluator8", evaluator8);

        BiFunction<Integer, Integer, Ticket> toTicketDecoratorEven =
                (n, digits) -> new TicketEvenDecorator(new TicketRecordImpl(n, digits));
        var evaluator8Even =
                new EvaluatorStreamImpl(eightDigitsTicketStreamSupplier(toTicketDecoratorEven));
        context.put("evaluator8Even", evaluator8Even);

        BiFunction<Integer, Integer, Ticket> toTicketDecoratorMultiplyOf5 =
                (n, digits) -> new TicketMultiplicityOfFiveDecorator(new TicketImpl(n, digits));
        var evaluator8MultiplyOf5 =
                new EvaluatorStreamImpl(eightDigitsTicketStreamSupplier(toTicketDecoratorMultiplyOf5));
        context.put("evaluator8MultiplyOf5", evaluator8MultiplyOf5);

        var evaluator8MultiplyOf10 =
                new EvaluatorStreamImpl(eightDigitsTicketStreamSupplier((n, digits) -> new TicketMultiplicityOfFiveDecorator(new TicketEvenDecorator(new TicketImpl(n, digits)))));
        context.put("evaluator8MultiplyOf10", evaluator8MultiplyOf10);

        var evaluator6 = new EvaluatorImpl(() -> new TicketIteratorImpl(6));
        context.put("evaluator6", evaluator6);
    }

    /**
     * Возвращает экземпляр объекта нужного типа с нудным именем
     */
    @Override
    public <T> Optional<T> getObjectByName(String name, Class<T> clazz) {
        var object = context.get(name);
        if (object == null) {
            return Optional.empty();
        }
        if (clazz.isAssignableFrom(object.getClass())) {
            return Optional.of((T) object);
        }

        return Optional.empty();
    }

    private static Stream<Ticket> eightDigitsTicketStreamSupplier() {
        var digits = 8;
        var maxNumber = (int) Math.pow(10, digits);
        Function<Integer, Stream<Ticket>> toStream =
                ((Function<Integer, IntStream>) number -> IntStream.range(0, number))
                        .andThen(IntStream::parallel)
                        .andThen(stream -> stream.mapToObj(n -> new TicketRecordImpl(n, digits)));

        return toStream.apply(maxNumber);
    }

    private static Supplier<Stream<Ticket>> eightDigitsTicketStreamSupplier(
            final BiFunction<Integer, Integer, Ticket> toTicket) {
        var digits = 8;
        var maxNumber = (int) Math.pow(10, digits);
        Function<Integer, Stream<Ticket>> toStream =
                ((Function<Integer, IntStream>) number -> IntStream.range(0, number))
                        .andThen(IntStream::parallel)
                        .andThen(stream -> stream.mapToObj(n -> toTicket.apply(n, digits)));

        return () -> toStream.apply(maxNumber);
    }
}