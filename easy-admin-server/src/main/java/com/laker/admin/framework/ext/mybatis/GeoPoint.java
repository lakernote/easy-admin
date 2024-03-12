package com.laker.admin.framework.ext.mybatis;

import lombok.Data;

@Data
public class GeoPoint {
    public GeoPoint(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /* 经度 */
    private double lon;
    /* 纬度 */
    private double lat;

}