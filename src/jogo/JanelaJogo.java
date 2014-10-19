/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import com.sun.javafx.iio.ImageStorage;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import util.AdministradorDeArquivo;


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
        scene.getStylesheets().add(JanelaJogo.class.getResource("JanelaJogo.css").toExternalForm());
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
        
        gameImage = new ImageView(new Image("resources/gamePlus.png"));
        gameImage.getStyleClass().add("gameImage");
        gameImage.setFitHeight(576);
        gameImage.setFitWidth(564);
        
        gameImage.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) 
            {
                double r = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getRed();
                double g = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getGreen();
                double b = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getBlue();
                
                int red =(int) (r*255);
                int green = (int) (g*255);
                int blue = (int) (b*255);
                
                if(red >= 250)
                {
                    if(green >= 250)
                    {
                        System.out.println("esta no valonguinho");
                    }else
                    {
                        System.out.println("esta no gragoata");
                    }
                }
                else if(green >= 250)
                {
                    System.out.println("esta em Unidades isoladas");
                }
                else if(blue >= 250)
                {
                    System.out.println("esta na praia vermelha");
                }
                System.out.println("red "+ red+","+" green "+green+","+" blue "+blue);
                
            }
            
        });
        
        pane.getChildren().add(gameImage);
        //constroiCirculos();
        
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
    
    private void  constroiCirculos()
    {
        ArrayList<TerritorioTela> lista = new ArrayList<>();
        try {
             lista = AdministradorDeArquivo.listaTerritorios();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for(TerritorioTela t : lista)
        {
            pane.getChildren().addAll(t.getCirculo(),t.getLabel());
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
