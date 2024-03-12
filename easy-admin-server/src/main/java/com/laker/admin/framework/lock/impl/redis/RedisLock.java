package com.laker.admin.framework.lock.impl.redis;

import com.laker.admin.framework.lock.api.LLock;
import com.laker.admin.framework.lock.core.AbstractSimpleLock;
import io.lettuce.core.RedisCommandInterruptedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

/**
 * @author laker
 */
@Slf4j
public class RedisLock extends AbstractSimpleLock {

    private static final String LOCK_SCRIPT = "return redis.call('SET', KEYS[1], ARGV[1], 'PX', tonumber(ARGV[2]), 'NX') and true or false";

    private static final String LOCK_RELEASE_SCRIPT = "return redis.call('GET', KEYS[1]) == ARGV[1] and (redis.call('DEL', KEYS[1]) == 1) or false";

    private static final String LOCK_REFRESH_SCRIPT =
            "if redis.call('GET', KEYS[1]) == ARGV[1] then\n" +
                    "    redis.call('PEXPIRE', KEYS[1], tonumber(ARGV[2]))\n" +
                    "    return true\n" +
                    "end\n" +
                    "return false";

    private final RedisScript<Boolean> lockScript = new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockReleaseScript = new DefaultRedisScript<>(LOCK_RELEASE_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockRefreshScript = new DefaultRedisScript<>(LOCK_REFRESH_SCRIPT, Boolean.class);
    private StringRedisTemplate stringRedisTemplate;

    public RedisLock(final StringRedisTemplate stringRedisTemplate, TaskScheduler taskScheduler) {
        super(taskScheduler);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected String acquire(final String key, final String token, final Duration expiration) {

//        final List<String> singletonKeyList = Collections.singletonList(key(key));
        // 使用这个也行
//        final boolean locked = stringRedisTemplate.execute(lockScript, singletonKeyList, token, String.valueOf(expiration));
        // 这个等价于 SET key token NX PX 5000
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(key(key), token, expiration);
        log.info("Tried to acquire lock for key {} with token {}. Locked: {}", key, token, locked);
        return locked ? token : null;
    }

    private String key(String key) {
        return "lock:" + key;
    }

    @Override
    protected boolean release0(LLock lock) {
        String key = lock.getKey();
        String token = lock.getToken();
        final List<String> singletonKeyList = Collections.singletonList(key(key));
        final boolean released = stringRedisTemplate.execute(lockReleaseScript, singletonKeyList, token);
        if (released) {
            log.info("Release script deleted the record for key {} with token {}", key, token);
        } else {
            log.error("Release script failed for key {} with token {}", key, token);
        }
        return released;
    }

    @Override
    protected boolean refresh(final String key, final String token, final Duration expiration) {
        final List<String> singletonKeyList = Collections.singletonList(key(key));

        boolean refreshed = false;
        try {
            refreshed = stringRedisTemplate.execute(lockRefreshScript, singletonKeyList, token, String.valueOf(expiration.toMillis()));
            if (refreshed) {
                log.info("Refresh script updated the expiration for key {} with token {} to {}", key, token, expiration);
            } else {
                log.info("Refresh script failed to update expiration for key {} with token {} with expiration: {}", key, token, expiration);
            }
        } catch (RedisSystemException e) {
            if (e.getCause() != null && (e.getCause() instanceof RedisCommandInterruptedException)) {
                log.error("Refresh script thread interrupted to update expiration for key {} with token {} with expiration: {}", key, token, expiration);
            } else {
                throw e;
            }
        }
        return refreshed;
    }
}