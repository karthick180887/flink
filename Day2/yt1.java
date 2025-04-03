import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.apache.flink.api.common.functions.FlatMapFunction;

public class WordCountBatch {
    public static void main(String[] args) throws Exception {
        // Step 1: Set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        
        // Step 2: Read input data
        DataSet<String> text = env.readTextFile("input/words.txt");
        
        // Step 3: Apply transformations (flatMap, groupBy, sum)
        DataSet<Tuple2<String, Integer>> wordCounts = text
            .flatMap(new Tokenizer())
            .groupBy(0)
            .sum(1);
        
        // Step 4: Print results to console
        wordCounts.print();
    }

    // Custom tokenizer function to split text lines into words
    public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            String[] tokens = value.toLowerCase().split("\\s+");
            for (String token : tokens) {
                out.collect(new Tuple2<>(token, 1));
            }
        }
    }
}
