package com.laker.admin.framework.aop.trace;

/**
 * @author laker
 */

public enum SpanType {
    Controller,
    Service,
    Mapper,
    Redis,
    Remote,
    Kafka,
    Transaction,
    Thread,
    Others;
}