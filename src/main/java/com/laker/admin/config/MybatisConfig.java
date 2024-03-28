package com.laker.admin.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.laker.admin.framework.ext.mybatis.PerformanceInterceptor;
import com.laker.admin.framework.ext.mybatis.datapermission.LakerDataPermissionV2Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author laker
 */
@Configuration
@MapperScan("com.laker.admin.**.mapper")
public class MybatisConfig {


    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//       V1版本
//        // 添加数据权限插件
//        DataPermissionInterceptor dataPermissionInterceptor = new DataPermissionInterceptor();
//        LakerDataPermissionHandler lakerDataPermissionHandler = new LakerDataPermissionHandler();
//        // 添加自定义的数据权限处理器
//        dataPermissionInterceptor.setDataPermissionHandler(lakerDataPermissionHandler);
        // V2版本
        LakerDataPermissionV2Interceptor dataPermissionInterceptor = new LakerDataPermissionV2Interceptor();
        interceptor.addInnerInterceptor(dataPermissionInterceptor);


        // 分页插件
        // new PaginationInnerInterceptor(DbType.MYSQL)
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }


    /**
     * SQL执行效率插件
     *
     */
    @Bean
    @ConditionalOnProperty(value = "javamelody.enabled", havingValue = "false")
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor interceptor = new PerformanceInterceptor();
        // sql美化打印
        interceptor.setFormat(true);
        // 设置SQL超时时间
        interceptor.setMaxTime(100L);
        // 写入日志
        interceptor.setWriteInLog(true);
        return interceptor;
    }
}
