package com.laker.admin.framework.aop.trace;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author laker
 */
@Data
@Slf4j
public class Trace {
    private static final String BAR = "+";
    /**
     *
     */
    private int depth = 0;

    private TreeView treeView = new TreeView(true, "");
    /**
     * 存储 span结果
     */
    private List<Span> spans = new ArrayList<>();
    /**
     * 方法调用栈
     */
    private LinkedList<Span> activeSpanStack = new LinkedList<>();

    /**
     * 添加 span
     *
     * @param span
     */
    public void addSpan(String spanName, SpanType spanType) {
        treeView.begin(spanType + "-" + spanName);
        Span span = new Span();
        span.setId(spanName);
        span.setSpanType(spanType);
        span.setStartTime(System.currentTimeMillis());
        span.setOrder(++depth);
        // 查询出栈中最新的span
        Span latest = current();
        // 栈为空设置栈leve为 0 也就是 root span
        if (latest == null) {
            span.setLevel(0);
            span.setLevelDeep(1);
            // 栈不为空
        } else {
            // 设置level 为上个level +1
//            span.setLevel(latest.getLevel() + 1);
            span.setLevel(latest.getLevel() + 1);
            span.setLevelDeep(latest.getChilds().size() + 1);
            // 添加进其 子span列表
            span.setParent(latest);
            latest.getChilds().add(span);
        }
        // 入栈
        activeSpanStack.addLast(span);
    }

    public boolean stopSpan(long time) {
        Span current = current();
        current.setEndTime(System.currentTimeMillis());
        current.setCost(current.getEndTime() - current.getStartTime());
        treeView.end();

        // 出栈
        Span pop = pop();
        // 栈不为空且是第一层栈 则加入到 trace的spans
        if (pop != null && pop.getLevel() == 0) {
            spans.add(pop);
        }
        boolean empty = activeSpanStack.isEmpty();
        if (empty && current.getCost() > time) {
            // 打印日志方式一 每个span 一行日志
            /**
             *  logSpan(trace.getSpans(), StringUtils.SPACE);
             */
            // 打印日志方式二 整体一颗树
            String draw = treeView.draw();
            log.info(draw);
        }
        return empty;
    }

    public Span current() {
        return peek();
    }

    private Span peek() {
        if (activeSpanStack.isEmpty()) {
            return null;
        }
        return activeSpanStack.getLast();
    }

    private Span pop() {
        return activeSpanStack.removeLast();
    }

    private static void logSpan(List<Span> spans, String append) {
        if (CollectionUtils.isEmpty(spans)) {
            return;
        }
        spans.sort(Comparator.comparing(Span::getOrder));
        spans.stream().filter(span -> span.getLevel() != 0).max(Comparator.comparing(Span::getCost)).ifPresent(span -> span.setMax(true));
        int i = 1;
        for (Span span : spans) {
            span.setName(span.getParent() == null ? "root" : span.getParent().getName() + "." + (i++) + "");
            log.warn("{} {}{}{}ms{}:[{}]-{}", span.getName(), append + BAR, span.isMax() ? "【" : "[", span.getCost(), span.isMax() ? "】" : "]", span.getSpanType(), span.getId());
            logSpan(span.getChilds(), append + BAR);
        }
    }
}
