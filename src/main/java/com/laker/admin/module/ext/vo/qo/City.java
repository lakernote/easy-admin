package com.laker.admin.module.ext.vo.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.laker.admin.module.enums.Distance;
import lombok.Data;

@Data
public class City {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Distance distance;
}
