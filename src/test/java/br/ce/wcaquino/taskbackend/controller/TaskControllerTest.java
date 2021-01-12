package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;

	@InjectMocks
	private TaskController taskController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void naoDeveSalvarTarefasSemDescricao() {
		Task toDo = new Task();
		toDo.setDueDate(LocalDate.now());
		try {
			taskController.save(toDo);
			Assert.fail("Não deveria chegar neste ponto!");
		} catch (ValidationException e) {
			assertEquals("Fill the task descriptionnnn", e.getMessage());
		}

	}

	@Test
	public void naoDeveSalvarTarefasSemData() {
		Task toDo = new Task();
		toDo.setTask("Descrição");
		try {
			taskController.save(toDo);
			Assert.fail("Não deveria chegar neste ponto!");
		} catch (ValidationException e) {
			assertEquals("Fill the due date", e.getMessage());
		}

	}

	@Test
	public void naoDeveSalvarTarefasComDataPassada() {
		Task toDo = new Task();
		toDo.setTask("Descrição");
		toDo.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			taskController.save(toDo);
			Assert.fail("Não deveria chegar neste ponto!");
		} catch (ValidationException e) {
			assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task toDo = new Task();
		toDo.setTask("Descrição");
		toDo.setDueDate(LocalDate.now());
		taskController.save(toDo);
		Mockito.verify(taskRepo).save(toDo);
	}
}
