package br.com.tasklist.entity.dto;

import br.com.tasklist.entity.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long idTask;

    @NotBlank(message = "Description cannot be empty")
    @Size(min = 5, max = 60, message = "Description must contain between 3 and 60 characters")
    private String descTask;

    @NotNull(message = "Start date must not be empty")
    private LocalDate dataInit;

    @NotNull(message = "End date must not be empty")
    private LocalDate dataClose;

    @NotNull(message = "Status cannot be empty")
    private Status status;

}
