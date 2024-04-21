package com.chamalwr.secureapp.model;

import java.util.List;

public class OwnersResponse {
    private Integer page;
    private Integer limit;
    private Integer skip;
    private Integer totalCount;
    private List<Owner> owners;

    public OwnersResponse() {
    }

    public OwnersResponse(Integer page, Integer limit, Integer skip, Integer totalCount, List<Owner> owners) {
        this.page = page;
        this.limit = limit;
        this.skip = skip;
        this.totalCount = totalCount;
        this.owners = owners;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
