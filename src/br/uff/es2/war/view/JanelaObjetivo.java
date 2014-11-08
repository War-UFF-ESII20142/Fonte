/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.model.Pais;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaObjetivo extends Application {

    private AnchorPane pane;
    private Stage stage;
    private Label lbObjetivo;
    private Button btnOk;
    private String objetivo;
    
    public JanelaObjetivo(String objetivo){
        this.objetivo = objetivo;
    }
  
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        initComponents();
        stage = primaryStage;
        
        Scene scene = new Scene(pane);
        //scene.getStylesheets().add("/stylesheet/JanelaNumeroTropas.css");
        this.stage.setScene(scene);
        this.stage.setTitle("Objetivo");
        stage.show();
        
        initListeners();
        initLayout();
    }
    
    private void initComponents() 
    {
        pane = new AnchorPane();
        pane.setPrefSize(200, 200);
        pane.getStyleClass().add("pane");
        
        lbObjetivo = new Label();
        
        lbObjetivo.setText("Conquistar 21 territórios");
        
        btnOk = new Button("ok");
        
        pane.getChildren().addAll(lbObjetivo,btnOk);
    }
    
    private void initLayout() 
    {
        lbObjetivo.setLayoutX( (pane.getWidth() - lbObjetivo.getWidth())/2 );
        lbObjetivo.setLayoutY((pane.getHeight() - lbObjetivo.getHeight())/2);
        
        btnOk.setLayoutX( (pane.getWidth() - btnOk.getWidth())/2 );
        btnOk.setLayoutY(150);
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
    }
}
