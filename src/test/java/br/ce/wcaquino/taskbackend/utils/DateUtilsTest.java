package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void deveRetonarTrueParaDatasFuturas() {
		LocalDate date = LocalDate.of(2030, 11, 27);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetonarTrueParaDataAtual() {
		LocalDate date = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetonarFalseParaDatasPassadas() {
		LocalDate date = LocalDate.of(2022, 10, 27);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
}
