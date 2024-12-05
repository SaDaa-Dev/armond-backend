package com.dev.armond.common.reponse;

import org.springframework.data.domain.Page;

public class PaginatedResponse <T> extends ApiResponse{
    private int currentPage;
    private int totalPage;
    private long totalItems;

    public PaginatedResponse(String status, String message, Page<T> data, String error) {
        super(status, message, data, error);
        this.currentPage = data.getNumber();
        this.totalPage =  data.getTotalPages();
        this.totalItems = data.getTotalElements();
    }

    public static <T> PaginatedResponse<T> success(String message, Page<T> data) {
        return new PaginatedResponse<>("success", message, data, null);
    }
}
