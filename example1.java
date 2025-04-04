package org.apache.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class example1 {

    public static void main(String[] args) throws Exception {

        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> dataStream = env
                .socketTextStream("localhost", 9000)
                .filter(new Filter());

        dataStream.print();

        env.execute("Car speed detection");
    }

    public static class Filter implements FilterFunction<String> {

        public boolean filter(String input) throws Exception {
            try {
                String[] tokens = input.split(",");

                if (tokens.length == 2 && Float.parseFloat(tokens[1].trim()) > 65) {
                    return true;
                }
            } catch (Exception ex) {
            }

            return false;
        }


    }

}
