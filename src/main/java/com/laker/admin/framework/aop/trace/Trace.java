package com.laker.admin.framework.aop.trace;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author laker
 */
@Data
public class Trace {
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
    public void addSpan(Span span) {
        span.setOrder(++depth);
        // 查询出栈中最新的span
        Span latest = current();
        // 栈为空设置栈leve为 0
        if (latest == null) {
            span.setLevel(0);
            // 栈不为空
        } else {
            // 设置level 为上个level +1
            span.setLevel(latest.getLevel() + 1);
            // 添加进其 子span列表
            latest.getChilds().add(span);
        }
        // 入栈
        activeSpanStack.addLast(span);
    }

    public boolean stopSpan() {
        // 出栈
        Span pop = pop();
        // 栈不为空且是第一层栈 则加入到 trace的spans
        if (pop != null && pop.getLevel() == 0) {
            spans.add(pop);
        }
        return activeSpanStack.isEmpty();
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

}
