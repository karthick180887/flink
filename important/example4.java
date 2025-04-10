package org.apache.flink;
import org.apache.flink.Person;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.impl.StreamFormatAdapter;
import org.apache.flink.connector.file.src.reader.TextLineInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.time.Duration;
public class example4 {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironmentWithWebUI(new Configuration());

        // Bounded
//        FileSource<String> fileSource = FileSource.<String>forRecordStreamFormat(new TextLineInputFormat(), new Path("/home/flink/flink-file/src/main/java/csv")).build();
//        env.fromSource(fileSource, WatermarkStrategy.noWatermarks(),"test_csv").print();

        //UnBounded
//        FileSource<String> fileSource = FileSource.<String>forRecordStreamFormat(new TextLineInputFormat(), new Path("/home/flink/flink-file/src/main/java/csv")).monitorContinuously(Duration.ofSeconds(10)).build();
//        env.fromSource(fileSource, WatermarkStrategy.noWatermarks(),"test_csv").print();

//        CSV
//        CsvReaderFormat<Person> csvReaderFormat = CsvReaderFormat.forPojo(Person.class);
//        FileSource<Person> fileSource = FileSource.forRecordStreamFormat(csvReaderFormat, new Path("/home/flink/flink-file/src/main/java/csv")).build();
//        env.fromSource(fileSource, WatermarkStrategy.noWatermarks(),"test_csv").print();

//        BulkFormat
        CsvReaderFormat<Person> csvReaderFormat = CsvReaderFormat.forPojo(Person.class);
        FileSource<Person> fileSource = FileSource.forBulkFileFormat(new StreamFormatAdapter<>(csvReaderFormat)
                        ,new Path("/home/flink/flink-file/src/main/java/csv"))
                        .monitorContinuously(Duration.ofSeconds(10)).build();

        env.fromSource(fileSource, WatermarkStrategy.noWatermarks(),"test_csv").print();

        env.execute("file source connector");

    }
}