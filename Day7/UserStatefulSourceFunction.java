package org.apache.flink;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.jobgraph.SavepointConfigOptions;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class UserStatefulSourceFunction {
    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.set(SavepointConfigOptions.SAVEPOINT_PATH, "file:///home/flink/flink-chapter7/src/main/resources/statesource/3d72237e23c6d4992db97dd2365dabdd/chk-1");
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(configuration);

        env.enableCheckpointing(1000);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);
//        env.getCheckpointConfig().setCheckpointTimeout(10000);
        env.getCheckpointConfig().setCheckpointStorage("file:///home/flink/flink-chapter7/src/main/resources/statesource");
        env.getCheckpointConfig().setExternalizedCheckpointCleanup(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        env.addSource(new StatefulSourceFunction(), "stateful_source")
                .print();
        env.execute();
    }
}
