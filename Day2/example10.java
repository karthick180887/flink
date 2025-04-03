package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example10 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Convert to POJO (assuming input format: "1,John")
        DataStream<Person> personStream = textStream.map(new MapFunction<String, Person>() {
            @Override
            public Person map(String value) {
                String[] parts = value.split(","); // Split "1,John" → ["1", "John"]
                return new Person(Integer.parseInt(parts[0]), // Convert "1" → 1
                parts[1]); // Keep "John" as-is
            }
        });

        // Print POJO data
        personStream.print("POJO Data");

        env.execute("POJO Example");
    }

    // POJO class
    public static class Person {
        public int id;
        public String name;

        // Default constructor required for Flink POJOs
        public Person() {}

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{id=" + id + ", name='" + name + "'}";
        }
    }
}
