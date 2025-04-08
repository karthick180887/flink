package org.apache.flink;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.KeyedStream;

public class example0 {
    public static void main(String[] args) throws Exception {
        // Set up execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Simulated stream of user transactions: (user, amount)
        DataStream<Tuple2<String, Integer>> transactions = env.fromElements(
                Tuple2.of("Alice", 50),
                Tuple2.of("Bob", 20),
                Tuple2.of("Alice", 70),
                Tuple2.of("Bob", 30),
                Tuple2.of("Alice", 30)
        );

        // Key by user
        KeyedStream<Tuple2<String, Integer>, String> keyedStream = transactions
                .keyBy(transaction -> transaction.f0); // key by username
        keyedStream.print("keyed output:");

        // Apply reduce to compute running total
        DataStream<Tuple2<String, Integer>> reducedStream = keyedStream.reduce(
                new ReduceFunction<Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> value1, Tuple2<String, Integer> value2) {
                        return Tuple2.of(value1.f0, value1.f1 + value2.f1);
                    }
                }
        );
        // Print result
        reducedStream.print("Reduced Output:");

        // Execute job
        env.execute("Flink Reduce Example");
    }
}
