package org.apache.flink;
import java.util.Arrays;
import java.util.List;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example5 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<Tuple2<Integer, String>> tupleList = Arrays.asList(
            Tuple2.of(1, "Alice"),
            Tuple2.of(2, "Bob"),
            Tuple2.of(3, "Charlie")
        );

        DataStream<Tuple2<Integer, String>> tupleStream = env.fromCollection(tupleList);

        tupleStream.print("From Collection Tuple");

        env.execute("From Collection Tuple Example");
    }
}
