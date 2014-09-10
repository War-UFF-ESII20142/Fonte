/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaCriacaoJogo {

    void start(Stage primaryStage) {
                primaryStage.setTitle("War UFF");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

//        Text scenetitle = new Text("Novo Jogo");
//        grid.add(scenetitle, 0, 0, 2, 1);
//        scenetitle.setId("Titulo");

        Button btnNovo = new Button("Novo jogo");
        btnNovo.setPrefWidth(100);
        HBox hbBtnNovo = new HBox(10);
        hbBtnNovo.setAlignment(Pos.CENTER);
        hbBtnNovo.getChildren().add(btnNovo);
        grid.add(hbBtnNovo, 1, 1);
        
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(JanelaPrincipal.class.getResource("JanelaPrincipal.css").toExternalForm());
        primaryStage.setTitle(" Criação de novo Jogo ");
        primaryStage.show();
    }
    
    
    
}
