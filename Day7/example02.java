package org.apache.flink;
import java.sql.Timestamp;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.core.fs.Path;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

public class example02 {
  public static void main(String[] args) throws Exception {
    // set up the streaming execution environment
    StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
    env.setParallelism(1);

    DataStream < String > data = env.socketTextStream("localhost", 9090);

    DataStream < Tuple2 < String, Long >> sum = data.map(new MapFunction < String, Tuple2 < Long, String >> () {
        public Tuple2 < Long, String > map(String s) {
          String[] words = s.split(",");
          return new Tuple2 < Long, String > (Long.parseLong(words[0]), words[1]);
        }
      })
      .keyBy(t -> t.f0)
      .flatMap(new StatefulMap());
   
    sum.addSink(StreamingFileSink
      .forRowFormat(new Path("/home/flink/flink-chapter7/src/main/resources/example02out"), new SimpleStringEncoder < Tuple2 < String, Long >> ("UTF-8"))
      .withRollingPolicy(DefaultRollingPolicy.builder().build())
      .build());

    // execute program
    env.execute("State");
  }

  public static class StatefulMap extends RichFlatMapFunction < Tuple2 < Long, String > , Tuple2 < String, Long >> {
    private transient ValueState < Long > count;
    private transient ListState < Long > numbers;

    public void flatMap(Tuple2 < Long, String > input, Collector < Tuple2 < String, Long >> out) throws Exception {
      Long currValue = Long.parseLong(input.f1);
      Long currCount = 0L;

      if (count.value() != null) {
        currCount = count.value();
      }

      currCount += 1;

      count.update(currCount);
      numbers.add(currValue);

      if (currCount >= 10) {
        Long sum = 0L;
        String numbersStr = "";
        for (Long number: numbers.get()) {
          numbersStr = numbersStr + " " + number;
          sum = sum + number;
        }
        /* emit sum of last 10 elements */
        out.collect(new Tuple2 < String, Long > (numbersStr, sum));
        /* clear value */
        count.clear();
        numbers.clear();
      }
    }
    public void open(Configuration conf) {
      ListStateDescriptor < Long > listDesc = new ListStateDescriptor < Long > ("numbers", Long.class);
      numbers = getRuntimeContext().getListState(listDesc);

      ValueStateDescriptor < Long > descriptor2 = new ValueStateDescriptor < Long > ("count", Long.class);
      count = getRuntimeContext().getState(descriptor2);
    }
  }
}
