package com.laker.admin.framework.kafka;

import cn.hutool.core.lang.Dict;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Component
public class EasyKafkaHealthIndicator extends AbstractHealthIndicator {

    private final KafkaAdmin kafkaAdmin;
    private final EasyKafkaConfig easyKafkaConfig;

    public EasyKafkaHealthIndicator(KafkaAdmin kafkaAdmin, @Autowired(required = false) EasyKafkaConfig easyKafkaConfig) {
        this.kafkaAdmin = kafkaAdmin;
        this.easyKafkaConfig = easyKafkaConfig;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        if (easyKafkaConfig == null || easyKafkaConfig.getTopic() == null) {
            builder.unknown().withDetail("error", "未启用kafka配置");
            return;
        }

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            builder.up();
            addTopicDetails(adminClient, builder);
            addClusterDetails(adminClient, builder);
        } catch (InterruptedException e) {
            builder.down().withException(e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            builder.down().withException(e);
        }
    }

    private void addTopicDetails(AdminClient adminClient, Health.Builder builder) throws ExecutionException, InterruptedException {
        Set<String> topicNames = adminClient.listTopics().names().get();
        Map<String, KafkaFuture<TopicDescription>> topicNameValues = adminClient.describeTopics(topicNames).topicNameValues();

        for (KafkaFuture<TopicDescription> topic : topicNameValues.values()) {
            TopicDescription topicDescription = topic.get();
            Object[] array = topicDescription.partitions().stream().map(partitionInfo -> Dict.create()
                    .set("partition", partitionInfo.partition())
                    .set("leader", partitionInfo.leader().host())
                    .set("replicas", partitionInfo.replicas().stream().map(Node::host))
                    .set("inSyncReplicas", partitionInfo.isr().stream().map(Node::host))).toArray();

            builder.withDetail(topicDescription.name(), Dict.create()
                    .set("topic", topicDescription.name())
                    .set("partitions", array)
                    .set("internal", topicDescription.isInternal()));
        }
    }

    private void addClusterDetails(AdminClient adminClient, Health.Builder builder) throws ExecutionException, InterruptedException {
        DescribeClusterResult clusterResult = adminClient.describeCluster();
        Collection<Node> nodes = clusterResult.nodes().get();
        String clusterId = clusterResult.clusterId().get();
        Node controller = clusterResult.controller().get();

        builder.withDetail("topics", adminClient.listTopics().names().get())
                .withDetail("nodes", nodes.stream().map(Node::host))
                .withDetail("clusterId", clusterId)
                .withDetail("controller", controller.host());
    }
}