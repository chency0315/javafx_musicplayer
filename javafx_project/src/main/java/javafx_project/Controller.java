/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 *
 * @author jeffrey
 */
public class Controller extends Search{
    public int songNumber;
    private int[] speeds = {25, 50, 75, 100, 125, 150, 175, 200};
    public static boolean running;
    private ArrayList<File> songs;
    public static  Timer timer;
    public static  TimerTask task;
    private File directory;
    private File[] files;
    public Media media;
    public static MediaPlayer mediaPlayer;

    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask(){
            public void run(){
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
//                System.out.println(current/end);
                if(current/end==1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }
   
    @Override
    public void start(Stage primaryStage) throws IOException {  
        BorderPane borderPane = new BorderPane();
        VBox vBox = new VBox();
        //set up buttons
        GridPane buttons = new GridPane();
        Button previousbutton = new Button();
        Button pausebutton = new Button();
        Button nextbutton = new Button();
        Button playbutton = new Button();
        Slider slider = new Slider();
        Button replaybutton = new Button();
        ComboBox choiceBoxbutton = new ComboBox(); 
        Button playlist = new Button();
        ProgressBar progressBar = new ProgressBar(0);
        Label songLabel = new Label("song");
        
        //gridpane add buttons
        buttons.add(previousbutton, 0, 0);
        buttons.add(pausebutton, 1, 0);
        buttons.add(playbutton, 2, 0);
        buttons.add(nextbutton, 3, 0);
        buttons.add(choiceBoxbutton, 0, 1);
        buttons.add(replaybutton, 1, 1);
        buttons.add(slider, 2, 1);
        buttons.add(playlist, 3, 1);
        
        Image cover = new Image(new File("images/musiccover.png").toURI().toString());
        ImageView musicCover = new ImageView(cover); 
        musicCover.setFitWidth(250);
        musicCover.setFitHeight(250);
        
        Image image1 = new Image(new File("images/rewind-button.png").toURI().toString());
        ImageView imageView1 = new ImageView(image1);
        previousbutton.setGraphic(imageView1);
        imageView1.setFitWidth(50);
        imageView1.setFitHeight(50);
        
        Image image2 = new Image(new File("images/pause-button.png").toURI().toString());
        ImageView imageView2 = new ImageView(image2);
        pausebutton.setGraphic(imageView2);
        imageView2.setFitWidth(50);
        imageView2.setFitHeight(50);
        
        Image image3 = new Image(new File("images/forward-button.png").toURI().toString());
        ImageView imageView3 = new ImageView(image3);
        nextbutton.setGraphic(imageView3);
        imageView3.setFitWidth(50);
        imageView3.setFitHeight(50);
        
        Image image4 = new Image(new File("images/play-button.png").toURI().toString());
        ImageView imageView4 = new ImageView(image4);
        playbutton.setGraphic(imageView4);
        imageView4.setFitWidth(50);
        imageView4.setFitHeight(50);
        
        Image image5 = new Image(new File("images/replay-button.png").toURI().toString());
        ImageView imageView5 = new ImageView(image5);
        replaybutton.setGraphic(imageView5);
        imageView5.setFitWidth(30);
        imageView5.setFitHeight(30);
        
        Image image6 = new Image(new File("images/playlist.png").toURI().toString());
        ImageView imageView6 = new ImageView(image6);
        playlist.setGraphic(imageView6);
        imageView6.setFitWidth(30);
        imageView6.setFitHeight(30);
        
        previousbutton.setPrefHeight(70);
        previousbutton.setPrefWidth(100);

        pausebutton.setPrefHeight(70);
        pausebutton.setPrefWidth(100);
        
        nextbutton.setPrefHeight(70);
        nextbutton.setPrefWidth(100);
        
        playbutton.setPrefHeight(70);
        playbutton.setPrefWidth(100);
        
        replaybutton.setPrefHeight(55);
        replaybutton.setPrefWidth(100);
        
        slider.setPrefHeight(55);
        slider.setPrefWidth(100);
        
        choiceBoxbutton.setPrefHeight(55);
        choiceBoxbutton.setPrefWidth(100);
        // create options for speed
        for(int i = 0; i<speeds.length;i++){
            choiceBoxbutton.getItems().add(Integer.toString(speeds[i]));
        }
        choiceBoxbutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
             public void handle(ActionEvent event){
                 if (choiceBoxbutton.getValue()==null){
                     mediaPlayer.setRate(1);
                 }else{mediaPlayer.setRate(Integer.parseInt((String)choiceBoxbutton.getValue())*0.01);
                 }
             }     
        });
        playlist.setPrefHeight(55);
        playlist.setPrefWidth(100);
        
        //run playlist
        Playlist playList = new Playlist();
        playlist.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                    playList.start(primaryStage);
                    mediaPlayer.stop();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
       // add songs to file
        songs = new ArrayList<File>();
        directory = new File("music");
        files = directory.listFiles();
        if(files!=null){
            for(File file:files){
                songs.add(file);
//                System.out.println(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());
        
        playbutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                try{
                beginTimer();
                mediaPlayer.play();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        pausebutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
             public void handle(ActionEvent event){
                try{
                cancelTimer();
                mediaPlayer.pause();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        replaybutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
             public void handle(ActionEvent event){
                try{
                progressBar.setProgress(0);
                mediaPlayer.seek(javafx.util.Duration.seconds(0));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        nextbutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
             public void handle(ActionEvent event){
               if(songNumber<songs.size()-1){
                   songNumber++;
                   mediaPlayer.stop();
                    if (running){
                       cancelTimer();
                   }
                   media = new Media(songs.get(songNumber).toURI().toString());
                   mediaPlayer = new MediaPlayer(media);
                   songLabel.setText(songs.get(songNumber).getName());
                   mediaPlayer.play();
               }else{
                   songNumber = 0;
                   mediaPlayer.stop();
                    if (running){
                       cancelTimer();
                   }
                   media = new Media(songs.get(songNumber).toURI().toString());
                   mediaPlayer = new MediaPlayer(media);
                   songLabel.setText(songs.get(songNumber).getName());
                   mediaPlayer.play();
               }
            }
        });
        previousbutton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
             public void handle(ActionEvent event){
               if(songNumber>0){
                   songNumber--;
                   mediaPlayer.stop();
                   if (running){
                       cancelTimer();
                   }
                   media = new Media(songs.get(songNumber).toURI().toString());
                   mediaPlayer = new MediaPlayer(media);
                   songLabel.setText(songs.get(songNumber).getName());
                   mediaPlayer.play();
               }else{
                   songNumber = songs.size()-1;
                   mediaPlayer.stop();
                    if (running){
                       cancelTimer();
                   }
                   media = new Media(songs.get(songNumber).toURI().toString());
                   mediaPlayer = new MediaPlayer(media);
                   songLabel.setText(songs.get(songNumber).getName());
                   mediaPlayer.play();
               }
            }
        });
        slider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(slider.getValue()*0.01);
            }
        });  
        timer = new Timer();
        task = new TimerTask(){
            @Override
            public void run(){
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
//                System.out.println(current/end);
                progressBar.setProgress(current/end);
                if(current/end==1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);

        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(15);
        progressBar.setStyle("-fx-accent:"+"lightgreen;");
                
        songLabel.setAlignment(Pos.CENTER);
        songLabel.setPrefHeight(55);
        songLabel.setPrefWidth(400);
        songLabel.setFont(new Font("SF Pro", 20));
        slider.setStyle("-fx-background-color:"+"lightgrey;");
        borderPane.setStyle("-fx-background-color: teal;");
        songLabel.setStyle("-fx-background-color: black;");
        songLabel.setTextFill(Color.rgb(41,197,153));
              
        buttons.setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(songLabel,progressBar,buttons);
        borderPane.setBottom(vBox);
        borderPane.setCenter(musicCover);
        
        Scene scene=new Scene(borderPane, 400, 600);
        primaryStage.setTitle("Retro music player");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
