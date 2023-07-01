package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.controller.DTO.TaskDTO;
import com.pca.projects.projects_module.exception.InvalidEmployeeException;
import com.pca.projects.projects_module.exception.InvalidTaskException;
import com.pca.projects.projects_module.exception.NotFoundException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.model.ProjectTask;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import com.pca.projects.projects_module.service.ProjectTaskService;
import com.pca.projects.projects_module.service.client.DTO.VersionDTO;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.service.client.SupportClientService;
import com.pca.projects.projects_module.utils.ProjectStatus;
import com.pca.projects.projects_module.utils.TaskPriority;
import com.pca.projects.projects_module.utils.TaskStatus;
import com.pca.projects.projects_module.utils.TaskType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
    private EnumConstantNotPresentException ecnpe;
    private NotFoundException nfe;

    private Exception e;
    @Before
    public void setup() {
        System.out.println("Before any test execution");
        MockitoAnnotations.initMocks(this);
        ite = null;
        projectTask = null;
        taskDTO = null;
        iee = null;
        e=null;
    }

    @Given("Quiero crear una nueva tarea en un proyecto al que pertenezco")
    public void quieroCrearUnaNuevaTareaEnUnProyectoAlQuePertenezco() {
        project = new Project();
        project.setId(1l);
        projectTask = new ProjectTask();
    }

    @When("Ingreso los datos de titulo, descripcion, tiempo estimado, tipo de tarea, responsable de la tarea y prioridad")
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

    @When("Ingreso los datos de titulo, descripcion, tiempo estimado, tipo de tarea, prioridad y un legajo de responsable que no es valido")
    public void ingresoLosDatosDeNombreDescripcionTiempoEstimadoPrioridadYUnLegajoDeResponsableQueNoEsValido() {
        projectTask.setTitle("Create homepage");
        projectTask.setDescription("we need homepage");
        projectTask.setEstimatedTime(20d);
        projectTask.setEmployeeId(2l);
        projectTask.setTaskPriority(TaskPriority.MEDIUM);
        projectTask.setTaskType(TaskType.FEATURE);
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenThrow(new NotFoundException("error", HttpStatus.FOUND, "error"));
        try {
            taskDTO = projectTaskService.createTask(project.getId(), projectTask);
        } catch(InvalidEmployeeException e) {
            this.iee = e;
        }
    }

    @Then("No se puede crear la tarea por error de legajo invalido")
    public void noSePuedeCrearLaTareaPorErrorDeLegajoInvalido() {
        assertNotNull(iee);
    }

    @When("Ingreso los datos de titulo, descripcion y ningun otro dato mas")
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

    @When("Ingreso los datos del titulo y la descripcion")
    public void ingresoLosDatosDelTituloYLaDescripcion() {
        projectTask.setTitle(taskDTO.getTitle());
        projectTask.setDescription(taskDTO.getDescription());
        Mockito.when(projectTaskRepository.findProjectTaskById(Mockito.any(Long.class))).thenReturn(projectTask);
        Mockito.when(projectTaskRepository.saveAndFlush(Mockito.any(ProjectTask.class))).thenReturn(projectTask);
        VersionDTO mockedVersion = new VersionDTO();
        mockedVersion.setVersionCode("2.0");
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenReturn(createResourceMock());
        Mockito.when(resourcesClientService.getResourceRegistersByTask(Mockito.any(Long.class),Mockito.any(Long.class),Mockito.any(Long.class))).thenReturn(new ArrayList<>());
        taskDTO = projectTaskService.updateTask(taskDTO,1l);
    }

    @Given("Quiero modificar el titulo y la descripcion de una tarea existente asociada a un proyecto")
    public void quieroModificarElTituloYLaDescripcionDeUnaTareaExistenteAsociadaAUnProyecto() {
        taskDTO = new TaskDTO();
        taskDTO.setTitle("Nuevo nombre");
        taskDTO.setDescription("nueva descripcion");
        projectTask = new ProjectTask();
        projectTask.setTaskType(TaskType.FEATURE);
        projectTask.setTaskPriority(TaskPriority.MEDIUM);
        projectTask.setProject(new Project());
        projectTask.getProject().setId(1l);
        projectTask.setStatus(TaskStatus.PENDING);
        projectTask.setEmployeeId(2l);
    }

    @Then("Se actualiza la informacion de la tarea con los datos ingresados")
    public void seActualizaLaInformacionDeLaTareaConLosDatosIngresados() {
        assertEquals("Nuevo nombre", taskDTO.getTitle());
        assertEquals("nueva descripcion", taskDTO.getDescription());
    }

    @Given("Quiero modificar el estado de una tarea existente asociada a un proyecto")
    public void quieroModificarElEstadoDeUnaTareaExistenteAsociadaAUnProyecto() {
        taskDTO = new TaskDTO();
        projectTask = new ProjectTask();
        taskDTO.setStatus("terminado");
    }

    @When("Ingreso un estado al que quiero que transicione la tarea que no es ninguno de los definidos por psa")
    public void ingresoElEstadoAlQueQuieroQueTransicioneLaTarea() {
/*        projectTask.setStatus(TaskStatus.PENDING);
        Mockito.when(projectTaskRepository.findProjectTaskById(Mockito.any(Long.class))).thenReturn(projectTask);
        Mockito.when(projectTaskRepository.saveAndFlush(Mockito.any(ProjectTask.class))).thenReturn(projectTask);
        VersionDTO mockedVersion = new VersionDTO();
        mockedVersion.setVersionCode("2.0");
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenReturn(createResourceMock());
        Mockito.when(resourcesClientService.getResourceRegistersByTask(Mockito.any(Long.class),Mockito.any(Long.class),Mockito.any(Long.class))).thenReturn(new ArrayList<>());*/
        Mockito.when(projectTaskRepository.findProjectTaskById(Mockito.any(Long.class))).thenReturn(projectTask);
        try {
            taskDTO = projectTaskService.updateTask(taskDTO,1l);
        } catch (EnumConstantNotPresentException e) {
            ecnpe = e;
        }
    }

    @Then("No se puede actualizar la tarea por error de estado invalido")
    public void NoSePuedeActualizarTareaPorErrorEstadoInvalido() {
        assertNotNull(ecnpe);
    }

    @Given("Quiero modificar el responsable de una tarea existente")
    public void quieroModificarElResponsableDeUnaTareaExistente() {
        taskDTO = new TaskDTO();
        projectTask = new ProjectTask();
        projectTask.setTaskType(TaskType.FEATURE);
        projectTask.setTaskPriority(TaskPriority.MEDIUM);
        projectTask.setProject(new Project());
        projectTask.getProject().setId(1l);
        projectTask.setStatus(TaskStatus.PENDING);
        projectTask.setEmployeeId(2l);
    }

    @When("Ingreso el legajo de un responsable que no es valido")
    public void ingresoElLegajoDeUnResponsableQueNoEsValido() {
        Mockito.when(projectTaskRepository.findProjectTaskById(Mockito.any(Long.class))).thenReturn(projectTask);
        VersionDTO mockedVersion = new VersionDTO();
        mockedVersion.setVersionCode("2.0");
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenThrow(new NotFoundException("error", HttpStatus.FOUND, "error"));
        try {
            taskDTO = projectTaskService.updateTask(taskDTO,1l);
        } catch (NotFoundException e) {
            nfe = e;
        }
    }

    @Given("Quiero eliminar una tarea existente asociada a un proyecto")
    public void quieroEliminarUnaTareaExistenteAsociadaAUnProyecto() {
        projectTask = new ProjectTask();
        taskDTO = new TaskDTO();
    }

    @When("Ingreso el id de la tarea que quiero eliminar")
    public void ingresoElIdDeLaTareaQueQuieroEliminar() {
        Mockito.doNothing().when(supportClientService).deleteTaskFromTicket(Mockito.any(Long.class));
        Mockito.doNothing().when(projectTaskRepository).deleteById(Mockito.any(Long.class));
        try {
            projectTaskService.deleteTask(1l);
        } catch (Exception e){
            e=e;
        }
    }

    @Then("Se elimina la tarea exitosamente")
    public void seEliminaLaTareaExitosamente() {
        assertNull(e);
    }
}
