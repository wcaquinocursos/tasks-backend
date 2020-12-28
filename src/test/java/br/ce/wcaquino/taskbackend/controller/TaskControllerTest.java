package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private  TaskController controller;

    @Before
    public  void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefasSemDescicao() {
        Task todo = new Task();
//        todo.setTask("descrição");
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar aqui!!!");
        } catch (ValidationException e){
            Assert.assertEquals("xFill the task description", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefasSemData(){
        Task todo = new Task();
        todo.setTask("descrição");
//        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar aqui!!!");
        } catch (ValidationException e){
            Assert.assertEquals("xFill the due date", e.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefasComDataPassada(){
        Task todo = new Task();
        todo.setTask("descrição");
        todo.setDueDate(LocalDate.of(2010, 01 ,01));
        try {
            controller.save(todo);
            Assert.fail("xNão deveria chegar aqui!!!");
        } catch (ValidationException e){
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setTask("descrição");
        todo.setDueDate(LocalDate.now());
        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
        }
    }



