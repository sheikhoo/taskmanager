package ir.sheikh.taskmanager.controller;

import ir.sheikh.taskmanager.entity.Project;
import ir.sheikh.taskmanager.entity.Task;
import ir.sheikh.taskmanager.repository.ProjectRepository;
import ir.sheikh.taskmanager.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProject(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(project.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetail) {
        Optional<Project> project = projectService.getProject(id);
        if (project.isPresent()) {
            Project updatedProject = project.get();
            updatedProject.setName(projectDetail.getName());
            updatedProject.setDescription(projectDetail.getDescription());

            updatedProject.setTasks(projectDetail.getTasks());

            return ResponseEntity.ok(projectService.updateProject(updatedProject));

        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Optional<Project> project = projectService.getProject(id);
        if (project.isPresent()) {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
