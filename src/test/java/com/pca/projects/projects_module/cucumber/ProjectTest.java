package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.exception.InvalidProjectException;
import com.pca.projects.projects_module.model.Project;
import com.pca.projects.projects_module.service.ProjectService;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTest extends ProjectIntegrationServiceTest {

    @Autowired
    private ProjectService projectService;

    private Project project;
    private InvalidProjectException ipe;
/*    private InsufficientFundsException ife;
    private DepositNegativeSumException dnse;

    @Before
    public void setup() {
        System.out.println("Before any test execution");
    }

    @Given("^Account with a balance of (\\d+)$")
    public void account_with_a_balance_of(int balance)  {
        account = createAccount(Double.valueOf(balance));
    }

    @When("^Trying to withdraw (\\d+)$")
    public void trying_to_withdraw(int sum) {
        try {
            withdraw(account, Double.valueOf(sum));
        } catch (InsufficientFundsException ife) {
            this.ife = ife;
        }
    }

    @When("^Trying to deposit (.*)$")
    public void trying_to_deposit(int sum) {
        try {
            deposit(account, Double.valueOf(sum));
        } catch (DepositNegativeSumException dnse) {
            this.dnse = dnse;
        }
    }

    @Then("^Account balance should be (\\d+)$")
    public void account_balance_should_be(int balance) {
        Account accountToCheck = accountService.findById(account.getCbu()).get();
        assertEquals(Double.valueOf(balance), accountToCheck.getBalance());
    }

    @Then("^Operation should be denied due to insufficient funds$")
    public void operation_should_be_denied_due_to_insufficient_funds() {
        assertNotNull(ife);
    }

    @Then("^Operation should be denied due to negative sum$")
    public void operation_should_be_denied_due_to_negative_sum() {
        assertNotNull(dnse);
    }

    @And("^Account balance should remain (\\d+)$")
    public void account_balance_should_remain(int balance) {
        Account accountToCheck = accountService.findById(account.getCbu()).get();
        assertEquals(Double.valueOf(balance), accountToCheck.getBalance());
    }*/

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
        project.setDescription("project description");
        project.setStartDate(new Date());
        project.setEndDate(new Date());
        project.setName("project");
        project.setVersionId(1l);
        project = projectService.create(project);
    }

    @Then("^Se crea el proyecto exitosamente$")
    public void seCreaElProyectoExitosamente() {
        project = this.projectService.create(project);
        assertNotNull(this.projectService.getProject(project.getId()));
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

    @Then("^No se puede crear el proyecto por fecha de inicio mayor a fecha de fin$")
    public void noSePuedeCrearElProyectoPorFechaDeInicioMayorAFechaDeFin() {
        assertNotNull(this.ipe);
    }
}
