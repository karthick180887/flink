package org.apache.flink;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
public class example3 {

    public static void main(String[] args) throws Exception {

        Configuration conf = new Configuration();
        conf.setInteger(RestOptions.PORT, 8082);

        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.createLocalEnvironment(8, conf);

        DataStream<String> dataStream = env
                .socketTextStream("localhost", 9000)
                .filter(new Filter())
                .name("High-speed Filter Operation").uid("car-speed-filter");

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
