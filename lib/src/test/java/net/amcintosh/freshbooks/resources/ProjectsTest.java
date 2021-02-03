package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Client;
import net.amcintosh.freshbooks.models.ClientList;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.ProjectList;
import org.junit.Test;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectsTest {

    @Test
    public void getProject() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects/654321", null)).thenReturn(mockRequest);

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
    public void getProject_notFound() throws IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response__not_found.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(404, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(404, e.statusCode);
            assertEquals("Requested resource could not be found.", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void getProject_badResponse() throws IOException {
        String jsonResponse = "stuff";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(500, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(500, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
    }

    @Test
    public void getProject_missingResponse() throws IOException {
        String jsonResponse = "{\"foo\": \"bar\"}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects/654321", null)).thenReturn(mockRequest);

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);

        try {
            projects.get(439000, projectId);
        } catch (FreshBooksException e) {
            assertEquals(200, e.statusCode);
            assertEquals("Returned an unexpected response", e.getMessage());
            assertEquals(0, e.errorNo);
            assertNull(e.field);
            assertNull(e.object);
            assertNull(e.value);
        }
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

}
