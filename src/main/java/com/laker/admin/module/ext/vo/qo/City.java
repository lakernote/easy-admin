package com.laker.admin.module.ext.vo.qo;

import com.laker.admin.module.enums.Distance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "城市信息")
public class City {

    @ApiModelProperty(value = "城市ID", example = "1")
    private String id;

    @ApiModelProperty(value = "距离", example = "km")
    //    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private Distance distance;
}
