package org.apache.flink;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.core.fs.Path;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

public class CheckpointingDemo {
  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

    // start a checkpoint every 1000 ms
    env.enableCheckpointing(1000);

    // to set minimum progress time to happen between checkpoints
    env.getCheckpointConfig().setMinPauseBetweenCheckpoints(500);

    // checkpoints have to complete within 10000 ms, or are discarded
    env.getCheckpointConfig().setCheckpointTimeout(10000);

    // set mode to exactly-once (this is the default)
    env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE); // AT_LEAST_ONCE

    // allow only one checkpoint to be in progress at the same time
    env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

    // enable externalized checkpoints which are retained after job cancellation
    env.getCheckpointConfig().enableExternalizedCheckpoints(ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION); // DELETE_ON_CANCELLATION

    //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 100));
    // number of restart attempts , delay in each restart

    DataStream < String > data = env.socketTextStream("localhost", 9090);

    DataStream < Long > sum = data.map(new MapFunction < String, Tuple2 < Long, String >> () {
        public Tuple2 < Long, String > map(String s) {
          String[] words = s.split(",");
          return new Tuple2 < Long, String > (Long.parseLong(words[0]), words[1]);
        }
      })
      .keyBy(t -> t.f0)
      .flatMap(new StatefulMap());
    //sum.writeAsText("/home/jivesh/state2");
    sum.addSink(StreamingFileSink
      .forRowFormat(new Path("/home/jivesh/state2"),
        new SimpleStringEncoder < Long > ("UTF-8"))
      .withRollingPolicy(DefaultRollingPolicy.builder().build())
      .build());

    // execute program
    env.execute("State");
  }

  public static class StatefulMap extends RichFlatMapFunction < Tuple2 < Long, String > , Long > {
    private transient ValueState < Long > sum;
    private transient ValueState < Long > count;

    public void flatMap(Tuple2 < Long, String > input, Collector < Long > out) throws Exception {
      Long currCount = 0L;
      Long currSum = 0L;

      if (count.value() != null) {
        currCount = count.value();
      }

      if (sum.value() != null) {
        currSum = sum.value();
      }

      currCount += 1;
      currSum = currSum + Long.parseLong(input.f1);

      count.update(currCount);
      sum.update(currSum);

      if (currCount >= 10) {
        /* emit sum of last 10 elements */
        out.collect(sum.value());
        /* clear value */
        count.clear();
        sum.clear();
      }
    }
    public void open(Configuration conf) {
      ValueStateDescriptor < Long > descriptor = new ValueStateDescriptor < Long > ("sum", TypeInformation.of(new TypeHint < Long > () {}));
      sum = getRuntimeContext().getState(descriptor);

      ValueStateDescriptor < Long > descriptor2 = new ValueStateDescriptor < Long > ("count", TypeInformation.of(new TypeHint < Long > () {}));
      count = getRuntimeContext().getState(descriptor2);
    }
  }
}