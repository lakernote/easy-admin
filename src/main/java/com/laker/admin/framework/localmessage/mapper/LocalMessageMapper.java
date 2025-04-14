package com.laker.admin.framework.localmessage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.framework.localmessage.entity.LocalMessage;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LocalMessageMapper extends BaseMapper<LocalMessage> {

    // 按时间查询最近10条消息
    @Select("SELECT * FROM local_message WHERE status = #{status} ORDER BY create_time DESC LIMIT 10")
    List<LocalMessage> findByStatus(String status);
}