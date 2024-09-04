package br.com.tasklist.controller;

import br.com.tasklist.entity.dto.TaskDTO;
import br.com.tasklist.services.TaskService;
import br.com.tasklist.services.exception.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@Tag(name = "Tarefas", description = "Contem as operações de Cadastro, Atualização, Buscas e Deleção de registros de tarefas")
@RestController
@RequestMapping(value = "/task")
@CrossOrigin("http://localhost:4200")
public class    TaskController {

    @Autowired
    private TaskService service;

    @Operation(summary = "Listar todas as tarefas", description = "Listar todas as tarefas cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista todas as cadastrados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = TaskDTO.class))))
            })
    @GetMapping
    public ResponseEntity<Object>findAll() {
        List<TaskDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = "Recuperar uma tarefa pelo id", description = "Recuperar uma Tarefa pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefa recuperada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    @GetMapping(value = "/{id}")
    public ResponseEntity<TaskDTO> findById(@PathVariable Long id) {
        TaskDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Recuperar uma lista de tarefas pelo status", description = "Recuperar uma Tarefa pelo status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tarefas recuperadas com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    @GetMapping(value = "/status/{status}")
    public ResponseEntity<List<TaskDTO>> findByStatus(@PathVariable String status) {
        List<TaskDTO> dto = service.findByStatus(status);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Cria uma nova tarefa", description = "Recurso para criar uma nova tarefa",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class)))
            })
    @PostMapping
    public ResponseEntity<Object>insert(@Valid @RequestBody TaskDTO dto){
        TaskDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getIdTask()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @Operation(summary = "Atualizar registro", description = "Atualizar registro",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    @PutMapping
    public ResponseEntity<TaskDTO> update(@Valid @RequestBody TaskDTO dto) {
        dto = service.update(dto.getIdTask(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Deletar uma tarefa pelo id", description = "Deletar uma Tarefa pelo ID",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Tarefas deletada com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResourceNotFoundException.class)))
            })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
