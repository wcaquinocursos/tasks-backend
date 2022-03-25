package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate date = LocalDate.of(2023, 01, 01);
		DateUtils.isEqualOrFutureDate(date);
	    Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}

	
	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		LocalDate date = LocalDate.of(2010, 01, 01);
		DateUtils.isEqualOrFutureDate(date);
	    Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}

	@Test
	public void deveRetornarTrueParaDatasAtual() {
		LocalDate date = LocalDate.now();
	    Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}

}
