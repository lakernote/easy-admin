package com.laker.admin.framework.aop.trace;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class Trace {
    private int depth;
    private String tracingId;
    private List<Span> spans = new ArrayList<>();
    private LinkedList<Span> activeSpanStack = new LinkedList<>();

    private Span peek() {
        if (activeSpanStack.isEmpty()) {
            return null;
        }
        return activeSpanStack.getLast();
    }

    private Span pop() {
        return activeSpanStack.removeLast();
    }

    public void addSpan(Span span) {
        span.setOrder(++depth);
        Span latest = current();
        if (latest != null) {
            span.setLevel(1);
            latest.getChilds().add(span);
        }
        activeSpanStack.addLast(span);
    }

    public Span current() {
        return peek();
    }

    public boolean stopSpan(Span current) {
        Span pop = pop();
        if (pop != null && pop.getLevel() == 0) {
            spans.add(pop);
        }
        return activeSpanStack.isEmpty();
    }


}
