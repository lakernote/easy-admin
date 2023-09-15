package com.laker.admin.module.sys.pojo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class UserDto {
    private String nickName;
    private Integer sex;
    private String keyword;
    private String avatar;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(nickName), "nick_name", nickName);
        queryWrapper.eq(sex != null, "sex", sex);
        queryWrapper.and(StringUtils.isNotEmpty(keyword),
                likeQueryWrapper -> likeQueryWrapper.like("user_name", keyword)
                        .or().like("nick_name", keyword));
        return queryWrapper;
    }
}