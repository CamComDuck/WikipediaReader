package edu.bsu.cs222;


import javafx.scene.control.Label;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;


public class ControllerInput {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        WebsiteChecker checker = new WebsiteChecker();
        OutputFormatter output = new OutputFormatter();

        System.out.print("What would you like to search Wikipedia for?: ");
        String userInput = scan.next();

        if(userInput.isEmpty()){
            System.out.println("Please input a webpage next time");
            return;
        }

        if(!checker.checkConnection()){
            System.out.println("No connection.");
            return;
        }

        String jsonOutput = ControllerInput.getJson(userInput);

        if(checker.checkMissing(jsonOutput)){
            System.out.println("Webpage does not exist");
            return;
        }

        ArrayList<String> result = output.output(jsonOutput);
        String articleName;
        String redirectFrom;
        String redirectTo;

        try {
            redirectFrom = output.getRedirectFrom(jsonOutput);
            redirectTo = output.getRedirectTo(jsonOutput);
            articleName = redirectTo;
        } catch (Exception e) {
            redirectFrom = "No Redirect";
            redirectTo = "No Redirect";
            articleName = output.getArticleName(jsonOutput);
        }

        System.out.println("Article Name: " + articleName);
        System.out.println("Redirect From: " + redirectFrom);
        System.out.println("Redirect To: " + redirectTo);
        System.out.println("Revisions: ");

        for (int i = 0; i < 13; i++) {
            System.out.println(i+1+". "+result.get(i));
        }

    }

    public static String getJson(String userInput) throws IOException {
        WebsiteChecker checker = new WebsiteChecker();
        URLConnection connection = checker.connectToWikipedia(userInput);
        return checker.readJsonAsStringFrom(connection);
    }

}
