package ir.sheikh.taskmanager.controller;

import ir.sheikh.taskmanager.entity.Task;
import ir.sheikh.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        Optional<Task> task = taskService.getTask(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        taskService.createTask(task);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        Optional<Task> taskOptional = taskService.getTask(id);
        if (taskOptional.isPresent()) {
            Task updatedTask = taskOptional.get();
            updatedTask.setName(task.getName());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setDueDate(task.getDueDate());
            updatedTask.setCompleted(task.getCompleted());
            taskService.updateTask(updatedTask);
            return ResponseEntity.ok(updatedTask);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.getTask(id);
        if (taskOptional.isPresent()) {
            taskService.deleteTask(id);
            return ResponseEntity.ok(taskOptional.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
