package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example12 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Convert to Tuple2<Integer, String> using separate mapper class
        DataStream<Tuple2<Integer, String>> tupleStream = textStream.map(new StringToTupleMapper());

        // Print Tuple2 data
        tupleStream.print("Tuple Data");

        env.execute("Tuple Example");
    }

    public static class StringToTupleMapper implements MapFunction<String, Tuple2<Integer, String>> {
        @Override
        public Tuple2<Integer, String> map(String value) throws Exception {
            String[] parts = value.split(",");
            return new Tuple2<>(Integer.parseInt(parts[0]), parts[1]);
        }
    }
}