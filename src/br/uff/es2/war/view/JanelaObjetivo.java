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
    private Label lbTitulo, lbObjetivo;
    private Button btnOk;
  
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
        lbTitulo.setText("O seu objetivo:");
        lbObjetivo = new Label();
        lbObjetivo.setText("Conquiste o mundo.");
        
        btnOk = new Button("ok");
        
        pane.getChildren().addAll(lbObjetivo,lbTitulo,btnOk);
    }
    
    private void initLayout() 
    {
        lbObjetivo.setLayoutX( (pane.getWidth() - lbTitulo.getWidth())/2 );
        lbObjetivo.setLayoutY((pane.getHeight() - lbTitulo.getHeight())/2);
        
        lbTitulo.setLayoutX( (pane.getWidth() - lbObjetivo.getWidth())/2 );
        lbTitulo.setLayoutY( 50 );
        
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
