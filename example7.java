
import org.apache.flink.api.common.functions.FlatMapFunction;
// ParameterTool that we'll use to pass command line arguments
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//  collector that we'll use within the flatmap to specify the output streaming entities.
import org.apache.flink.util.Collector;

public class SentenceSplitting {

    public static void main(String[] args) throws Exception {

        // A Flink program can be configured using a properties file or via command line arguments. 
        // The ParameterTool allows you to pass both files, as well as arguments. 
        // ParameterTool.fromArgs allows us to pass and extract command line arguments specified by the user.
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // We'll use these user‑defined params to configure our execution config by invoking setGlobalJobParameters.
        env.getConfig().setGlobalJobParameters(params);

        // This particular bit of code allows us to instantiate a data stream from different sources.
        DataStream<String> dataStream;

        //  If we specify an input command line argument, this streaming application will read in data from a file source.
        if (params.has("input")) {
            System.out.println("Splitting sentences from a file");
            // The data stream that we instantiate will use a text file as the source, as specified by this input command line argument.
            dataStream = env.readTextFile(params.get("input")); 
        } 
        // Our streaming application here also has the ability to read in input from a socket on our local machine.
        // We extract the host and port values from the input command line arguments, and we then use this host and port 
        // information to instantiate a socketTextStream, which will provide our input stream.
        else if (params.has("host") && params.has("port")) {
            System.out.println("Splitting sentences from a socket stream");

            dataStream = env.socketTextStream(
                    params.get("host"), Integer.parseInt(params.get("port")));
        } 
        // If the user has not specified an input file nor a host and port via the command line arguments, 
        // we'll print out an error to screen and exit from this program.
        else {
            System.out.println("Use --host and --port to specify socket OR");
            System.out.println("Use --input to specify file input");
            System.exit(1);
            return;
        }

        System.out.println("Source initialized, split sentences");

        //Invoke the FlatMapFunction and pass in an object that will split the input sentences into their individual words.
        DataStream<String> wordDataStream = dataStream.flatMap(new SentenceSplitter());

        wordDataStream.print();

        env.execute("Splitting Words");
    }

    public static class SentenceSplitter implements FlatMapFunction<String, String> {

        public void flatMap(String sentence, Collector<String> out)
                throws Exception {

            for (String word: sentence.split(" ")) {
                out.collect(word);
            }
        }
    }
}
