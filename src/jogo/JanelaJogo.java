/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 *
 * @author AleGomes
 */
public class JanelaJogo extends Application
{
    private AnchorPane pane;
    private ImageView gameImage;
    private static Stage stage;
    
    @Override
    public void start(Stage primaryStage) 
    {
        initComponents();
        
        Scene scene = new Scene(pane);
        primaryStage.setTitle("War UFF");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
        
        initLayout();
    }
    
    private void initComponents()
    {
        pane = new AnchorPane();
        pane.setPrefSize(1000, 600);
        
        gameImage = new ImageView(new Image("resources/gameMap.jpg"));
        gameImage.setFitHeight(400);
        gameImage.setFitWidth(800);
        
        pane.getChildren().addAll(gameImage);
    }
    
    private void initLayout()
    {
        gameImage.setLayoutX(10);
        gameImage.setLayoutY(10);
    }
    
    public static Stage getStage()
    {
        return stage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
