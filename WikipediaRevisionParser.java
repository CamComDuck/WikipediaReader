package edu.bsu.cs222;

import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import com.jayway.jsonpath.JsonPath;


public class WikipediaRevisionParser {
    public String parse (String testDataStream, String input, int count) throws IOException {
        JSONArray result = (JSONArray) JsonPath.read(testDataStream, "$.."+input);
        return result.get(count).toString();
    }
}
