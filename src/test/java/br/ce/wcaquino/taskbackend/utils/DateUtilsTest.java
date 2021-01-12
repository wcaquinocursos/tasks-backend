package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {
	
	private DateUtils dataUtil = new DateUtils();

	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate dataFutura = LocalDate.of(2030, 01, 01);
		assertTrue(DateUtils.isEqualOrFutureDate(dataFutura));
	}
	
	@Test
	public void deveRetornarTrueParaDataAtual() {
		LocalDate dataAtual = LocalDate.now();
		assertTrue(DateUtils.isEqualOrFutureDate(dataAtual));
	}
	
	@Test
	public void deveRetornarFalsoParaPassada() {
		LocalDate dataPassada = LocalDate.of(2020, 01, 01);
		assertFalse(DateUtils.isEqualOrFutureDate(dataPassada));
	}

}
