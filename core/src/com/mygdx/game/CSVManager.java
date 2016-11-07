import java.io.FileWriter;
import java.util.Arrays;
import java.sql.Timestamp;

public class CSVManager {
    String csvFile;
    FileWriter writer = new FileWriter(csvFile);

    public CSVManager(){
        this.csvFile = "~/score.csv";
    }

    public void saveScore(int score) {
        java.util.Date date= new java.util.Date();
        Timestamp fullDate = new Timestamp(date.getTime());
        CSVUtils.writeLine(writer, Arrays.asList(score, fullDate));

        writer.flush();
        writer.close();
    }
}