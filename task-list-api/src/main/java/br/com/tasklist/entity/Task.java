package br.com.tasklist.entity;

import br.com.tasklist.entity.dto.TaskDTO;
import br.com.tasklist.entity.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Entity
@Table(name = "tb_tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idTask;

    @Column
    private String descTask;

    @Column
    private LocalDate dataInit;

    @Column
    private LocalDate dataClose;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

}
