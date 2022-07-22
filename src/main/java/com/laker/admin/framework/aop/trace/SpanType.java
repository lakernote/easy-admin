package com.laker.admin.framework.aop.trace;

public enum SpanType {
    Controller,
    Service,
    Mapper,
    Redis,
    Remote,
    Kafka,
    Others;
}