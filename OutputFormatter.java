package edu.bsu.cs222;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

public class OutputFormatter {

    public ArrayList<String> output(String jsonInput) throws IOException {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();
        ArrayList<String> guiOutput = new ArrayList<String>();

        for (int i = 0; i < 13; i++) {
            try {
                String timestamp = parser.parse(jsonInput,"timestamp",i);
                String author = parser.parse(jsonInput,"user",i);
                guiOutput.add(timestamp + " " + author);
            } catch (Exception e) {
                guiOutput.add("No more revisions");
            }
        }
        return guiOutput;
    }

    public String getArticleName(String jsonInput) throws IOException {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();
        return parser.parse(jsonInput, "to", 0);
    }

    public String getRedirectFrom(String jsonInput) throws IOException {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();
        return parser.parse(jsonInput, "from", 1);
    }

    public String getRedirectTo(String jsonInput) throws IOException {
        WikipediaRevisionParser parser = new WikipediaRevisionParser();
        return parser.parse(jsonInput, "to", 1);
    }
}
