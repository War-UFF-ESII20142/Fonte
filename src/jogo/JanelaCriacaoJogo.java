/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

        TableView table = new TableView();
        table.setId("tableView");
        
        grid.add(table,0,0);
        
        GridPane bottonGrid = new GridPane();
        bottonGrid.setAlignment(Pos.CENTER);
        
        grid.add(bottonGrid,0,1);

        Button btnNovo = new Button("Voltar");
        btnNovo.setPrefWidth(100);
        HBox hbBtnNovo = new HBox(10);
        hbBtnNovo.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtnNovo.getChildren().add(btnNovo);
        bottonGrid.add(hbBtnNovo, 4, 1);
        
        
        Button addHuman = new Button("+H");
        addHuman.setPrefWidth(100);
        HBox addHumanBox = new HBox(10);
        addHumanBox.setAlignment(Pos.BOTTOM_LEFT);
        addHumanBox.getChildren().add(addHuman);
        bottonGrid.add(addHumanBox, 0, 1);
        
        Button removeHuman = new Button("-H");
        removeHuman.setPrefWidth(100);
        HBox removeHumanBox = new HBox(10);
        removeHumanBox.setAlignment(Pos.BOTTOM_LEFT);
        removeHumanBox.getChildren().add(removeHuman);
        bottonGrid.add(removeHumanBox, 1, 1);
        
        Button addBot = new Button("+B");
        addBot.setPrefWidth(100);
        HBox addBotBox = new HBox(10);
        addBotBox.setAlignment(Pos.BOTTOM_LEFT);
        addBotBox.getChildren().add(addBot);
        bottonGrid.add(addBotBox, 2, 1);
        
        Button removeBot = new Button("-B");
        removeBot.setPrefWidth(100);
        HBox removeBotBox = new HBox(10);
        removeBotBox.setAlignment(Pos.BOTTOM_LEFT);
        removeBotBox.getChildren().add(removeBot);
        bottonGrid.add(removeBotBox, 3, 1);
        
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaPrincipal jp = new JanelaPrincipal();
                jp.start(primaryStage);
            }
        });
        
        
        table.setEditable(true);

        TableColumn nomeJogador = new TableColumn("Nome");
        TableColumn cor = new TableColumn("Cor");
        TableColumn tipo = new TableColumn( "Tipo" );
        
        table.getColumns().addAll(nomeJogador, cor, tipo);
        //table.setStyle(".table { -fx-position: center; }");
        
        Scene scene = new Scene(grid, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        scene.getStylesheets().add(JanelaCriacaoJogo.class.getResource("JanelaCriacaoJogo.css").toExternalForm());
        primaryStage.setTitle(" Criação de novo Jogo ");
        primaryStage.show();
    }
    
    
    
}
