package org.apache.flink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example13 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Create a socket stream
        DataStream<String> textStream = env.socketTextStream("localhost", 9999);

        // Convert to POJO using separate mapper class
        DataStream<Person> personStream = textStream.map(new StringToPersonMapper());

        // Print POJO data
        personStream.print("POJO Data");

        env.execute("POJO Example");
    }

    // Mapper implementation
    public static class StringToPersonMapper implements MapFunction<String, Person> {
        @Override
        public Person map(String value) throws Exception {
            String[] parts = value.split(",");
            return new Person(Integer.parseInt(parts[0]), parts[1]);
        }
    }

    // POJO class
    public static class Person {
        public int id;
        public String name;

        // Default constructor required for Flink
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