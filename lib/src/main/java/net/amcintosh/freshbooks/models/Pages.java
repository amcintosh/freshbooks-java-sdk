package net.amcintosh.freshbooks.models;

public class Pages {
    private int page;
    private int pages;
    private int perPage;
    private int total;

    public Pages(int page, int pages, int perPage, int total) {
        this.page = page;
        this.pages = pages;
        this.perPage = perPage;
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

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
