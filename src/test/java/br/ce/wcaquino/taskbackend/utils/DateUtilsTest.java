package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testFutureDate() {
		LocalDate date = LocalDate.of(2030, 10, 12);
		assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void testCurrentDate() {
		LocalDate date = LocalDate.now();
		assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void testOldDate() {
		LocalDate date = LocalDate.of(2010, 10, 12);
		assertFalse(DateUtils.isEqualOrFutureDate(date));
	}

}
