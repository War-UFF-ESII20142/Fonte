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
        table.setId("tableView");
        
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));
        grid.add(table, 0, 0);
        //grid.getChildren().addAll(table);

//        Text scenetitle = new Text("Carregar jogo");
//        grid.add(scenetitle, 0, 0, 2, 1);
//        scenetitle.setId("Titulo");

        Button btnCarregar = new Button("Carregar");
        btnCarregar.setPrefWidth(100);
        HBox hbBtnCarregar = new HBox(10);
        hbBtnCarregar.setAlignment(Pos.CENTER_RIGHT);
        hbBtnCarregar.getChildren().add(btnCarregar);
        grid.add(hbBtnCarregar, 0, 1);
        
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(JanelaPrincipal.class
                        .getResource("JanelaPrincipal.css")
                        .toExternalForm());
        primaryStage.setTitle("Carregar Jogo Salvo");
//        primaryStage.setWidth(800);
//        primaryStage.setHeight(600);

        table.setEditable(true);

        TableColumn nomeJogo = new TableColumn("Nome do jogo");
        TableColumn dataCriacao = new TableColumn( "Data de Criacao" );
        TableColumn dataSalvo = new TableColumn( "Data de Pausa" );
        TableColumn nomeJogador = new TableColumn("Nome do jogador ");
        TableColumn emailCol = new TableColumn("Cor do jogador");
        TableColumn numeroDeJogadores = new TableColumn( "Numero de Jogadores" );
        TableColumn numeroDeBots = new TableColumn( "Numero de Bots" );
        TableColumn Mapa = new TableColumn( "Mapa Utilizado" );
        

        table.getColumns().addAll(nomeJogo, dataCriacao, dataSalvo,
                nomeJogador, emailCol, numeroDeJogadores, numeroDeBots,Mapa );
        table.setStyle(
        ".table { -fx-position: center; }"
        );
        

//        final VBox vbox = new VBox();
//        vbox.setSpacing(5);
//        vbox.setPadding(new Insets(10, 0, 0, 10));
//        vbox.getChildren().addAll(label, table);

        //((Group) scene.getRoot()).getChildren().addAll(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
