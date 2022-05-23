package com.cdu.lys.graduation.commons.result;

/**
 * @author liyinsong
 * @date 2022/2/8 11:32
 */
public class PageResult<T> {

    /**
     * 页数
     */
    private int pageNum;

    /**
     * 页面大小
     */
    private int pageSize;

    /**
     * 数据总数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 页面数据
     */
    private T data;

    public PageResult() {
    }

    public PageResult(T data) {
        this.data = data;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", total=" + total +
                ", pages=" + pages +
                ", data=" + data +
                '}';
    }
}
