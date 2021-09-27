package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void deveRetornarTrueParaDataFuturas() {
		LocalDate localDate = LocalDate.of(2030,  01, 01);
		
		Boolean isFuture = DateUtils.isEqualOrFutureDate(localDate);
		
		assertFalse(isFuture);
	}
	
	@Test
	public void deveRetornarFalseParaDataFuturas() {
		LocalDate localDate = LocalDate.of(2020,  01, 01);
		
		Boolean isFuture = DateUtils.isEqualOrFutureDate(localDate);
		
		assertFalse(isFuture);
	}
	
	@Test
	public void deveRetornarTrueParaDataAtual() {
		LocalDate localDate = LocalDate.now();
		
		Boolean isFuture = DateUtils.isEqualOrFutureDate(localDate);
		
		assertTrue(isFuture);
	}
}
