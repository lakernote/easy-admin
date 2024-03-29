package com.laker.admin.module.ext.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.laker.admin.framework.ext.mvc.CurrentUser;
import com.laker.admin.framework.ext.mvc.PageRequest;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.enums.DemoTypeEnum;
import com.laker.admin.module.enums.Distance;
import com.laker.admin.module.ext.vo.qo.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 日志 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-16
 */
@Api(tags = "示例-△△△各种请求参数示例△△△")
@ApiSupport(order = -1)
@RestController
@RequestMapping("/ext/demo")
@Slf4j
public class DemoController {

    @GetMapping
    @ApiOperation(value = "参数数组 - querystring")
    public void pageAll(@RequestParam(required = false, defaultValue = "1") long page,
                        @RequestParam(required = false, defaultValue = "10") long limit,
                        @RequestParam(required = false) List<DemoTypeEnum> types) {
        log.info("page:{},limit:{},types:{}", page, limit, types.toString());
    }

    @GetMapping("/1")
    @ApiOperation(value = "枚举 - querystring，默认是枚举值 都是大写的这里是可以自定义")
    public void pageAll1(Distance distance) {
        log.info(distance.toString());
    }

    @GetMapping("/2")
    @ApiOperation(value = "对象 - querystring")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "distance", value = "距离", required = true, dataType = "Distance", paramType = "query")
    })
    public Response<City> pageAll2(City city) {
        log.info(city.toString());
        return Response.ok(city);
    }

    @PostMapping("/3")
    @ApiOperation(value = "实体 - json")
    public Response<City> pageAll3(@RequestBody City city) {
        log.info(city.toString());
        return Response.ok(city);
    }

    @PostMapping("/4")
    @ApiOperation(value = "实体-json + 自动填充CurrentUser")
    public void pageAll4(@RequestBody City city, CurrentUser user) {
        log.info("city:{},user{}", city, user.toString());
    }

    @GetMapping("/5")
    @ApiOperation(value = "请求参数的PageRequest对象自动填充 - PageRequest")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", example = "1", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页大小", example = "10", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "sort", value = "排序", example = "create_time,asc", dataType = "String", paramType = "query")
    })
    public void pageAll5(PageRequest user) {
        log.info(user.toString());
    }

    @PostMapping("/6")
    @ApiOperation(value = "请求参数的CurrentUser对象自动填充 - CurrentUser")
    public void pageAll6(CurrentUser user) {
        log.info("user{}", user.toString());
    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // 处理文件上传逻辑
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{fileId}")
    @ApiOperation(value = "下载文件")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // 根据 fileId 获取文件并返回下载
        Resource fileResource = new FileSystemResource(Paths.get("storage/nginx.conf")); // 获取文件资源的逻辑
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
                .body(fileResource);
    }

    @GetMapping("/datas")
    @ApiOperation(value = "查询数据列表")
    public ResponseEntity<List<City>> fetchData(@RequestParam(required = false) String keyword) {
        List<City> dataList = new ArrayList<>(); // 查询数据的逻辑
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/datas/{id}")
    @ApiOperation("获取单个数据信息")
    public ResponseEntity<City> getUserById(@PathVariable String id) {
        City city = new City();
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/datas")
    @ApiOperation(value = "新增数据")
    public ResponseEntity<City> addData(@RequestBody City city) {
        // 添加新数据的逻辑
        return ResponseEntity.status(HttpStatus.CREATED).body(city);
    }

    @PutMapping("/datas/{id}")
    @ApiOperation(value = "更新数据")
    public ResponseEntity<City> updateData(@PathVariable String id, @RequestBody City city) {
        // 根据 id 更新数据的逻辑
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/datas/{id}")
    @ApiOperation(value = "删除数据")
    public ResponseEntity<String> deleteData(@PathVariable String id) {
        // 根据 id 删除数据的逻辑
        if (true) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
