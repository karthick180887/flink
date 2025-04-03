package org.apache.flink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example1 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> stringStream = env.fromElements("Apple", "Banana", "Cherry");
        DataStream<Integer> integerStream = env.fromElements(10, 20, 30);
        DataStream<Double> doubleStream = env.fromElements(1.5, 2.5, 3.5);

        stringStream.print("String");
        integerStream.print("Integer");
        doubleStream.print("Double");

        env.execute("From Elements Example");
    }
}
