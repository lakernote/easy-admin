package com.laker.admin.module.ext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laker.admin.module.ext.entity.ExtLog;
import com.laker.admin.module.ext.vo.LogStatisticsTop10Vo;
import com.laker.admin.module.ext.vo.LogStatisticsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 日志 Mapper 接口
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
public interface ExtLogMapper extends BaseMapper<ExtLog> {


    @Select("SELECT\n" +
            "    DATE_FORMAT(t.create_time, '%Y-%m-%d') AS date,\n" +
            "    COUNT(*) AS value\n" +
            "FROM\n" +
            "    ext_log t\n" +
            "WHERE\n" +
            "    t.create_time >= CURDATE() - INTERVAL 7 DAY\n" +
            "GROUP BY\n" +
            "    date")
    List<LogStatisticsVo> selectStatistics7Day();

    @Select("SELECT\n" +
            "    w.ip,\n" +
            "    city,\n" +
            "    COUNT(*) AS value\n" +
            "FROM\n" +
            "    ext_log w\n" +
            "WHERE\n" +
            "    w.create_time >= CURDATE() - INTERVAL 1 DAY\n" +
            "GROUP BY\n" +
            "    w.ip, city\n" +
            "ORDER BY\n" +
            "    value DESC\n" +
            "LIMIT 10\n")
    List<LogStatisticsTop10Vo> selectStatisticsVisitsTop10IP();

    @Select("SELECT count(DISTINCT ip) from ext_log")
    int selectDistinctIp();

}
