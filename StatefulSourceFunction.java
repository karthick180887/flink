package org.apache.flink;

import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class StatefulSourceFunction implements SourceFunction<Long>, CheckpointedFunction {

    private long value = 0L;
    private final AtomicBoolean isRunning = new AtomicBoolean(true);
    private ListState<Long> listState;

    @Override
    public void run(SourceContext<Long> ctx) throws Exception {
        while (isRunning.get() && value < 1_000L) {
            synchronized (ctx.getCheckpointLock()) {
                ctx.collect(value);
                value++;
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    @Override
    public void cancel() {
        this.isRunning.set(false);
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        System.out.println("the snapshot state method is called...");
        this.listState.clear();
        this.listState.add(this.value);
    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        System.out.println("the initializeState method is called...");
        ListStateDescriptor<Long> descriptor =
                new ListStateDescriptor<>("value", Long.class);
        this.listState = context.getOperatorStateStore().getListState(descriptor);

        if (context.isRestored()) {
            Iterable<Long> iterable = this.listState.get();
            if (iterable != null) {
                for (Long i : iterable) {
                    this.value = i;
                }
            }
        }
    }
}