package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock
	private TaskRepo repo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task tarefa = new Task();
		tarefa.setTask("Tarefa de teste " + LocalTime.now());
		tarefa.setDueDate(LocalDate.now());
		
		ResponseEntity<Task> response = controller.save(tarefa);
		Assert.assertEquals(201, response.getStatusCodeValue());
		
		// Check if internal process above was ok: Was TaskRepo.class called upon method save receiving tarefa object as its paramether?
		Mockito.verify(repo).save(tarefa); 
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task tarefa = new Task();
		tarefa.setDueDate(LocalDate.now());
		
		try {
			controller.save(tarefa);
			Assert.fail("Test should not reach this point!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task tarefa = new Task();
		tarefa.setTask("Tarefa de teste " + LocalTime.now());
		
		try {
			controller.save(tarefa);
			Assert.fail("Test should not reach this point!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task tarefa = new Task();
		tarefa.setTask("Tarefa de teste " + LocalTime.now());
		tarefa.setDueDate(LocalDate.of(2022, 10, 27));
		
		try {
			controller.save(tarefa);
			Assert.fail("Test should not reach this point!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
}
