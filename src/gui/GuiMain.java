package evra.gui;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.web.WebView;
import javafx.geometry.*;
import evra.EvraMain;

public class GuiMain extends Application {
    public void launchGui(String[] args) {
        launch(args);
    }

    private static WebView html;
    private TextField tf;
    private static StringBuilder sb;
    private final String htmlHeader = new String("<html><body contentEditable=\"false\" bgcolor=black>");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Evra");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        //Create the HTML area; the primary text box
        html = new WebView();
        clear();
        html.setPrefHeight(1500);
        html.setPrefWidth(1500);
        grid.add(html, 0, 0);

        tf = new TextField();
        tf.setFont(new Font("Courier New", 24));
        tf.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                EvraMain.dispatch(tf.getText());
                tf.clear();
            }
        });
        grid.add(tf, 0, 1);


        //Adds the grid to the scene, at 400x400 resoluton
        Scene scene = new Scene(grid);

        primaryStage.setScene(scene);
        primaryStage.show();
        EvraMain.initiate();
    }

    public void clear() {
        sb = new StringBuilder(htmlHeader);
        addText("<font size=6 face=\"courier new\" color=\"white\">");
        update();
    }

    private void update() {
        html.getEngine().loadContent(sb.toString());

    }

    public void addText(String s) {
        sb.append(s);
        update();
    }
}