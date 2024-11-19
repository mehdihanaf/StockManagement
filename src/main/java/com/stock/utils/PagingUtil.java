package com.stock.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public final class PagingUtil {

    private PagingUtil() {}

    public static Pageable getPageable(String sortField, String defaultSortField, String order, Integer pageNum, Integer limitPerPage) {
        Sort.Direction direction = Objects.equals(order, "asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        if (StringUtils.isBlank(sortField)) {
            sortField = defaultSortField;
        }
        Sort sort = Sort.by(direction, sortField);
        return PageRequest.of(pageNum, limitPerPage)
                .withSort(sort);
    }
}
