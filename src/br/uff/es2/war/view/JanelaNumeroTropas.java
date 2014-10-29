/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.model.Pais;
import javafx.application.Application;
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
 * @author RulffdaCosta
 */
public class JanelaNumeroTropas extends Application
{
    private AnchorPane pane;
    private Stage stage;
    private Pais paisAtacante;
    private ComboBox cBox;
    private Label lbNumeroTropas;
    private Button btnOk;
    private int qtdSelecionada;
            
    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        initComponents();
        stage = primaryStage;
        
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/stylesheet/JanelaNumeroTropas.css");
        this.stage.setScene(scene);
        
        stage.show();
        
        initListeners();
        initLayout();
    }
    
    /*public JanelaNumeroTropas(Pais p)
    {
    super();
    paisAtacante = p;
    }*/

    private void initComponents() 
    {
        pane = new AnchorPane();
        pane.setPrefSize(200, 200);
        pane.getStyleClass().add("pane");
        
        cBox = new ComboBox();
        int numeroTropas = 3;
        
        for(int i = 1;i <= numeroTropas;i++)
        {
            cBox.getItems().add(Integer.toString(i));
        }
        
        lbNumeroTropas = new Label();
        lbNumeroTropas.setText("Selecione o numero de\ntropas para ataque");
        
        btnOk = new Button("ok");
        
        pane.getChildren().addAll(cBox,lbNumeroTropas,btnOk);
    }
    
    private void initLayout() 
    {
        cBox.setLayoutX( (pane.getWidth() - cBox.getWidth())/2 );
        cBox.setLayoutY((pane.getHeight() - cBox.getHeight())/2);
        
        lbNumeroTropas.setLayoutX( (pane.getWidth() - lbNumeroTropas.getWidth())/2 );
        lbNumeroTropas.setLayoutY( 50 );
        
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
                qtdSelecionada = Integer.parseInt(cBox.getValue().toString());
                
                stage.close();
            }
        });
    }

    public int getTropaSelecionada() {
        return qtdSelecionada;
    }
    
}
