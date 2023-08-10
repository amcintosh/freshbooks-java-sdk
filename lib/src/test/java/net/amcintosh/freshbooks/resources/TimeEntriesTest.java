package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.util.Key;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TimeEntriesTest {

    @Test
    public void getTimeEntry() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_time_entry_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/timetracking/business/439000/time_entries/123456", null)).thenReturn(mockRequest);

        long timeEntryId = 123456;
        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntry timeEntry = timeEntries.get(439000, timeEntryId);

        assertEquals(timeEntryId, timeEntry.getId());
        assertEquals(true, timeEntry.isActive());
        assertEquals(true, timeEntry.isBillable());
        assertEquals(false, timeEntry.isBilled());
        assertEquals(56789, timeEntry.getClientId());
        assertEquals(ZonedDateTime.of(2023, 8, 10, 10,
                        34, 13, 0, ZoneId.of("UTC")),
                timeEntry.getCreatedAt()
        );
        assertEquals(Duration.ofSeconds(9840), timeEntry.getDuration());
        assertEquals(9840, timeEntry.getDuration().getSeconds());
        assertEquals(65432, timeEntry.getIdentityId());
        assertEquals(false, timeEntry.isInternal());
        assertEquals(true, timeEntry.isLogged());
        assertEquals(ZonedDateTime.of(2023, 8, 9, 14,
                        56, 50, 0, ZoneId.of("America/Toronto")),
                timeEntry.getLocalStartedAt()
        );
        assertEquals(ZoneId.of("America/Toronto"), timeEntry.getLocalTimezone());
        assertEquals("Slow day", timeEntry.getNote());
        assertEquals("", timeEntry.getPendingClient());
        assertEquals("", timeEntry.getPendingProject());
        assertEquals("", timeEntry.getPendingTask());
        assertEquals(654321, timeEntry.getProjectId());
        assertEquals(0, timeEntry.getRetainerId());
        assertEquals(4054453, timeEntry.getServiceId());
        assertEquals(ZonedDateTime.of(2023, 8, 9, 19,
                        56, 50, 0, ZoneId.of("UTC")),
                timeEntry.getStartedAt()
        );
        assertEquals(159361, timeEntry.getTaskId());
    }

    @Test
    public void listTimeEntries() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_time_entries_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/timetracking/business/439000/time_entries")).thenReturn(mockRequest);

        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntryList timeEntryList = timeEntries.list(439000);

        ImmutableList<Integer> timeEntryIds = ImmutableList.of(123456, 123457);

        assertEquals(2, timeEntryList.getPages().getTotal());
        assertEquals("Slow day", timeEntryList.getTimeEntries().get(0).getNote());
        for (int i=0; i<timeEntryIds.size(); i++) {
            assertEquals(timeEntryIds.get(i).longValue(), timeEntryList.getTimeEntries().get(i).getId());
        }
    }


    @Test
    public void createTimeEntry_dataMap() throws FreshBooksException, IOException {
        String note = "Slow day";
        Map<String, Object> data = ImmutableMap.of("note", note);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_time_entry_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/timetracking/business/439000/time_entries",
                ImmutableMap.of("time_entry", data))
        ).thenReturn(mockRequest);


        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntry timeEntry = timeEntries.create(439000, data);

        assertEquals(123456, timeEntry.getId());
        assertEquals(note, timeEntry.getNote());
    }

    @Test
    public void createTimeEntry_timeEntryObject() throws FreshBooksException, IOException {
        String note = "Slow day";
        TimeEntry data = new TimeEntry();
        data.setNote(note);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_time_entry_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/timetracking/business/439000/time_entries",
                ImmutableMap.of("time_entry", data.getContent()))
        ).thenReturn(mockRequest);


        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntry timeEntry = timeEntries.create(439000, data);

        assertEquals(123456, timeEntry.getId());
        assertEquals(note, timeEntry.getNote());
    }

    @Test
    public void updateTimeEntry_dataMap() throws FreshBooksException, IOException {
        long timeEntryId = 123456;
        String note = "Slow day";
        Map<String, Object> data = ImmutableMap.of("note", note);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_time_entry_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/timetracking/business/439000/time_entries/123456",
                ImmutableMap.of("time_entry", data))
        ).thenReturn(mockRequest);

        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntry timeEntry = timeEntries.update(439000, timeEntryId, data);

        assertEquals(123456, timeEntry.getId());
        assertEquals(note, timeEntry.getNote());
    }

    @Test
    public void updateTimeEntry_timeEntryObject() throws FreshBooksException, IOException {
        long timeEntryId = 123456;
        String note = "Slow day";
        TimeEntry data = new TimeEntry();
        data.setNote(note);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_time_entry_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/timetracking/business/439000/time_entries/123456",
                ImmutableMap.of("time_entry", data.getContent()))
        ).thenReturn(mockRequest);

        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        TimeEntry timeEntry = timeEntries.update(439000, timeEntryId, data);

        assertEquals(123456, timeEntry.getId());
        assertEquals(note, timeEntry.getNote());
    }

    @Test
    public void deleteTimeEntry() throws FreshBooksException, IOException {
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(204, "");
        when(mockedFreshBooksClient.request(
                HttpMethods.DELETE,
                "/timetracking/business/439000/time_entries/123456", null)
        ).thenReturn(mockRequest);

        TimeEntries timeEntries = new TimeEntries(mockedFreshBooksClient);
        timeEntries.delete(439000, 123456);
    }
}
