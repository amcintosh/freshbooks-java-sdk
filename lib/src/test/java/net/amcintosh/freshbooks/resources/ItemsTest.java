package net.amcintosh.freshbooks.resources;

import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpRequest;
import com.google.common.collect.ImmutableMap;
import net.amcintosh.freshbooks.FreshBooksClient;
import net.amcintosh.freshbooks.FreshBooksException;
import net.amcintosh.freshbooks.TestUtil;
import net.amcintosh.freshbooks.models.Item;
import net.amcintosh.freshbooks.models.ItemList;
import net.amcintosh.freshbooks.models.VisState;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemsTest {

    @Test
    public void getItem() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/items/items/4331", null)).thenReturn(mockRequest);

        long itemId = 4331;
        Items items = new Items(mockedFreshBooksClient);
        Item item = items.get("ABC123", itemId);

        assertEquals(itemId, item.getId());
        assertEquals(itemId, item.getItemId());
        assertEquals("ACM123", item.getAccountingSystemId());
        assertEquals("It's Cheap", item.getDescription());
        assertEquals("", item.getInventory());
        assertEquals("A Cheap Item", item.getName());
        assertEquals(new BigDecimal("100"), item.getQuantity());
        assertEquals("SQ1234", item.getSku());
        assertEquals(7840, item.getTax1());
        assertEquals(0, item.getTax2());
        assertEquals(new BigDecimal("1.00"), item.getUnitCost().getAmount());
        assertEquals("CAD", item.getUnitCost().getCode());
        assertEquals(ZonedDateTime.of(2021, 6, 13, 14,
                45, 20, 0, ZoneId.of("UTC")),
                item.getUpdated()
        );
        assertEquals(VisState.ACTIVE, item.getVisState());
    }

    @Test
    public void listItems() throws FreshBooksException, IOException {
        String jsonResponse = TestUtil.loadTestJson("fixtures/list_items_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(HttpMethods.GET,
                "/accounting/account/ABC123/items/items")).thenReturn(mockRequest);

        Items items = new Items(mockedFreshBooksClient);
        ItemList itemList = items.list("ABC123");

        assertEquals(3, itemList.getPages().getTotal());
        assertEquals(4331, itemList.getItems().get(0).getId());
        assertEquals("A Cheap Item", itemList.getItems().get(0).getName());
    }

    @Test
    public void createItem_dataMap() throws FreshBooksException, IOException {
        String itemName = "A Cheap Item";
        Map<String, Object> data = ImmutableMap.of("name", itemName);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/items/items",
                ImmutableMap.of("item", data))
        ).thenReturn(mockRequest);

        Items items = new Items(mockedFreshBooksClient);
        Item item = items.create("ABC123", data);

        assertEquals(4331, item.getId());
        assertEquals(itemName, item.getName());
    }

    @Test
    public void createItem_itemObject() throws FreshBooksException, IOException {
        String itemName = "A Cheap Item";
        Item data = new Item();
        data.setName(itemName);

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.POST,
                "/accounting/account/ABC123/items/items",
                ImmutableMap.of("item", data.getContent()))
        ).thenReturn(mockRequest);

        Items items = new Items(mockedFreshBooksClient);
        Item item = items.create("ABC123", data);

        assertEquals(4331, item.getId());
        assertEquals(itemName, item.getName());
    }

    @Test
    public void updateItem_dataMap() throws FreshBooksException, IOException {
        long itemId = 4331;
        Map<String, Object> data = ImmutableMap.of("description", "New description");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        jsonResponse = jsonResponse.replace("It's Cheap", "New description");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/items/items/4331",
                ImmutableMap.of("item", data))
        ).thenReturn(mockRequest);

        Items items = new Items(mockedFreshBooksClient);
        Item item = items.update("ABC123", itemId, data);

        assertEquals(itemId, item.getId());
        assertEquals("New description", item.getDescription());
    }

    @Test
    public void updateItem_itemObject() throws FreshBooksException, IOException {
        long itemId = 4331;
        Item data = new Item();
        data.setDescription("New description");

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        jsonResponse = jsonResponse.replace("It's Cheap", "New description");
        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/items/items/4331",
                ImmutableMap.of("item", data.getContent()))
        ).thenReturn(mockRequest);

        Items items = new Items(mockedFreshBooksClient);
        Item item = items.update("ABC123", itemId, data);

        assertEquals(itemId, item.getId());
        assertEquals("New description", item.getDescription());
    }

    @Test
    public void deleteItem() throws FreshBooksException, IOException {
        long itemId = 4331;

        String jsonResponse = TestUtil.loadTestJson("fixtures/get_item_response.json");
        jsonResponse = jsonResponse.replace("\"vis_state\": 0", "\"vis_state\": 1");

        ImmutableMap<String, Object> data = ImmutableMap.of("vis_state", VisState.DELETED.getValue());

        FreshBooksClient mockedFreshBooksClient = mock(FreshBooksClient.class);
        HttpRequest mockRequest = TestUtil.buildMockHttpRequest(200, jsonResponse);
        when(mockedFreshBooksClient.request(
                HttpMethods.PUT,
                "/accounting/account/ABC123/items/items/4331",
                ImmutableMap.of("item", data))
        ).thenReturn(mockRequest);


        Items items = new Items(mockedFreshBooksClient);
        Item item = items.delete("ABC123", itemId);

        assertEquals(itemId, item.getId());
        assertEquals(VisState.DELETED, item.getVisState());
    }
}
