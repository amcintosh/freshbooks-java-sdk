package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.ProjectList;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectsTest {

    @Test
    public void getProject() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.get(439000, projectId);

        assertEquals(projectId, project.getId());
        assertEquals("Awesome Project", project.getTitle());
        assertEquals(ZonedDateTime.of(2020, 9, 13, 3,
                10, 13, 0, ZoneId.of("UTC")),
                project.getUpdatedAt()
        );
    }

    @Test
    public void listProjects() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_projects_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects")).thenReturn(mockRequest);

        Projects projects = new Projects(mockedFreshBooksClient);
        ProjectList projectList = projects.list(439000);

        ImmutableList<Integer> projectIds = ImmutableList.of(654321, 654322, 654323);

        assertEquals(3, projectList.getPages().getTotal());
        assertEquals("Awesome Project", projectList.getProjects().get(0).getTitle());
        for (int i=0; i<projectIds.size(); i++) {
            assertEquals(projectIds.get(i).longValue(), projectList.getProjects().get(i).getId());
        }
    }

    @Test
    public void createProject_dataMap() throws FreshBooksException, IOException {
        String title = "Some Project";
        Map<String, Object> data = ImmutableMap.of("title", title);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/projects/business/439000/project",
                ImmutableMap.of("project", data))
        ).thenReturn(mockRequest);


        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.create(439000, data);

        assertEquals(12345, project.getId());
        assertEquals(title, project.getTitle());
    }

    @Test
    public void createProject_projectObject() throws FreshBooksException, IOException {
        String title = "Some Project";
        Project data = new Project();
        data.setTitle(title);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/projects/business/439000/project",
                ImmutableMap.of("project", data.getContent()))
        ).thenReturn(mockRequest);


        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.create(439000, data);

        assertEquals(12345, project.getId());
        assertEquals(title, project.getTitle());
    }

    @Test
    public void updateProject_dataMap() throws FreshBooksException, IOException {
        long projectId = 12345;
        String title = "Some Project";
        Map<String, Object> data = ImmutableMap.of("title", title);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/projects/business/439000/project/12345",
                ImmutableMap.of("project", data))
        ).thenReturn(mockRequest);


        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.update(439000, projectId, data);

        assertEquals(projectId, project.getId());
        assertEquals(title, project.getTitle());
    }

    @Test
    public void updateProject_projectObject() throws FreshBooksException, IOException {
        long projectId = 12345;
        String title = "Some Project";
        Project data = new Project();
        data.setTitle(title);

        String jsonResponse = TestUtil.loadTestJson("fixtures/create_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/projects/business/439000/project/12345",
                ImmutableMap.of("project", data.getContent()))
        ).thenReturn(mockRequest);

        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.update(439000, projectId, data);

        assertEquals(projectId, project.getId());
        assertEquals(title, project.getTitle());
    }

    @Test
    public void deleteProject() throws FreshBooksException, IOException {
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(204, "");
        when(mockedFreshBooksClient.request(
                HttpMethods.DELETE,
                "/projects/business/439000/project/12345", null)
        ).thenReturn(mockRequest);

        Projects projects = new Projects(mockedFreshBooksClient);
        projects.delete(439000, 12345);
    }
}
