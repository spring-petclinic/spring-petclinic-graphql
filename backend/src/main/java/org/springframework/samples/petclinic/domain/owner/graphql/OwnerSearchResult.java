package org.springframework.samples.petclinic.domain.owner.graphql;

import org.springframework.data.domain.Page;
import org.springframework.samples.petclinic.domain.owner.Owner;

import java.util.List;

public class OwnerSearchResult {
    private final Page<Owner> result;

    public OwnerSearchResult(Page<Owner> result) {
        this.result = result;
    }

    public PageInfo getPageInfo() {
        return new PageInfo(result);
    }

    public List<Owner> getOwners() {
        return result.getContent();
    }
}
