package net.amcintosh.freshbooks.models;

/**
 * Paginated list resources will contain a pages object.
 */
public class Pages {
    private int page;
    private int pages;
    private int perPage;
    private int total;

    /**
     *
     * @param page The current page of results
     * @param pages The total number of pages
     * @param perPage The number of results per page
     * @param total The total number of results
     */
    public Pages(int page, int pages, int perPage, int total) {
        this.page = page;
        this.pages = pages;
        this.perPage = perPage;
        this.total = total;
    }

    /**
     *
     * @return The current page of results
     */
    public int getPage() {
        return page;
    }

    /**
     *
     * @return The total number of pagess
     */
    public int getPages() {
        return pages;
    }

    /**
     *
     * @return The number of results per page
     */
    public int getPerPage() {
        return perPage;
    }

    /**
     *
     * @return The total number of results
     */
    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Pages{" +
                "page=" + page +
                ", pages=" + pages +
                ", perPage=" + perPage +
                ", total=" + total +
                '}';
    }
}
