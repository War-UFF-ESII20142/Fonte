/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import br.uff.es2.war.model.HumanPlayer;

/**
 *
 * @author AleGomes
 */
public class JanelaCriacaoJogo {

    final ObservableList<HumanPlayer> Players = FXCollections.observableArrayList();
    ComboBox cBoxCor = new ComboBox();

    void start(Stage primaryStage) {
        primaryStage.setTitle("War UFF");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //
        TableView<HumanPlayer> table = new TableView<>();
        //
        table.setId("tableView");

        grid.add(table, 0, 0);

        GridPane bottonGrid = new GridPane();
        bottonGrid.setAlignment(Pos.CENTER);

        grid.add(bottonGrid, 0, 1);

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

        cBoxCor.getItems().addAll(
                "Verde", "Vermelho", "Preto", "Azul", "Branco",
                "Amarelo"
        );

//        Button removeHuman = new Button("-H");
//        removeHuman.setPrefWidth(100);
//        HBox removeHumanBox = new HBox(10);
//        removeHumanBox.setAlignment(Pos.BOTTOM_LEFT);
//        removeHumanBox.getChildren().add(removeHuman);
//        bottonGrid.add(removeHumanBox, 1, 1);
        Button addBot = new Button("+B");
        addBot.setPrefWidth(100);
        HBox addBotBox = new HBox(10);
        addBotBox.setAlignment(Pos.BOTTOM_LEFT);
        addBotBox.getChildren().add(addBot);
        bottonGrid.add(addBotBox, 2, 1);

//        Button removeBot = new Button("-B");
//        removeBot.setPrefWidth(100);
//        HBox removeBotBox = new HBox(10);
//        removeBotBox.setAlignment(Pos.BOTTOM_LEFT);
//        removeBotBox.getChildren().add(removeBot);
//        bottonGrid.add(removeBotBox, 3, 1);
        Button remove = new Button("Remove");
        remove.setPrefWidth(100);
        HBox removeBox = new HBox(10);
        removeBox.setAlignment(Pos.BOTTOM_LEFT);
        removeBox.getChildren().add(remove);
        bottonGrid.add(removeBox, 3, 1);

        table.setEditable(true);

        TableColumn nomeJogador = new TableColumn("Nome");
        TableColumn cor = new TableColumn("Cor");
        TableColumn tipo = new TableColumn("Tipo");

        table.getColumns().addAll(nomeJogador, cor, tipo);
        //table.setStyle(".table { -fx-position: center; }");

        nomeJogador.setCellValueFactory(new PropertyValueFactory<HumanPlayer, String>("nome")
        );
        cor.setCellValueFactory(new PropertyValueFactory<HumanPlayer, String>("cor")
        );
        tipo.setCellValueFactory(new PropertyValueFactory<HumanPlayer, String>("tipo")
        );

        //Remover jogador
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removePlayer(primaryStage);
            }
        });

        //Adicionar bot
        addBot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addNewPlayer(cBoxCor, "Bot", primaryStage);
            }
        });

        // Adicionar jogador humano
        addHuman.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addNewPlayer(cBoxCor, "Humano", primaryStage);
            }
        });

        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaPrincipal jp = new JanelaPrincipal();
                jp.start(primaryStage);
            }
        });
        
        Button criaJogo = new Button("Criar Jogo");
        criaJogo.setPrefWidth(100);
        HBox criaJogoBox = new HBox(10);
        criaJogoBox.setAlignment(Pos.BOTTOM_LEFT);
        criaJogoBox.getChildren().add(criaJogo);
        bottonGrid.add(criaJogoBox, 5, 1);
        
        criaJogo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new JanelaJogo().start(new Stage());
                primaryStage.close();
            }
        });

        table.setItems(Players);
        Scene scene = new Scene(grid, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaCriacaoJogo.css");
        primaryStage.setTitle(" Criação de novo Jogo ");
        primaryStage.show();

        //
    }

    public void addNewPlayer(ComboBox cBoxCor, String tipo, Stage primaryStage) {
        TextField TFNome = new TextField();
        TFNome.setPromptText("Nome");

        Button btnOk = new Button("Ok");
        Button btnCancelar = new Button("Cancelar");
        GridPane grid = new GridPane();

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Nome: "), 0, 0);
        grid.add(TFNome, 1, 0);
        grid.add(new Label("Cor: "), 0, 1);
        grid.add(cBoxCor, 1, 1);

        grid.add(btnOk, 0, 4);
        grid.add(btnCancelar, 1, 4);

        Scene newPlayerScene = new Scene(new Group(), 250, 150);
        Stage newPlayerStage = new Stage();
        newPlayerStage.setTitle("New Player");
        Group root = (Group) newPlayerScene.getRoot();
        root.getChildren().add(grid);
        newPlayerStage.setScene(newPlayerScene);
        newPlayerStage.setX(primaryStage.getX() + 250);
        newPlayerStage.setY(primaryStage.getY() + 100);
        newPlayerStage.show();

        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newPlayerStage.close();
            }
        });

        btnOk.setOnAction((ActionEvent x) -> {
            HumanPlayer p = new HumanPlayer(TFNome.getText(), cBoxCor.getValue().toString(), tipo);
            Players.add(p);
            cBoxCor.getItems().remove(cBoxCor.getValue().toString());
            newPlayerStage.close();
        });

    }

    private void removePlayer(Stage primaryStage) {
        TextField TFNome = new TextField();
        TFNome.setPromptText("Nome");

        Button btnOk = new Button("Ok");
        Button btnCancelar = new Button("Cancelar");
        GridPane grid = new GridPane();

        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("Nome: "), 0, 0);
        grid.add(TFNome, 1, 0);

        grid.add(btnOk, 0, 4);
        grid.add(btnCancelar, 1, 4);

        Scene newPlayerScene = new Scene(new Group(), 250, 150);
        Stage newPlayerStage = new Stage();
        newPlayerStage.setTitle("New Player");
        Group root = (Group) newPlayerScene.getRoot();
        root.getChildren().add(grid);
        newPlayerStage.setScene(newPlayerScene);
        newPlayerStage.setX(primaryStage.getX() + 250);
        newPlayerStage.setY(primaryStage.getY() + 100);
        newPlayerStage.show();

        btnCancelar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                newPlayerStage.close();
            }
        });

        btnOk.setOnAction((ActionEvent x) -> {
            for (int i = 0; i < Players.size(); i++) {
                if (Players.get(i).getNome().equals(TFNome.getText())) {
                    cBoxCor.getItems().add(Players.get(i).getCor());
                    Players.remove(i);
                }
            }
            newPlayerStage.close();
        });
    }
}
