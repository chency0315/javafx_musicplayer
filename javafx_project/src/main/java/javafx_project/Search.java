/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javafx_project.Controller.mediaPlayer;

/**
 *
 * @author jeffrey
 */
public class Search extends Application {
    private File directory;
    private File[] files;
    private ArrayList<File> songs;
    @Override
    public void start(Stage stage1) throws IOException {  
        BorderPane borderPane = new BorderPane();
        FlowPane top = new FlowPane();
        FlowPane bottom = new FlowPane();
        Button returnButton = new Button();
        Button searchButton = new Button();
        TextField searchBar = new TextField("search songs");
        ListView listView = new ListView();
        Image returnicon = new Image(new File("images/returnbutton.png").toURI().toString());
        ImageView ricon = new ImageView(returnicon);
        returnButton.setGraphic(ricon);
        ricon.setFitWidth(40);
        ricon.setFitHeight(40);
        returnButton.setPrefSize(80,50);
        
        Image search = new Image(new File("images/searchbutton.png").toURI().toString());
        ImageView searchicon = new ImageView(search);
        searchButton.setGraphic(searchicon);
        searchButton.setPrefWidth(45);
        searchicon.setFitWidth(25);
        searchicon.setFitHeight(25);
        
        searchBar.setPrefHeight(32);
        searchBar.setPrefWidth(200);
        
        ObservableList<String> songList = FXCollections.observableArrayList();
        // pass in observablelist
//        listView.setItems(songList);
        songs = new ArrayList<File>();
        directory = new File("music");
        files = directory.listFiles();
         if(files!=null){
            for(File file:files){
                songs.add(file);
                // get song file name (string)
                String rootFileName = file.getName();
                songList.add(rootFileName);
//                System.out.println(rootFileName);
            }
        }
         searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            // Filter the data based on the search query
            ObservableList<String> filteredData = FXCollections.observableArrayList();
            for (String song : songList) {
                if (song.toLowerCase().contains(newValue.toLowerCase())) {
                    filteredData.add(song);
                }
            }
            listView.setItems(filteredData);
        });
        
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(returnButton);
        top.setAlignment(Pos.CENTER);
        top.getChildren().addAll(searchBar,searchButton);
        top.setPrefHeight(35);
        
        borderPane.setTop(top);
        borderPane.setCenter(listView);
        borderPane.setBottom(bottom);
        
        //run playlist
        Playlist playList = new Playlist();
        returnButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    playList.start(stage1);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    });
        
        Scene scene=new Scene(borderPane, 400, 600);
        stage1.setTitle("Search");
        stage1.setScene(scene);
        stage1.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
