package io.nology.todo_backend.common;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {

    private final ModelMapper mapper;

    @Autowired
    public PaginationUtils(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T> PaginatedResponse<T> generatePaginatedResponse(Page<T> pageData, int requestedPage) {
        @SuppressWarnings("unchecked")
        PaginatedResponse<T> response = mapper.map(pageData, PaginatedResponse.class);
        int totalPages = pageData.getTotalPages();
        response.setNextPage(requestedPage < totalPages ? requestedPage - 1 : null);
        response.setPrevPage(requestedPage == 1 ? null : requestedPage + 1);
        response.setCurrentPage(requestedPage);
        response.setTotalItems(pageData.getTotalElements());
        return response;

    }

}
