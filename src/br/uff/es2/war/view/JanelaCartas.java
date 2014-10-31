/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaCartas extends Application {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    private AnchorPane pane;
    private Stage stage;
    private Label lbTitulo;
    private Button btnOk;
    private Button btnTrocar;
  
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        initComponents();
        stage = primaryStage;
        
        Scene scene = new Scene(pane);
        //scene.getStylesheets().add("/stylesheet/JanelaNumeroTropas.css");
        this.stage.setScene(scene);
        
        stage.show();
        
        initListeners();
        initLayout();
    }
    
    private void initComponents() 
    {
        pane = new AnchorPane();
        pane.setPrefSize(200, 200);
        pane.getStyleClass().add("pane");
        
        lbTitulo = new Label();
        lbTitulo.setText("Suas Cartas:");
        
        //Tabela com quadradinho de marcar;
        
        //Botao de fazer trocas
        
        btnOk = new Button("ok");
        btnTrocar = new Button("Trocar");
        
        pane.getChildren().addAll(btnTrocar,lbTitulo,btnOk);
    }
    
    private void initLayout() 
    {
        lbTitulo.setLayoutX( (pane.getWidth() - lbTitulo.getWidth())/2 );
        lbTitulo.setLayoutY( 50 );
        
        btnOk.setLayoutX( (pane.getWidth() - btnOk.getWidth())/2 );
        btnOk.setLayoutY(150);
        btnTrocar.setLayoutX( (pane.getWidth() - btnOk.getWidth())/2 );
        btnTrocar.setLayoutY(150);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    private void initListeners() 
    {
        btnOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                stage.close();
            }
        });
        
        btnTrocar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                //Realizar troca
            }
        });
    }
}
