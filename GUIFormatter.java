package edu.bsu.cs222;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUIFormatter extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ControllerInput ourController = new ControllerInput();
        OutputFormatter output = new OutputFormatter();

        VBox parent = new VBox(10);
        parent.setAlignment(Pos.TOP_CENTER);
        parent.getChildren().add(new Label("Wikipedia Revisions Checker"));

        HBox urlArea = new HBox(new Label("Enter Article: "));
        urlArea.setSpacing(5);
        urlArea.setAlignment(Pos.CENTER);

        TextField textField = new TextField();
        urlArea.getChildren().add(textField);
        parent.getChildren().add(urlArea);

        Button revisionsButton = new Button("Find Revisions");
        revisionsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (parent.getChildren().size() > 3) parent.getChildren().remove(3, parent.getChildren().size());

                    WebsiteChecker checker = new WebsiteChecker();

                    if(!checker.checkConnection()){
                        parent.getChildren().add(new Label("No connection."));
                        return;
                    }

                    OutputFormatter outFormat = new OutputFormatter();
                    String jsonOutput = ControllerInput.getJson(textField.getText());


                    if (Objects.equals(textField.getText(), "")) {
                        parent.getChildren().add(new Label("No user input provided."));
                        return;
                    }

                    if (checker.checkMissing(jsonOutput)) {
                        parent.getChildren().add(new Label("No webpage found."));
                        return;
                    }

                    ArrayList<String> result = outFormat.output(jsonOutput);
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

                    parent.getChildren().add(new Label("Article Name: " + articleName));
                    parent.getChildren().add(new Label("Redirect From: " + redirectFrom));
                    parent.getChildren().add(new Label("Redirect To: " + redirectTo));
                    parent.getChildren().add(new Label("Revisions: "));

                    for (int i = 0; i < 13; i++) {
                        parent.getChildren().add(new Label(i+1+". "+result.get(i)));
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        parent.getChildren().add(revisionsButton);

        primaryStage.setScene(new Scene(parent));
        primaryStage.setWidth(350);
        primaryStage.setHeight(750);
        primaryStage.show();
    }
}
