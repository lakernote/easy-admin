package com.laker.admin.module.ext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.module.ext.entity.Product;
import org.apache.ibatis.annotations.Update;

public interface ProductMapper extends BaseMapper<Product> {
    // 更新商品库存,防止超卖
    @Update("update product set stock = stock - 1 where id = #{productId} and stock > 0")
    int purchaseProduct(Long productId);
}
