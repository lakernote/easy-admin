package com.laker.admin.framework.ext.actuator.endpoint;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.SneakyThrows;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 端点的路径由 @Endpoint 的 id 参数决定。会将请求路由到 /actuator/jarVersion
 */
@WebEndpoint(id = "jarVersion")
@Component
public class EasyJarVersionEndpoint {

    private static final Cache<String, Set<Dependency>> dependencyCache = CacheBuilder.newBuilder().build();

    private static final String DEPENDENCY_KEY = "dependencies";

    /**
     * <p>
     *
     * @ReadOperation：映射到 HTTP GET
     * @WriteOperation：映射到 HTTP POST
     * @DeleteOperation：映射到 HTTP DELETE
     * </p>
     */
    @ReadOperation
    public Set<Dependency> list() {
        return dependencyCache.getIfPresent(DEPENDENCY_KEY);
    }

    @ReadOperation
    public String read(@Selector String a) {
        return "read my endpoint";
    }

    @WriteOperation
    public String write() {
        return "write operation";
    }

    @DeleteOperation
    public String delete() {
        return "delete operation";
    }

    @SneakyThrows
    @PostConstruct
    // 优化这个方法，将依赖信息缓存起来
    private void initDependency() {
        Set<Dependency> dependencies = new LinkedHashSet<>();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources_1 = pathMatchingResourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + "**/META-INF/**/pom.properties");
        for (Resource resource : resources_1) {
            Dependency dependency = new Dependency();
            byte[] data = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String[] list = new String(data, StandardCharsets.UTF_8).split("\n");
            for (String string : list) {
                string = string.replaceAll("\r", "");
                if (string.startsWith("version=") || string.startsWith("groupId=") || string.startsWith("artifactId=")) {
                    if (string.startsWith("version=")) {
                        dependency.setVersion(string.replace("version=", ""));
                    }
                    if (string.startsWith("groupId=")) {
                        dependency.setGroupId(string.replace("groupId=", ""));
                    }
                    if (string.startsWith("artifactId=")) {
                        dependency.setArtifactId(string.replace("artifactId=", ""));
                    }
                }
            }
            dependencies.add(dependency);
        }
        dependencyCache.put(DEPENDENCY_KEY, dependencies);
    }
}