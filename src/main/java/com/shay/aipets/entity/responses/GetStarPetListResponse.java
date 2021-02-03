package com.shay.aipets.entity.responses;

import com.shay.aipets.dto.Pet;
import java.util.List;

public class GetStarPetListResponse {
    List<Pet> petList;
    boolean hasMore;
    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
    public List<Pet> getPetList() {
        return petList;
    }

    public void setPetList(List<Pet> petList) {
        this.petList = petList;
    }
}
