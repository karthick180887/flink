package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example8 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Print the raw text data (String type)
        textStream.print("String Data");

        // Convert to Integer type
        DataStream<Integer> intStream = textStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String value) {
                return Integer.parseInt(value);
            }
        });

        // Print Integer data
        intStream.print("Integer Data");

        // Convert to Double type
        DataStream<Double> doubleStream = textStream.map(new MapFunction<String, Double>() {
            @Override
            public Double map(String value) {
                return Double.parseDouble(value);
            }
        });

        // Print Double data
        doubleStream.print("Double Data");

        env.execute("Primitive Types Example");
    }
}
