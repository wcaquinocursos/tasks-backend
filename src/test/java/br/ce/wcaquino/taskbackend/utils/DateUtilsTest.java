package br.ce.wcaquino.taskbackend.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateUtilsTest {

    @Test
    public void deveRetornarTrueParaDatasFuturas(){
        LocalDate date = LocalDate.of(2030, 01, 01);
        DateUtils.isEqualOrFutureDate(date);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarFalseParaDatasPassadas(){
        LocalDate date = LocalDate.of(2020, 01, 01);
        DateUtils.isEqualOrFutureDate(date);
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void deveRetornarTrueParaDatasAtual(){
        LocalDate date = LocalDate.now();
        DateUtils.isEqualOrFutureDate(date);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

}
