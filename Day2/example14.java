package org.apache.flink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example14 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> fileStream = env.readTextFile("/home/flink/flink-beginer/src/main/resources/file.txt");

        fileStream.print("File Data");

        env.execute("Read Text File Example");
    }
}
