package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.*;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TasksTest {

    @Test
    public void getTask() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/projects/tasks/159361", null)).thenReturn(mockRequest);

        long taskId = 159361;
        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.get("ABC123", taskId);

        assertEquals(taskId, task.getId());
        assertEquals("Piloting", task.getName());
        assertEquals("Piloting the project based on the expectations of the executive", task.getDescription());
        assertEquals(taskId, task.getTaskId());
        assertEquals(new BigDecimal("100.00"), task.getRate().getAmount());
        assertEquals("CAD", task.getRate().getCode());
        assertTrue(task.isBillable());
        assertEquals(ZonedDateTime.of(2017, 7, 24, 13,
                        39, 21, 0, ZoneId.of("UTC")),
                task.getUpdated());
        assertEquals(VisState.ACTIVE, task.getVisState());
    }

    @Test
    public void listTasks() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_tasks_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/projects/tasks")).thenReturn(mockRequest);

        Tasks tasks = new Tasks(mockedFreshBooksClient);
        TaskList taskList = tasks.list("ABC123");

        assertEquals(3, taskList.getPages().getTotal());
        assertEquals(159361, taskList.getTasks().get(0).getId());
        assertEquals("Piloting", taskList.getTasks().get(0).getName());
    }

    @Test
    public void createTask_dataMap() throws FreshBooksException, IOException {
        String taskName = "Piloting";
        Map<String, Object> data = ImmutableMap.of("name", taskName);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/projects/tasks",
                ImmutableMap.of("task", data))
        ).thenReturn(mockRequest);

        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.create("ABC123", data);

        assertEquals(159361, task.getId());
        assertEquals(taskName, task.getName());
    }

    @Test
    public void createTask_taskObject() throws FreshBooksException, IOException {
        String taskName = "Piloting";
        Task data = new Task();
        data.setName(taskName);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/projects/tasks",
                ImmutableMap.of("task", data.getContent()))
        ).thenReturn(mockRequest);

        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.create("ABC123", data);

        assertEquals(159361, task.getId());
        assertEquals(taskName, task.getName());
    }

    @Test
    public void updateTask_dataMap() throws FreshBooksException, IOException {
        long taskId = 159361;
        Map<String, Object> data = ImmutableMap.of("description", "New description");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        jsonResponse = jsonResponse.replace("Piloting the project based on the expectations of the executive", "New description");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/projects/tasks/159361",
                ImmutableMap.of("task", data))
        ).thenReturn(mockRequest);

        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.update("ABC123", taskId, data);

        assertEquals(taskId, task.getId());
        assertEquals("New description", task.getDescription());
    }

    @Test
    public void updateTask_taskObject() throws FreshBooksException, IOException {
        long taskId = 159361;
        Task data = new Task();
        data.setDescription("New description");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        jsonResponse = jsonResponse.replace("Piloting the project based on the expectations of the executive", "New description");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/projects/tasks/159361",
                ImmutableMap.of("task", data.getContent()))
        ).thenReturn(mockRequest);

        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.update("ABC123", taskId, data);

        assertEquals(taskId, task.getId());
        assertEquals("New description", task.getDescription());
    }

    @Test
    public void deleteTask() throws FreshBooksException, IOException {
        long taskId = 159361;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_task_response.json");
        jsonResponse = jsonResponse.replace("\"vis_state\": 0", "\"vis_state\": 1");

        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED.getValue());

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/projects/tasks/159361",
                ImmutableMap.of("task", data))
        ).thenReturn(mockRequest);


        Tasks tasks = new Tasks(mockedFreshBooksClient);
        Task task = tasks.delete("ABC123", taskId);

        assertEquals(taskId, task.getId());
        assertEquals(VisState.DELETED, task.getVisState());
    }
}
