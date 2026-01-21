package br.ce.wcaquino.taskbackend.controller;

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

    @InjectMocks
    private TaskController controller;

    @Mock
    private TaskRepo taskRepo;
    
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        try {
			controller.save(todo);
			Assert.fail("Não deveria cheager nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description.", e.getMessage());
		}
    }

    @Test
    public void naoDeveSalvarTarefaSemData(){
        Task todo = new Task();
        todo.setTask("Estudar testes");
        try {
			controller.save(todo);
			Assert.fail("Não deveria cheager nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date.", e.getMessage());
		}
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada(){
        Task todo = new Task();
        todo.setTask("Tarefa atrasada");
        todo.setDueDate(LocalDate.now().minusDays(1));
        try {
			controller.save(todo);
			Assert.fail("Não deveria cheager nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException{
        Task todo = new Task();
        todo.setTask("Estudar Mockito");
        todo.setDueDate(LocalDate.now().plusDays(1));
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }
}

