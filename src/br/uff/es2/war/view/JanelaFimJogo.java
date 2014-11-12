/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.WindowManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.util.Tools;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author claudio
 */
public class JanelaFimJogo extends Application implements iWindow
{
    private Stage stage;
    private AnchorPane aPane;
    private Button btnInicio;
    private Label lbVitoria;
    private final Player winner;
    private final WindowManager windowController;
    private ImageView imgWar;
    
    public JanelaFimJogo(Player winner, WindowManager controller)
    {
        super();
        this.winner = winner;
        this.windowController = controller;
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception 
    {   
        initComponents();
        
        Scene scene = new Scene(aPane,800,600);
        scene.getStylesheets().add("/stylesheet/JanelaFimJogo.css");
        this.stage = primaryStage;
        this.stage.setScene(scene);
        this.stage.show();
        
        
        initLayout();
        initListeners();
    }

    private void initComponents() 
    {
        aPane = new AnchorPane();
        aPane.setPrefSize(1000, 600);
        aPane.setStyle("-fx-background-color:"+Tools.convertCorToColor(winner.getCor()));
        
        imgWar = new ImageView(new Image("resources/gamePlus1.png"));
        imgWar.getStyleClass().add("imgWar");
        
        btnInicio = new Button("Menu Iniciar");
        btnInicio.getStyleClass().add("button");
        
        lbVitoria = new Label("Parabéns "+winner.getNome()+" por vencer o WARUFF");
        
        aPane.getChildren().addAll(imgWar,btnInicio,lbVitoria);
        
    }

    private void initListeners() 
    {
        btnInicio.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                windowController.startMainWindow(JanelaFimJogo.this);
            }
        });
        
    }
    
    private void initLayout() 
    {
        imgWar.setLayoutX(10);
        imgWar.setLayoutY(20);
        
        lbVitoria.setLayoutX((aPane.getWidth() - lbVitoria.getWidth())/2);
        lbVitoria.setLayoutY((aPane.getHeight()- lbVitoria.getHeight())/2);
        
        btnInicio.setLayoutX((aPane.getWidth() - btnInicio.getWidth())/2);
        btnInicio.setLayoutY( aPane.getHeight() - 10 - btnInicio.getHeight() );
        
    }

    @Override
    public Stage getStage() 
    {
        return this.stage;
    }

    @Override
    public void setStage(Stage stage) 
    {
        this.stage = stage;
    }

    
}
