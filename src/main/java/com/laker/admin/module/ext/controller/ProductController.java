package com.laker.admin.module.ext.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.laker.admin.module.ext.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "△△△4.电商△△△")
@RequestMapping("/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 抢购商品
     * 运用工具（像 Postman 或者 Apache JMeter）模拟多个线程同时访问 /purchase/{productId} 接口。
     * 当多个线程同时抢购商品时，就可能出现超卖的情况。
     *
     * @param productId 商品ID
     * @return 抢购结果
     */
    @SaIgnore
    @GetMapping("/purchase/{productId}")
    @Operation(summary = "抢购商品")
    public String purchase(@PathVariable Long productId) {
        boolean success = productService.purchaseProduct(productId);
        if (success) {
            return "抢购成功";
        } else {
            return "库存不足，抢购失败";
        }
    }
}    