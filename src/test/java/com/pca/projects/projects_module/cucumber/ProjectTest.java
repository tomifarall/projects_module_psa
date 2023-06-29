package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.controller.DTO.ProjectDTO;
import com.pca.projects.projects_module.exception.InvalidProjectException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.service.ProjectService;

import com.pca.projects.projects_module.service.client.DTO.VersionDTO;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.service.client.SupportClientService;
import com.pca.projects.projects_module.utils.ProjectStatus;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTest extends ProjectIntegrationServiceTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    SupportClientService supportClientService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ResourcesClientService resourcesClientService;
    private Project project;

    private ProjectDTO projectDTO;

    private InvalidProjectException ipe;
//    private InsufficientFundsException ife;
 //   private DepositNegativeSumException dnse;

    @Before
    public void setup() {
        System.out.println("Before any test execution");
        MockitoAnnotations.initMocks(this);
        this.ipe = null;
        this.project = null;
        this.projectDTO = null;
    }

    @After
    public void tearDown() {
        System.out.println("After all test execution");
    }

    @Given("^Quiero crear un nuevo proyecto$")
    public void create_new_project() {
        project = new Project();
    }

    @When("^Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin$")
    public void ingresoLosDatosDeNombreDescripcionFechaDeInicioYFechaDeFin() {
        //project = createProject(project,"project", "project description", 1l, new Date(), new Date());
        project.setDescription("project description");
        project.setStartDate(new Date());
        project.setEndDate(new Date());
        project.setName("project");
        project.setVersionId(1l);
        Project mockedProject = project;
        mockedProject.setId(0l);
        Mockito.when(projectRepository.findByVersionId(1l)).thenReturn(null);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(mockedProject);
        Mockito.when(supportClientService.updateVersion(Mockito.any(Long.class), Mockito.any(VersionDTO.class))).thenReturn(new VersionDTO());
        project = projectService.create(project);
    }

    @Then("^Se crea el proyecto exitosamente$")
    public void seCreaElProyectoExitosamente() {
        assertNotNull(project);
    }

    @When("^Ingreso los datos de nombre, descripcion, fecha de inicio y fecha de fin la cual es menor a la fecha de inicio$")
    public void ingresoLosDatosDeNombreDescripcionFechaDeInicioYFechaDeFinLaCualEsMenorALaFechaDeInicio() {
        project.setDescription("project description");
        Date biggestDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(biggestDate);
        c.add(Calendar.DATE, 10);
        biggestDate = c.getTime();
        project.setStartDate(biggestDate);
        project.setEndDate(new Date());
        project.setName("project");
        project.setVersionId(1l);
        try {
            project = projectService.create(project);
        } catch(InvalidProjectException e) {
            this.ipe = e;
        }
    }

    @Then("^No se puede crear el proyecto por error de fecha de fin menor a fecha de inicio$")
    public void noSePuedeCrearElProyectoPorFechaDeFinMenorAFechaDeInicio() {
        assertNotNull(this.ipe);
        assertEquals(this.ipe.getMessage(),"The end date can't be lower than the start date");
    }

    @When("^Ingreso los datos de nombre y descripcion y ningun otro dato mas$")
    public void ingresoLosDatosDeNombreYDescripcionYNingunOtroDatoMas() {
        project.setDescription("project description");
        project.setName("project");
        try {
            project = projectService.create(project);
        } catch(InvalidProjectException e) {
            this.ipe = e;
        }
    }

    @Then("^No se puede crear el proyecto por error de datos invalidos$")
    public void noSePuedeCrearElProyectoPorDatosInvalidos() {
        assertNotNull(this.ipe);
        assertEquals(this.ipe.getMessage(),"Project data is invalid.");
    }

    @Given("Quiero modificar un proyecto existente al que pertenezco")
    public void quieroModificarUnProyectoExistenteAlQuePertenezco() {
        project = new Project();
    }

    @Then("No se puede modificar el proyecto por error de fecha de fin menor a fecha de inicio")
    public void noSePuedeModificarElProyectoPorErrorDeFechaDeFinMenorAFechaDeInicio() {
        assertNotNull(this.ipe);
        assertEquals(this.ipe.getMessage(),"The end date can't be lower than the start date");
    }

    @Then("Se actualiza la informacion del proyecto con los datos ingresados")
    public void seActualizaLaInformacionDelProyectoConLosDatosIngresados() {
        assertEquals("Nuevo nombre", projectDTO.getName());
        assertEquals("nueva descripcion", projectDTO.getDescription());
    }

    @Given("Quiero modificar el nombre y la descripcion de un proyecto existente al que pertenezco")
    public void quieroModificarElNombreYLaDescripcionDeUnProyectoExistenteAlQuePertenezco() {
        project = createProjectMock("Nombre viejo", "descripcion vieja", 0l, new Date(), new Date(),ProjectStatus.DEVELOPING);
        projectDTO = new ProjectDTO();
        projectDTO.setName("Nuevo nombre");
        projectDTO.setDescription("nueva descripcion");
    }

    @When("Ingreso los datos del nombre y la descripcion")
    public void ingresoLosDatosDelNombreYLaDescripcion() {
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        VersionDTO mockedVersion = new VersionDTO();
        mockedVersion.setVersionCode("2.0");
        Mockito.when(supportClientService.getVersion(Mockito.any(Long.class))).thenReturn(mockedVersion);
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenReturn(createResourceMock());
        projectDTO = projectService.updateProject(1l, projectDTO);
    }

    private Project createProjectMock(String name, String description, Long version, Date startDate, Date endDate, ProjectStatus status) {
        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setVersionId(version);
        project.setStatus(status);
        return project;
    }

    @Given("Quiero modificar el estado de un proyecto existente al que pertenezco")
    public void quieroModificarElEstadoDeUnProyectoExistenteAlQuePertenezco() {
        project = createProjectMock("rappiya", "descripcion", 1l, new Date(), new Date(),ProjectStatus.DEVELOPING);
        projectDTO = new ProjectDTO();
        projectDTO.setStatus(ProjectStatus.IMPLEMENTATION.getId());
    }

    @When("Ingreso el estado al que quiero que transicione")
    public void ingresoElEstadoAlQueQuieroQueTransicione() {
        Mockito.when(projectRepository.findProjectById(Mockito.any(Long.class))).thenReturn(project);
        project.setStatus(ProjectStatus.IMPLEMENTATION);
        Mockito.when(projectRepository.save(Mockito.any(Project.class))).thenReturn(project);
        VersionDTO mockedVersion = new VersionDTO();
        mockedVersion.setVersionCode("2.0");
        Mockito.when(supportClientService.getVersion(Mockito.any(Long.class))).thenReturn(mockedVersion);
        Mockito.when(resourcesClientService.getResource(Mockito.any(Long.class))).thenReturn(createResourceMock());
        projectDTO = projectService.updateProject(1l, projectDTO);
    }

    @Then("Se actualiza el estado del proyecto exitosamente")
    public void seActualizaElEstadoDelProyectoExitosamente() {
        assertEquals("implementation", projectDTO.getStatus());
    }
}
