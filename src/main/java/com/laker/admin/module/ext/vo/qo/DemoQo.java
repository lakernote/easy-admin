package com.laker.admin.module.ext.vo.qo;

import com.laker.admin.module.enums.DemoEnum;
import com.laker.admin.module.enums.Distance;
import lombok.Data;

@Data
public class DemoQo {
    private DemoEnum demo;
    private Distance distance;
    private String city;
    private Integer value;
}
