package com.laker.admin.framework.ext.mvc;

import lombok.Builder;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
public class PageRequest {
    private int page;
    private int size;
    private List<Order> orders;

    @Builder
    @ToString
    public static class Order {
        private String property;
        private String direction;
    }
}