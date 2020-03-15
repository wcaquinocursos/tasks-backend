package br.ce.wcaquino.taskbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ce.wcaquino.taskbackend.model.Task;

public interface TaskRepo extends JpaRepository<Task, Long>{

}
