package com.laker.admin.framework.ext.step;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import com.laker.admin.framework.ext.actuator.step.EasyMeterRegistry;
import com.laker.admin.framework.ext.actuator.step.StepCounterTuple;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author: laker
 * @date: 2022/10/23
 **/
@Slf4j
public class StepCounterTest {
    @SneakyThrows
    @Test
    public void test() {
        EasyMeterRegistry easyMeterRegistry = new EasyMeterRegistry(Duration.ofSeconds(10));

        for (int i = 0; i < 30; i++) {

            ThreadUtil.execute(() -> {
                StepCounterTuple counter = easyMeterRegistry.counter(RandomUtil.randomString("qwertyuiopasdfghjklzxcvbnm", 1));
                counter.increment1(1);
                counter.increment2(2);
            });
        }

        TimeUnit.SECONDS.sleep(30);
    }
}
