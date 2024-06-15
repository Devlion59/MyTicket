package org.hse.course.application.datasource;

import lombok.Getter;
import org.hse.course.domain.model.Ticket;


/**
 * Реалищация интерфесйа {@link Ticket} с помощью record
 *
 * @param number номер билета
 * @param digits количество цифр в билете
 */
public record TicketRecordImpl(@Getter int number, @Getter int digits) implements Ticket { }
