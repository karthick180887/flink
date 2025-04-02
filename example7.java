package org.apache.flink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example7 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> socketStream = env.socketTextStream("localhost", 9999);

        socketStream.print("Socket Data");

        env.execute("Socket Text Stream Example");
    }
}
