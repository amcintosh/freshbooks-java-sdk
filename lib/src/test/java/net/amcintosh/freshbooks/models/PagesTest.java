package net.amcintosh.freshbooks.models;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PagesTest {

    @Test
    public void BuildPages() {
        Pages pages = new Pages(1, 2, 15, 20);
        assertEquals(1, pages.getPage());
        assertEquals(2, pages.getPages());
        assertEquals(15, pages.getPerPage());
        assertEquals(20, pages.getTotal());
        assertEquals("Pages{page=1, pages=2, perPage=15, total=20}", pages.toString());
    }

}