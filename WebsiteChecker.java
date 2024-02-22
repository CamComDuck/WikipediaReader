package edu.bsu.cs222;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class WebsiteChecker {

    public boolean checkConnection() throws IOException{
        try {
            this.connectToWikipedia("Soup");
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    public boolean checkMissing(String data){
        return data.contains("missing");
    }

    public URLConnection connectToWikipedia(String desiredArticle) throws IOException {
        String encodedUrlString = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=revisions&titles=" +
                URLEncoder.encode(desiredArticle, Charset.defaultCharset()) +
                "&rvprop=timestamp|user&rvlimit=13&redirects";
        URL url = new URL(encodedUrlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent-Duck",
                "CS222FirstProject/0.1 (joshua.miller3@bsu.edu)");
        connection.connect();

        return connection;
    }

    public String readJsonAsStringFrom(URLConnection connection) throws IOException {
            return new String(connection.getInputStream().readAllBytes(), Charset.defaultCharset());
    }

    public void printRawJson(String jsonData) {
        System.out.println(jsonData);
    }
}
