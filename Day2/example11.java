package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example11 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Print the raw text data
        textStream.print("String Data");

        // Convert to Integer using separate class
        DataStream<Integer> intStream = textStream.map(new StringToIntegerMapper());
        intStream.print("Integer Data");

        // Convert to Double using separate class
        DataStream<Double> doubleStream = textStream.map(new StringToDoubleMapper());
        doubleStream.print("Double Data");

        env.execute("Primitive Types Example");
    }

    public static class StringToIntegerMapper implements MapFunction<String, Integer> {
        @Override
        public Integer map(String value) throws Exception {
            return Integer.parseInt(value);
        }
    }

    public static class StringToDoubleMapper implements MapFunction<String, Double> {
        @Override
        public Double map(String value) throws Exception {
            return Double.parseDouble(value);
        }
    }
}