package persistence;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ShopDAO {
    void tryOpeningFile() throws IOException, ParseException;
}
