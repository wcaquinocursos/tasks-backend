package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DataUtilsTest {
	
	@Test
	public void deveRetoornarTrueParaDatasFuturas() {
		LocalDate date = LocalDate.of(2030, 01, 01);
		
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetoornarFalseParaDatasPassadas() {
		LocalDate date = LocalDate.of(2010, 01, 01);
		
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
	}
	
	@Test
	public void deveRetoornarTrueParaDataAtual() {
		LocalDate date = LocalDate.now();
		
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
	}
}
