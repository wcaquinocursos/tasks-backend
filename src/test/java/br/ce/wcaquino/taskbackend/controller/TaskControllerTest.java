package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;

public class TaskControllerTest {

    // definindo um mock
    @Mock
    private TaskRepo taskRepo;

    // injetando o mock na classe controller
    @InjectMocks
    TaskController controller = new TaskController();

    // iniciando o mockito
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        Task todo = new Task();
        // todo.setTask("Descrição");
        todo.setDueDate(LocalDate.now());
        try {
            controller.save(todo);
            Assert.fail("Não deveria chegar nesse ponto");
        } catch (Exception e) {
            Assert.assertEquals("Fill the task description", e.getMessage());
        }

    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        Task todo = new Task();
        // todo.setDueDate(LocalDate.now());
        todo.setTask("Descrição");
        try {
            controller.save(todo);
        } catch (Exception e) {
            Assert.assertEquals("Fill the due date", e.getMessage());
        }

    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.of(2020, 01, 01));
        todo.setTask("Descrição");
        try {
            controller.save(todo);
        } catch (Exception e) {
            Assert.assertEquals("Due date must not be in past", e.getMessage());
        }

    }

    @Test
    public void DeveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        todo.setTask("Descrição");
        controller.save(todo);

        // verificando se a classe mockada foi invocada no método salvar
        Mockito.verify(taskRepo).save(todo);

    }

}