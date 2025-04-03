package org.apache.flink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example3 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Person> personStream = env.fromElements(
                new Person(1,"Alice",25),
                new Person(2,"Bob",30),
                new Person(3,"Charlie",35)
        );
        personStream.print("Person Stream");
        env.execute("Pojo DataStream Example");

    }
}
