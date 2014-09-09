/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaJogo {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("War UFF");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Novo Jogo");
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setId("Titulo");
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(TelaInicial.class.getResource("TelaInicial.css").toExternalForm());
        primaryStage.show();
    }
}
