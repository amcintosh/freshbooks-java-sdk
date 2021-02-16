package net.amcintosh.freshbooks.resources.api;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableList;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Project;
import net.amcintosh.freshbooks.models.ProjectList;
import net.amcintosh.freshbooks.models.builders.IncludesQueryBuilder;
import net.amcintosh.freshbooks.models.builders.PaginationQueryBuilder;
import net.amcintosh.freshbooks.models.builders.QueryBuilder;
import net.amcintosh.freshbooks.resources.Projects;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectResourceTest {

    @Test
    public void getProject_includes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321?include_overdue_fees=true",
                null)).thenReturn(mockRequest);

        IncludesQueryBuilder builder = new IncludesQueryBuilder().include("include_overdue_fees");

        long projectId = 654321;
        Projects projects = new Projects(mockedFreshBooksClient);
        Project project = projects.get(439000, projectId, builder);

        assertEquals(projectId, project.getId());
        assertEquals("Awesome Project", project.getTitle());
    }

    @Test
    public void getResource_notFound() throws IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_project_response__not_found.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(404, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

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
    public void getResource_badResponse() throws IOException {
        String jsonResponse = "stuff";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(500, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

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
    public void getResource_missingResponse() throws IOException {
        String jsonResponse = "{\"foo\": \"bar\"}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/project/654321", null)).thenReturn(mockRequest);

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
    public void listResource_noMatches() throws FreshBooksException, IOException {
        String jsonResponse = "{\"meta\": {\"sort\": [], \"total\": 0, \"per_page\": 1, " +
                "\"page\": 1, \"pages\": 0}, \"projects\": []}";
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects")).thenReturn(mockRequest);

        Projects projects = new Projects(mockedFreshBooksClient);
        ProjectList projectList = projects.list(439000);


        assertEquals(0, projectList.getPages().getTotal());
        assertEquals(ImmutableList.of(), projectList.getProjects());
    }

    @Test
    public void listResource_paged() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_projects_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects?page=2&per_page=3")).thenReturn(mockRequest);

        List<QueryBuilder> builders = ImmutableList.of(new PaginationQueryBuilder(2,3));
        Projects projects = new Projects(mockedFreshBooksClient);
        ProjectList projectList = projects.list(439000, builders);

        assertEquals(3, projectList.getPages().getTotal());
    }

    @Test
    public void listResource_includes() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_projects_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/projects/business/439000/projects?include_overdue_fees=true")).thenReturn(mockRequest);

        IncludesQueryBuilder includes = new IncludesQueryBuilder().include("include_overdue_fees");
        List<QueryBuilder> builders = ImmutableList.of(includes);

        Projects projects = new Projects(mockedFreshBooksClient);
        projects.list(439000, builders);
    }
}
