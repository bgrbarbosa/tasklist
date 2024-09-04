package br.com.tasklist.repository;

import br.com.tasklist.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepositoy extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM TB_TASKS t WHERE t.status like %:status%", nativeQuery = true)
    List<Task> findByStatus(String status);
}
