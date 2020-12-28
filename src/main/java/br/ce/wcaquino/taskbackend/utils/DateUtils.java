package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

public class DateUtils {

	// A data é igual ou Futura
	public static boolean isEqualOrFutureDate(LocalDate date) {
		return date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now());
	}
}

