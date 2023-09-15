package com.laker.admin.framework.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询参数
 */
@Data
public class PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 每页显示条数，默认 10
     */
    @Schema(description = "每页显示条数，默认 10")
    private long size = 10;
    /**
     * 当前页，默认1，第一页
     */
    @Schema(description = "当前页，默认1，第一页")
    private long current = 1;

    /**
     * 排序字段信息
     * 排序字段和排序类型
     * create_time desc,user_no asc.
     */
    @Schema(description = "排序字段和排序类型，例如：create_time desc,user_no asc.")
    private String orderBy;


    public Page toPage() {
        Page page = new Page();
        page.setCurrent(current);
        page.setSize(size);
        if (StrUtil.isNotBlank(orderBy)) {
            List<OrderItem> orders = new ArrayList<>();
            String[] orderItems = StrUtil.split(orderBy, ",");
            for (String orderItemStr : orderItems) {
                String[] orderAndSort = StrUtil.split(orderItemStr, " ");
                if (CollUtil.size(orderAndSort) != 2) {
                    continue;
                }
                String order = orderAndSort[0];
                String sort = orderAndSort[1];
                orders.add(new OrderItem(order, StrUtil.equalsIgnoreCase("ASC", sort)));
            }
            page.setOrders(orders);
        }
        return page;
    }
}
