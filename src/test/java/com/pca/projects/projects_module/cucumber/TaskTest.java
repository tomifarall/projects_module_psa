package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.exception.InvalidEmployeeException;
import com.pca.projects.projects_module.exception.InvalidTaskException;
import com.pca.projects.projects_module.exception.NotFoundException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import com.pca.projects.projects_module.service.ProjectTaskService;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.service.client.SupportClientService;
import com.pca.projects.projects_module.utils.TaskPriority;
import com.pca.projects.projects_module.utils.TaskType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TaskTest extends ProjectIntegrationServiceTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectTaskService projectTaskService;

    @Autowired
    SupportClientService supportClientService;

    @Autowired
    private ResourcesClientService resourcesClientService;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    private Project project;
    private ProjectTask projectTask;

    private TaskDTO taskDTO;

    private InvalidTaskException ite;
    private InvalidEmployeeException iee;

    @Before
    public void setup() {
        System.out.println("Before any test execution");
        MockitoAnnotations.initMocks(this);
        this.ite = null;
        this.projectTask = null;
        this.taskDTO = null;
        this.iee = null;
    }

    @Given("Quiero crear una nueva tarea en un proyecto al que pertenezco")
    public void quieroCrearUnaNuevaTareaEnUnProyectoAlQuePertenezco() {
        project = new Project();
        project.setId(1l);
        projectTask = new ProjectTask();
    }

    @When("Ingreso los datos de nombre, descripcion, tiempo estimado, responsable de la tarea y prioridad")
    public void ingresoLosDatosDeNombreDescripcionTiempoEstimadoResponsableDeLaTareaYPrioridad() {
        projectTask.setTitle("Create homepage");
        projectTask.setDescription("we need homepage");
        projectTask.setEstimatedTime(20d);
        projectTask.setEmployeeId(2l);
        projectTask.setTaskPriority(TaskPriority.MEDIUM);
        projectTask.setTaskType(TaskType.FEATURE);
        ProjectTask mockedTask = projectTask;
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenReturn(createResourceMock());
        Mockito.when(projectTaskRepository.save(Mockito.any(ProjectTask.class))).thenReturn(mockedTask);
        Mockito.when(resourcesClientService.getResourceRegistersByTask(Mockito.any(Long.class), Mockito.any(Long.class), Mockito.any(Long.class))).thenReturn(new ArrayList<>());
        taskDTO = projectTaskService.createTask(project.getId(), projectTask);
    }

    @Then("Se crea la tarea exitosamente")
    public void seCreaLaTareaExitosamente() {
        assertNotNull(taskDTO);
    }

    @When("Ingreso los datos de nombre, descripcion, tiempo estimado, prioridad y un legajo de responsable que no es valido")
    public void ingresoLosDatosDeNombreDescripcionTiempoEstimadoPrioridadYUnLegajoDeResponsableQueNoEsValido() {
        projectTask.setTitle("Create homepage");
        projectTask.setDescription("we need homepage");
        projectTask.setEstimatedTime(20d);
        projectTask.setEmployeeId(2l);
        projectTask.setTaskPriority(TaskPriority.MEDIUM);
        projectTask.setTaskType(TaskType.FEATURE);
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenThrow(new NotFoundException("error", HttpStatus.FOUND));
        try {
            taskDTO = projectTaskService.createTask(project.getId(), projectTask);
        } catch(InvalidEmployeeException e) {
            this.iee = e;
        }
    }

    @Then("No se puede crear la tarea por error de legajo invalido")
    public void noSePuedeCrearLaTareaPorErrorDeLegajoInvalido() {
        assertNotNull(this.iee);
    }

    @When("Ingreso los datos de nombre, descripcion y ningun otro dato mas")
    public void ingresoLosDatosDeNombreDescripcionYNingunOtroDatoMas() {
        projectTask.setTitle("Create homepage");
        projectTask.setDescription("we need homepage");
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        try {
            taskDTO = projectTaskService.createTask(project.getId(), projectTask);
        } catch(InvalidTaskException e) {
            this.ite = e;
        }
    }

    @Then("No se puede crear la tarea por error de datos invalidos")
    public void noSePuedeCrearLaTareaPorErrorDeDatosInvalidos() {
        assertNotNull(ite);
    }
}
