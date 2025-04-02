package org.apache.flink;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example2 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<Integer, String>> tupleStream = env.fromElements(
            Tuple2.of(1, "Alice"),
            Tuple2.of(2, "Bob"),
            Tuple2.of(3, "Charlie")
        );

        tupleStream.print("Tuple");

        env.execute("From Elements Tuple Example");
    }
}
