package org.springframework.samples.petclinic.graphql;

import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.model.Owner;

public class PageInfo {
    private final Page<Owner> result;

    public PageInfo(Page<Owner> result) {
        this.result = result;
    }

    public int getPageNumber() {
        return result.getNumber();
    }

    public int getTotalPages() {
        return result.getTotalPages();
    }

    public long getTotalCount() {
        return result.getTotalElements();
    }

    public boolean getHasNext() {
        return result.hasNext();
    }

    public boolean getHasPrev() {
        return result.hasPrevious();
    }

    public Integer getNextPage() {
        if (result.hasNext()) {
            return result.getNumber() + 1;
        }

        return null;
    }

    public Integer getPrevPage() {
        if (result.hasPrevious()) {
            return result.getNumber() - 1;
        }

        return null;
    }
}
