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

    // ------- 任务并发抢占处理 以下是非阻塞获取和更新数据的操作 -------
    // process_tag 每个实例启动后会生成一个唯一的值，作为标识，例如启动后 UUID
    // 可以再加个字段来识别哪个实例处理的消息，例如 instance_id ip
    // 非阻塞获取并更新数据
    // SELECT id FROM ... LIMIT 10 是 非锁定操作，意味着多个线程可能在执行 UPDATE 之前都读到了一样的10条数据。
    //然后两个线程都进入 UPDATE，虽然加了 locked_by IS NULL 条件，能部分避免冲突，但不能保证并发线程严格每个都能抢到10条任务。
    @Update("UPDATE local_message SET process_tag = #{processTag} " +
            "WHERE id IN (SELECT id FROM (SELECT id FROM local_message WHERE status = #{status} " +          // for update skip locked
            "   AND process_tag IS NULL ORDER BY create_time DESC LIMIT 10 for update skip locked ) tmp) " + //  查询返回查询结果，但忽略有行锁的记录
            "   AND status = #{status} AND process_tag IS NULL")
    int updateProcessTagByStatus(@Param("status") String status, @Param("processTag") String processTag);

    // 根据 process_tag 查询更新后的记录
    @Select("SELECT * FROM local_message WHERE process_tag = #{processTag}")
    List<LocalMessage> findByProcessTag(@Param("processTag") String processTag);
    // --------

    /**
     * <pre>
     *     适用mysql8
     *     <select id="selectAndLockMessages" resultType="com.example.LocalMessage">
     *   SELECT *
     *   FROM local_message
     *   WHERE status = 'PENDING' AND locked_by IS NULL
     *   ORDER BY create_time DESC
     *   LIMIT #{limit}
     *   FOR UPDATE SKIP LOCKED  // 自动跳过被锁定任务	SKIP LOCKED 避免阻塞或死锁
     * </select>
     *
     * <update id="lockMessages">
     *   <foreach collection="list" item="msg" separator=";">
     *     UPDATE local_message
     *     SET locked_by = #{workerId}, locked_time = NOW()
     *     WHERE id = #{msg.id}
     *   </foreach>
     * </update>
     *
     *    //  @Transactional
     *     public List<LocalMessage> fetchAndLockMessages(String workerId, int limit) {
     *         List<LocalMessage> messages = localMessageMapper.selectAndLockMessages(limit);
     *         if (!messages.isEmpty()) {
     *             localMessageMapper.lockMessages(messages, workerId);
     *         }
     *         return messages;
     *     }
     *     </pre>
     */
}