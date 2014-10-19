/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import com.sun.javafx.iio.ImageStorage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import br.uff.es2.war.util.AdministradorDeArquivo;


/**
 *
 * @author AleGomes
 */
public class JanelaJogo extends Application
{
    private AnchorPane pane;
    private ImageView gameImage;
    private static Stage stage;
    private Label info;
    
    @Override
    public void start(Stage primaryStage) 
    {
        initComponents();
        
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/stylesheet/JanelaJogo.css");
        primaryStage.setTitle("War UFF");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        stage = primaryStage;
        
        initCircles();
        
        initLayout();
    }
    
    private void initComponents()
    {
        pane = new AnchorPane();
        pane.setPrefSize(1000, 600);
        
        gameImage = new ImageView(new Image("resources/gamePlus1.png"));
        gameImage.getStyleClass().add("gameImage");
        gameImage.setFitHeight(575);
        gameImage.setFitWidth(564);
        
        info = new Label();
        
        gameImage.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) 
            {
                String temp = "";
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
                        temp = inValonguinho(red);
                        gameImage.setCursor(Cursor.HAND);
                    }else
                    {
                        temp = inGragoata(red);
                        gameImage.setCursor(Cursor.HAND);
                    }
                }
                else if(green >= 250)
                {
                    temp = inUnidadeIsol(green);
                    gameImage.setCursor(Cursor.HAND);
                }
                else if(blue >= 250)
                {
                    temp = inPraiaVerm(blue);
                    gameImage.setCursor(Cursor.HAND);
                }else
                {
                    gameImage.setCursor(Cursor.DEFAULT);
                }
                info.setText(temp);
            }
        });
        
        gameImage.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event) {
                String temp = "";
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
                        temp = inValonguinho(red);
                        gameImage.setCursor(Cursor.HAND);
                    }else
                    {
                        temp = inGragoata(red);
                        gameImage.setCursor(Cursor.HAND);
                    }
                }
                else if(green >= 250)
                {
                    temp = inUnidadeIsol(green);
                    gameImage.setCursor(Cursor.HAND);
                }
                else if(blue >= 250)
                {
                    temp = inPraiaVerm(blue);
                    gameImage.setCursor(Cursor.HAND);
                }else
                {
                    gameImage.setCursor(Cursor.DEFAULT);
                }
                
                System.out.println(temp + " "+event.getSceneX()+" "+event.getSceneY());
            }
            
        });
        
        
        pane.getChildren().addAll(gameImage,info);
        //constroiCirculos();
        
    }
    
    private String inValonguinho(int red)
    {
        String pais = "";
        switch(red)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
            case 250:
                pais = "pais 6";
                break;
        }
        
        return ("Está no: " + pais+" do continente Valonguinho");
    }
    
    private String inGragoata(int red)
    {
        String pais = "";
        switch(red)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        
        return ("Está no: " + pais+" do continente Gragoata");
    }
    
    private String inPraiaVerm(int blue)
    {
        String pais = "";
        switch(blue)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        
        return ("Está no: " + pais+" do continente Praia Vermelha");
    }
    
    private String inUnidadeIsol(int green)
    {
        String pais = "";
        switch(green)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        
        return ("Está no: " + pais+" do continente UnidadeIsolada");
    }
    
    private void initLayout()
    {
        gameImage.setLayoutX(10);
        gameImage.setLayoutY(20);
    }
    
    public static Stage getStage()
    {
        return stage;
    }
    
    private void initCircles() 
    {
        ArrayList<TerritorioTela> lista = new ArrayList<>();
        try {
            lista = AdministradorDeArquivo.listaTerritorios();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for(TerritorioTela t : lista)
        {
            Circle circle = t.getCirculo();
            pane.getChildren().addAll(circle);
        }
        
        for(TerritorioTela t:lista)
        {
            Label label = t.getLabel();
            pane.getChildren().add(label);
        }
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
