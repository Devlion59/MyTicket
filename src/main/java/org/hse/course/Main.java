package org.hse.course;

import org.hse.course.application.context.Context;
import org.hse.course.application.datasource.TicketImpl;
import org.hse.course.domain.application.Evaluator;

import java.util.function.Consumer;

/**
 * Приложение для вычисления количества шестизначных счастливых билетов
 */
public class Main {

    private static final Consumer<Evaluator> EVALUATE =
            evaluator -> {
                for (var i = 0; i < 5; i++) {
                    long start = System.currentTimeMillis();
                    var result = evaluator.evaluate();
                    long duration = System.currentTimeMillis() - start;
                    System.out.println(String.format("Всего %d шестизначных счастливых билетов. Получено за %d мс", result, duration));
                }
            };

    public static void main( String[] args ) {
        var ticket = new TicketImpl(100500, 6);
        ticket.withNumber(100501);
        var build = TicketImpl.builder().number(100500).digits(6).build();

        Context context = Context.getInstance();
        var evaluator8 = context.getObjectByName("evaluator8", Evaluator.class);
        evaluator8.ifPresentOrElse(EVALUATE, () -> System.out.println("Объект не найден!"));

        System.out.println();

        var evaluator8Even = context.getObjectByName("evaluator8MultiplyOf10", Evaluator.class);
        evaluator8Even.ifPresentOrElse(EVALUATE, () -> System.out.println("Объект не найден!"));
    }
}
