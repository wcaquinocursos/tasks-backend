package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.*;

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
	private TaskRepo todoRepo;
	
	@InjectMocks
	private TaskController controller;
	
	private LocalDate date;
	private Task task;
	
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		task = new Task();
	}
	
	@Test
	public void testEmptyDescription() {
		date = LocalDate.now();
		task.setDueDate(date);
		try {
			controller.save(task);
			Assert.fail("Não deveria chegar a esse ponto");
		} catch (ValidationException e) {
			assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void testEmptyDueDate() {
		task.setTask("Desc");
		try {
			controller.save(task);
			Assert.fail("Não deveria chegar a esse ponto");
		} catch (ValidationException e) {
			assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void testOldDate() {
		date = LocalDate.of(2005, 10, 10);
		task.setDueDate(date);
		task.setTask("Desc");
		try {
			controller.save(task);
			Assert.fail("Não deveria chegar a esse ponto");
		} catch (ValidationException e) {
			assertEquals("Due date must not be in past", e.getMessage());
		}
	}

	@Test
	public void testTaskSavedSuccessfully() throws ValidationException {
		date = LocalDate.now();
		task.setDueDate(date);
		task.setTask("Desc");
		controller.save(task);
		Mockito.verify(todoRepo).save(task);
	}

}
