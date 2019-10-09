package com.mbyte.easy.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * 定义分页信息类
 * @author 申劭明
 */
@Data
public class PageInfo<T> {
    /**
     * 每页显示的条数
     */
    private long pageSize;
    /**
     * 当前页
     */
    private long pageNum;
    /**
     * 总页数
     */
    private long total;
    /**
     * 下一页
     */
    private long nextPage;
    /**
     * 上一页
     */
    private long prePage;
    /**
     * 尾页
     */
    private long pages;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private List<T> list;

    public PageInfo(IPage<T> page) {
        this.pageSize = page.getSize();
        this.pageNum = page.getCurrent();
        this.total = page.getTotal();
        this.pages = page.getPages();
        if (this.pageNum > 1) {
            this.prePage = this.pageNum - 1;
        }

        if (this.pageNum < this.pages) {
            this.nextPage = this.pageNum + 1;
        }

        this.list = page.getRecords();
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        judgePageBoudary();
    }

    private void judgePageBoudary() {
        this.isFirstPage = this.pageNum == 1;
        this.isLastPage = this.pageNum == this.pages || this.pages == 0;
        this.hasPreviousPage = this.pageNum > 1;
        this.hasNextPage = this.pageNum < this.pages;
    }

    public boolean isIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

}
