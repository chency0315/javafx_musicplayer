/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_project;

import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static javafx_project.Controller.mediaPlayer;
/**
 *
 * @author jeffrey
 */
public class Playlist extends Application {
    private ArrayList<File> songs;
    private File[] files;
    public int num = 0 ;
    @Override
    public void start(Stage stage) throws IOException {  
        BorderPane borderPane = new BorderPane();
        FlowPane bottom = new FlowPane();
        Button addButton = new Button();
        Button returnButton = new Button();
        Button searchButton = new Button();
        
        Image add = new Image(new File("images/addbutton.png").toURI().toString());
        ImageView addicon = new ImageView(add);
        addButton.setGraphic(addicon);
        addicon.setFitWidth(40);
        addicon.setFitHeight(40);
        
        Image returnicon = new Image(new File("images/returnbutton.png").toURI().toString());
        ImageView ricon = new ImageView(returnicon);
        returnButton.setGraphic(ricon);
        ricon.setFitWidth(40);
        ricon.setFitHeight(40);
        
        Image search = new Image(new File("images/searchbutton.png").toURI().toString());
        ImageView searchicon = new ImageView(search);
        searchButton.setGraphic(searchicon);
        searchicon.setFitWidth(40);
        searchicon.setFitHeight(40);
        
        addButton.setPrefSize(80, 50);
        returnButton.setPrefSize(80,50);
        searchButton.setPrefSize(80, 50);
        
        ObservableList songList = FXCollections.observableArrayList();
        songs = new ArrayList<File>();
        File directory = new File("music");
        files = directory.listFiles();
        // loop every song in music
        if(files!=null){
            for(File file: files){
                String rootFileName = file.getName();
                songList.add(rootFileName);
                songs.add(file);
            }
        };
         addButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    FileChooser openFile = new FileChooser();
                    openFile.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("mp3 Files", "*.mp3"));
                    File file = openFile.showOpenDialog(stage);
                    System.out.println(file);
                    String rootFileName = file.getName();
                    file.renameTo(new File("music/"+rootFileName));
                    songList.add(rootFileName);
                    songs.add(file);
                    System.out.println("Add success");
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    });
        // display song names in music file 
        ListView listView = new ListView();
        listView.setItems(songList);

        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().addAll(addButton,returnButton,searchButton);
        borderPane.setCenter(listView);
        borderPane.setBottom(bottom);
        
        //run controller
         Controller controllerpage = new Controller();
        returnButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{ 
                    controllerpage.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    });
        
        //run search
        Search searchpage = new Search();
        searchButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    searchpage.start(stage);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    });
        
        Scene scene=new Scene(borderPane, 400, 600);
        stage.setTitle("Playlist");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
