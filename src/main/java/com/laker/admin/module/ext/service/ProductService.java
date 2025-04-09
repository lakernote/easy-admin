package com.laker.admin.module.ext.service;

import com.laker.admin.module.ext.entity.Product;
import com.laker.admin.module.ext.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    //    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public boolean purchaseProduct(Long productId) {
        log.info("开始购买商品，商品ID: {}", productId);
        // 查询商品库存
        Product product = productMapper.selectById(productId);
        // 如果商品不存在或者库存不足，返回失败
        if (product == null || product.getStock() <= 0) {
            log.warn("商品不存在或者库存不足，商品ID: {}", productId);
            return false;
        }
        // 更新商品库存,如果库存不足，更新失败
        final int updateCount = productMapper.purchaseProduct(productId);
        if (updateCount > 0) {
            log.info("购买成功，商品ID: {}", productId);
            return true;
        } else {
            log.warn("购买失败，商品ID: {}", productId);
        }
        return false;
    }
}