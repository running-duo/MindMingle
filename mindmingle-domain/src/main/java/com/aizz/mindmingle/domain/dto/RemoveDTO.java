package com.aizz.mindmingle.domain.dto;

import java.util.List;

/**
 * @author zhangyuliang
 */
public class RemoveDTO {

    public List<Long> ids;

    // Constructors
    public RemoveDTO() {
    }

    public RemoveDTO(List<Long> ids) {
        this.ids = ids;
    }

    // Getter
    public List<Long> getIds() {
        return ids;
    }

    // Setter
    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    // Builder
    public static RemoveDTOBuilder builder() {
        return new RemoveDTOBuilder();
    }

    public static class RemoveDTOBuilder {
        private List<Long> ids;

        RemoveDTOBuilder() {
        }

        public RemoveDTOBuilder ids(List<Long> ids) {
            this.ids = ids;
            return this;
        }

        public RemoveDTO build() {
            return new RemoveDTO(ids);
        }
    }
}
