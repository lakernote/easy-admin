package com.laker.admin.framework.localmessage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.framework.localmessage.entity.LocalMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface LocalMessageMapper extends BaseMapper<LocalMessage> {

    // 按时间查询最近10条消息
    @Select("SELECT * FROM local_message WHERE status = #{status} ORDER BY create_time DESC LIMIT 10")
    List<LocalMessage> findByStatus(String status);

    // ------- 以下是非阻塞获取和更新数据的操作 -------
    // process_tag 每个实例启动后会生成一个唯一的值，作为标识，例如启动后 UUID
    // 可以再加个字段来识别哪个实例处理的消息，例如 instance_id ip
    // 非阻塞获取并更新数据
    @Update("UPDATE local_message SET process_tag = #{processTag} " +
            "WHERE id IN (SELECT id FROM (SELECT id FROM local_message WHERE status = #{status} " +
            "   AND process_tag IS NULL ORDER BY create_time DESC LIMIT 10) tmp) " + // 这里加个for update 感觉更稳，测试为准吧。
            "   AND status = #{status} AND process_tag IS NULL")
    int updateProcessTagByStatus(@Param("status") String status, @Param("processTag") String processTag);

    // 根据 process_tag 查询更新后的记录
    @Select("SELECT * FROM local_message WHERE process_tag = #{processTag}")
    List<LocalMessage> findByProcessTag(@Param("processTag") String processTag);
    // --------
}