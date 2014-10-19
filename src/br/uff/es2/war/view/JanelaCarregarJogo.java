/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));
        grid.add(table, 0, 0);
        
        GridPane bottonGrid = new GridPane();
        bottonGrid.setAlignment(Pos.BASELINE_RIGHT);
        grid.add(bottonGrid, 0, 1);
        
        Button btnCarregar = new Button("Carregar");
        btnCarregar.setPrefWidth(100);
        HBox hbBtnCarregar = new HBox(10);
        hbBtnCarregar.setAlignment(Pos.CENTER_RIGHT);
        hbBtnCarregar.getChildren().add(btnCarregar);
        bottonGrid.add(hbBtnCarregar, 0, 0);
        
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setPrefWidth(100);
        HBox hbBtnNovo = new HBox(10);
        hbBtnNovo.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnNovo.getChildren().add(btnVoltar);
        bottonGrid.add(hbBtnNovo, 1, 0);
        
        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaPrincipal jp = new JanelaPrincipal();
                jp.start(primaryStage);
            }
        });
        
        Scene scene = new Scene(grid, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaCarregarJogo.css");
        primaryStage.setTitle("Carregar Jogo Salvo");

        table.setEditable(true);

        TableColumn nomeJogo = new TableColumn("Jogo");
        TableColumn dataCriacao = new TableColumn( "Criacao" );
        TableColumn dataSalvo = new TableColumn( "Pausa" );
        TableColumn nomeJogador = new TableColumn("Nome");
        TableColumn emailCol = new TableColumn("Cor");
        TableColumn numeroDeJogadores = new TableColumn( "Nº Jogadores" );
        TableColumn numeroDeBots = new TableColumn( "Nº Bots" );
        TableColumn Mapa = new TableColumn( "Mapa " );
        

        table.getColumns().addAll(nomeJogo, dataCriacao, dataSalvo,
                nomeJogador, emailCol, numeroDeJogadores, numeroDeBots,Mapa );
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
