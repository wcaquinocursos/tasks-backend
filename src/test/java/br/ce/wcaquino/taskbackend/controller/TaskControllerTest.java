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
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricaoNull() {
		Task todo = new Task();
		
		todo.setDueDate(LocalDate.now());
		
		try {
			taskController.save(todo);
			Assert.fail("Deverá ser lançado uma exeception.");
		} catch (ValidationException e) {
			
			String msg = "Fill the task description";
			
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricaoVazia() {
		Task todo = new Task();
		
		todo.setDueDate(LocalDate.now());
		
		todo.setTask("");
		
		try {
			taskController.save(todo);
			Assert.fail("Deverá ser lançado uma exeception.");
		} catch (ValidationException e) {
			
			String msg = "Fill the task description";
			
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData(){
		
		Task todo = new Task();
		
		todo.setTask("Teste");
		
		try {
			taskController.save(todo);
			Assert.fail("Deverá ser lançado uma exeception.");
		} catch (ValidationException e) {
			
			String msg = "Fill the due date";
			
			assertEquals(msg, e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada(){
		
		Task todo = new Task();
		
		todo.setDueDate(LocalDate.now().minusYears(1));
		
		todo.setTask("Teste");
		
		try {
			taskController.save(todo);
			Assert.fail("Deverá ser lançado uma exeception.");
		} catch (ValidationException e) {
			
			String msg = "Due date must not be in past";
			
			assertEquals(msg, e.getMessage());
		}
	}

	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException{
		
		Task todo = new Task();
		
		todo.setDueDate(LocalDate.now().plusDays(1));
		
		todo.setTask("Teste");
		
		 taskController.save(todo);
		
		Mockito.verify(taskRepo).save(todo);
		
		
	}
}
