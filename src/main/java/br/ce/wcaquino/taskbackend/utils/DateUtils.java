package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class DateUtils {

	public static boolean isEqualOrFutureDate(@NotNull LocalDate date) {
		return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
	}
}
