package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void deveRetornarTrueParaDatasFuturas() {

		LocalDate date = LocalDate.of(2023, 01, 01);

		Assert.assertFalse("A data futura falhou!", DateUtils.isEqualOrFutureDate(date));

	}

	@Test
	public void deveRetornarFalseParaDatasPassadas() {

		LocalDate date = LocalDate.of(2010, 01, 01);

		Assert.assertFalse("A data passada falhou!", DateUtils.isEqualOrFutureDate(date));

	}

	@Test
	public void deveRetornarTrueParaDataAtual() {

		LocalDate date = LocalDate.now();

		Assert.assertTrue("A data atual falhou!", DateUtils.isEqualOrFutureDate(date));

	}

}
