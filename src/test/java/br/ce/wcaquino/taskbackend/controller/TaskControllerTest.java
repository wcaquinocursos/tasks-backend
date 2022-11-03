package br.ce.wcaquino.taskbackend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TaskControllerTest {

  @Mock private TaskRepo taskRepo;
  @InjectMocks private TaskController sobTeste;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void naoDeveSalvarTarefaSemDescricao() {
    Task taskSemDescricao = new Task();
    taskSemDescricao.setDueDate(LocalDate.now());

    assertThatThrownBy(() -> sobTeste.save(taskSemDescricao))
        .isInstanceOf(ValidationException.class)
        .hasMessage("Fill the task description");
  }

  @Test
  public void naoDeveSalvarTarefaSemData() {
    Task taskSemData = new Task();
    taskSemData.setTask("Tomar banho na banheira.");

    assertThatThrownBy(() -> sobTeste.save(taskSemData))
        .isInstanceOf(ValidationException.class)
        .hasMessage("Fill the due date");
  }

  @Test
  public void naoDeveSalvarTarefaComDataPassada() {
    Task taskComDataPassada = new Task();
    taskComDataPassada.setTask("Tomar banho na banheira.");
    taskComDataPassada.setDueDate(LocalDate.of(2001, 1, 1));

    assertThatThrownBy(() -> sobTeste.save(taskComDataPassada))
        .isInstanceOf(ValidationException.class)
        .hasMessage("Due date must not be in past");
  }

  @Test
  public void deveSalvarTarefaComSucesso() throws ValidationException {
    Task taskCorreta = new Task();
    taskCorreta.setTask("Tomar banho na banheira.");
    taskCorreta.setDueDate(LocalDate.now());

    ResponseEntity<Task> resultado = sobTeste.save(taskCorreta);

    verify(taskRepo).save(taskCorreta);
    assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }
}
