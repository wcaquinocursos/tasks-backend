package br.ce.wcaquino.taskbackend.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.Test;

public class DateUtilsTest {

  @Test
  public void deveRetornarTrueParaDatasFuturas() {
    LocalDate date = LocalDate.of(2099,1, 1);
    assertThat(DateUtils.isEqualOrFutureDate(date)).isFalse();
  }

  @Test
  public void deveRetornarFalseParaDatasPassadas() {
    LocalDate date = LocalDate.of(2001,1, 1);
    assertThat(DateUtils.isEqualOrFutureDate(date)).isFalse();
  }

  @Test
  public void deveRetornarTrueParaDataAtual() {
    LocalDate date = LocalDate.now();
    assertThat(DateUtils.isEqualOrFutureDate(date)).isTrue();
  }
}