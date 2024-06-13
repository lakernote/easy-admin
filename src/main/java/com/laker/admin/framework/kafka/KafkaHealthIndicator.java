package com.laker.admin.framework.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {

    private final KafkaAdmin kafkaAdmin;
    private final EasyKafkaConfig easyKafkaConfig;


    public KafkaHealthIndicator(KafkaAdmin kafkaAdmin, @Autowired(required = false) EasyKafkaConfig easyKafkaConfig) {
        this.kafkaAdmin = kafkaAdmin;
        this.easyKafkaConfig = easyKafkaConfig;
    }

    @Override
    public void doHealthCheck(Health.Builder builder) {
        if (easyKafkaConfig == null || easyKafkaConfig.getTopic() == null) {
            builder.unknown().withDetail("error", "未启用kafka配置");
            return;
        }
        try {
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            ListTopicsResult listTopicsResult = adminClient.listTopics();
            Set<String> topicNames = listTopicsResult.names().get();
            DescribeClusterResult clusterResult = adminClient.describeCluster();
            Collection<Node> nodes = clusterResult.nodes().get();
            final String clusterId = clusterResult.clusterId().get();
            final Node controller = clusterResult.controller().get();
            builder.up().withDetail("topics", topicNames)
                    .withDetail("nodes", nodes)
                    .withDetail("clusterId", clusterId)
                    .withDetail("controller", controller);
        } catch (Exception e) {
            builder.down().withDetail("error", e.getMessage());
        }
    }

}