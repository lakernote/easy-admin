package ${package.Controller};

import org.springframework.web.bind.annotation.RequestMapping;
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import com.laker.admin.framework.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
* <p>
    * ${table.comment!} 前端控制器
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>
    @Autowired
    ${table.serviceName} ${table.serviceName?substring(1)?uncap_first};

    @GetMapping
    @ApiOperation(value = "${table.comment!}分页查询")
    public Response pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<${table.entityName}> queryWrapper = new QueryWrapper().lambda();
        Page pageList = ${table.serviceName?substring(1)?uncap_first}.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }

    @PostMapping
    @ApiOperation(value = "新增或者更新${table.comment!}")
    public Response saveOrUpdate(${table.entityName} param) {
        return Response.ok(${table.serviceName?substring(1)?uncap_first}.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询${table.comment!}")
    public Response get(@PathVariable Long id) {
        return Response.ok(${table.serviceName?substring(1)?uncap_first}.getById(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除${table.comment!}")
    public Response delete(@PathVariable Long id) {
        return Response.ok(${table.serviceName?substring(1)?uncap_first}.removeById(id));
    }
}