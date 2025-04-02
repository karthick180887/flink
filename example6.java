package org.apache.flink;
import java.util.Arrays;
import java.util.List;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example6 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<Person> personList = Arrays.asList(
            new Person(1, "Alice", 25),
            new Person(2, "Bob", 30),
            new Person(3, "Charlie", 35)
        );

        DataStream<Person> personStream = env.fromCollection(personList);

        personStream.print("From Collection POJO");

        env.execute("From Collection POJO Example");
    }
}
