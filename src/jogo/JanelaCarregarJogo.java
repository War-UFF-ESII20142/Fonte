/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaCarregarJogo {

    void start(Stage primaryStage) {
        primaryStage.setTitle("War UFF");

        TableView table = new TableView();
        
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.getChildren().addAll(label, table);

        Text scenetitle = new Text("Carregar jogo");
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.setId("Titulo");

        Button btnCarregar = new Button("Carregar");
        btnCarregar.setPrefWidth(100);
        HBox hbBtnCarregar = new HBox(10);
        hbBtnCarregar.setAlignment(Pos.CENTER);
        hbBtnCarregar.getChildren().add(btnCarregar);
        grid.add(hbBtnCarregar, 1, 1);
        
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(TelaInicial.class.getResource("TelaInicial.css").toExternalForm());
        primaryStage.setTitle("Table View Sample");
        primaryStage.setWidth(300);
        primaryStage.setHeight(500);

        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");

        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);

//        final VBox vbox = new VBox();
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll(label, table);

        //((Group) scene.getRoot()).getChildren().addAll(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
