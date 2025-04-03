package org.apache.flink;
import java.util.Arrays;
import java.util.List;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example4 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<String> stringList = Arrays.asList("Apple", "Banana", "Cherry");
        DataStream<String> stringStream = env.fromCollection(stringList);

        stringStream.print("From Collection");

        env.execute("From Collection Example");
    }
}
