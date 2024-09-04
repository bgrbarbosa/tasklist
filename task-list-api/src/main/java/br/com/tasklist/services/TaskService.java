package br.com.tasklist.services;


import br.com.tasklist.entity.Task;
import br.com.tasklist.entity.dto.TaskDTO;
import br.com.tasklist.repository.TaskRepositoy;
import br.com.tasklist.services.exception.DatabaseException;
import br.com.tasklist.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepositoy repository;

    @Transactional(readOnly = true)
    public List<TaskDTO> findAll() {
        List<Task> list = repository.findAll();
        List<TaskDTO> taskListDTO = new ArrayList<>();
        for(Task task : list) {
            taskListDTO.add(toDTO(task));
        }
        return taskListDTO;
    }

    @Transactional(readOnly = true)
    public TaskDTO findById(Long id) {
        Optional<Task> obj = repository.findById(id);
        Task entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<TaskDTO> findByStatus(String status) {
        List<Task> tasks = repository.findByStatus(status);
        List<TaskDTO> taskListDTO = new ArrayList<>();
        if (tasks.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        for(Task task : tasks) {
            taskListDTO.add(toDTO(task));
        }
        return taskListDTO;
    }

    public TaskDTO insert(TaskDTO dto) {
        Task entity = new Task();
        entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public TaskDTO update(Long id, TaskDTO dto) {
        try {
            Task entity = repository.getReferenceById(id);
            entity.setDescTask(dto.getDescTask());
            entity.setStatus(dto.getStatus());
            entity.setDataInit(dto.getDataInit());
            entity.setDataClose(dto.getDataClose());
            entity = repository.save(entity);
            return toDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional()
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }
    }

    private Task toEntity(TaskDTO dto){
        var entity = new Task();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private TaskDTO toDTO(Task entity){
        var dto = new TaskDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
