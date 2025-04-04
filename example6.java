package org.apache.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example6 {

    public static void main(String[] args) throws Exception {
        // Set up the execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Read from CSV file (adjust path as needed)
        DataStream<String> csvData = env.readTextFile("path/to/your/file.csv");
        
        // Alternative: Read from socket (for testing)
        // DataStream<String> csvData = env.socketTextStream("localhost", 9000);

        // Extract only columns 0 (country) and 2 (development status)
        DataStream<String> result = csvData.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                String[] columns = value.split(",");
                if (columns.length >= 3) {
                    return columns[0].trim() + " - " + columns[2].trim();
                }
                return "Invalid record: " + value;
            }
        });

        // Print the results
        result.print();

        // Execute the program
        env.execute("CSV Column Extractor");
    }
}
