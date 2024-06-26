package io.nology.todo_backend.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedResponse<T> {
    private List<T> content;
    private int currentPage;
    private long totalItems;
    private int totalPages;
    private int size;
    private Integer nextPage;
    private Integer prevPage;

    public PaginatedResponse() {
    }

}
