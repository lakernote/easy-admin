
package com.laker.admin.framework.ext.mybatis;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@MappedTypes({GeoPoint.class})
public class GeoPointTypeHandler extends BaseTypeHandler<GeoPoint> {
    /**
     * 空间参照标识系 MySQL数据库默认为0
     */
    private static int SRID = 0;
    /**
     * 字节顺序指示符为1或0表示小端或大端存储。小字节序和大字节序分别也称为网络数据表示（NDR）和外部数据表示（XDR）
     */
    private static byte ENDIAN = (byte) 1;


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, GeoPoint parameter, JdbcType jdbcType) throws SQLException {

        ps.setBytes(i, to(parameter));
    }

    @Override
    public GeoPoint getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getBytes(columnName));
    }

    @Override
    public GeoPoint getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getBytes(columnIndex));
    }

    @Override
    public GeoPoint getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getBytes(columnIndex));
    }

    /**
     * bytes转GeoPoint对象
     *
     * @param bytes
     */
    private GeoPoint parse(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bytes)
                // 小端点排序（Java默认是大端点排序，这里要改下）
                .order(ByteOrder.LITTLE_ENDIAN);
        int SRID = wrap.getInt();
        byte endian = wrap.get();
        int wkbType = wrap.getInt();
        double x = wrap.getDouble();
        double y = wrap.getDouble();
        GeoPoint geoPoint = new GeoPoint(x, y);
        log.info("geo-point:{}", JSONUtil.toJsonStr(geoPoint));
        return geoPoint;
    }

    /**
     * GeoPoint转bytes对象
     *
     * @param geoPoint
     */
    private byte[] to(GeoPoint geoPoint) {
        ByteBuffer wrap = ByteBuffer.allocate(25)
                // 小端点排序（Java默认是大端点排序，这里要改下）
                .order(ByteOrder.LITTLE_ENDIAN);
        // SRID: 0
        wrap.putInt(SRID);
        // 字节顺序指示符为1或0表示小端或大端存储。小字节序和大字节序分别也称为网络数据表示（NDR）和外部数据表示（XDR）
        wrap.put(ENDIAN);
        // WKB类型是指示几何类型的代码 wkbType: 1 MySQL使用从1个值至7，以表示 Point，LineString， Polygon，MultiPoint， MultiLineString， MultiPolygon，和 GeometryCollection。
        wrap.putInt(1);
        // X坐标
        wrap.putDouble(geoPoint.getLon());
        // Y坐标
        wrap.putDouble(geoPoint.getLat());
        return wrap.array();
    }
}
