package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example9 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Convert to Tuple2<Integer, String> (assuming input format: "1,Hello")
        DataStream<Tuple2<Integer, String>> tupleStream = textStream.map(new MapFunction<String, Tuple2<Integer, String>>() {
            @Override
            public Tuple2<Integer, String> map(String value) {
                String[] parts = value.split(","); // Splits "1,Apple" → ["1", "Apple"]
                return new Tuple2<>(Integer.parseInt(parts[0]), parts[1]);  // → (1, "Apple")
            }
        });

        // Print Tuple2 data
        tupleStream.print("Tuple Data");

        env.execute("Tuple Example");
    }
}
