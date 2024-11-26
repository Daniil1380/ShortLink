import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigLoader {


    private final static String DATA_FILE = "config.txt";


    public Config loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(reader, Config.class);

        } catch (Exception e) {
            return new Config();
        }
    }
}
