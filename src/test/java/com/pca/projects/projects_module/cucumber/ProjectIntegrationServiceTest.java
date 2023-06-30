package com.pca.projects.projects_module.cucumber;

import com.pca.projects.projects_module.repository.ProjectRepository;
import com.pca.projects.projects_module.repository.ProjectTaskRepository;
import com.pca.projects.projects_module.service.ProjectService;
import com.pca.projects.projects_module.service.client.DTO.ResourceDTO;
import com.pca.projects.projects_module.service.client.ResourcesClientService;
import com.pca.projects.projects_module.service.client.SupportClientService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@CucumberContextConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProjectIntegrationServiceTest {

    @SpyBean
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private SupportClientService supportClientService;

    @MockBean
    private ResourcesClientService resourcesClientService;

    @MockBean
    private ProjectTaskRepository projectTaskRepository;

    public ResourceDTO createResourceMock() {
        ResourceDTO mockedResource = new ResourceDTO();
        mockedResource.setId(1l);
        mockedResource.setName("Jorgita");
        mockedResource.setLastname("ToyHarta");
        return mockedResource;
    }

    public List<Date> getStartAndEndDates() {
        List<Date> list = new ArrayList<>();
        Date biggestDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(biggestDate);
        c.add(Calendar.DATE, 10);
        list.addAll(Arrays.asList(new Date(), c.getTime()));
        return list;
    }

/*    public Project createProject(Project project, String name, String description, Long version, Date startDate, Date endDate){
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
        project.setVersionId(version);
        Mockito.when(projectRepository.findByVersionId(1l)).thenReturn(null);
        return projectService.create(project);
    }*/

}
