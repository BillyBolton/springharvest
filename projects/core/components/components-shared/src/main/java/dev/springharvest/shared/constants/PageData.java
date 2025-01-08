package dev.springharvest.shared.constants;

import lombok.Data;

import java.util.List;

@Data
public class PageData<D> {
    private List<D> data;
    private int currentPage;
    private int currentPageCount;
    private int pageSize;
    private long total;
    private int totalPages;

    public PageData(List<D> data, int currentPage, int pageSize, long total, int totalPages, int currentPageCount) {
        this.data = data;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPages = totalPages;
        this.currentPageCount = currentPageCount;
    }

    public String toString() {
        return "PageData(dtos=" + this.getData() + ", currentPage=" + this.getCurrentPage() + ", currentPageCount=" + this.getCurrentPageCount() + ", pageSize=" + this.getPageSize() + ", total=" + this.getTotal() + ", totalPages=" + this.getTotalPages() + ")";
    }
}
