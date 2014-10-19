package br.uff.es2.war.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaPrincipal extends Application {

    @Override
    public void start(Stage primaryStage) {
        //Título da janela
        primaryStage.setTitle("War UFF");
        primaryStage.setResizable(false);

        //a grade onde ficam os botões
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        //botão de novo jogo
        Button btnNovo = new Button("Novo jogo");
        btnNovo.setPrefWidth(100);
        HBox hbBtnNovo = new HBox(10);
        hbBtnNovo.setAlignment(Pos.CENTER);
        hbBtnNovo.getChildren().add(btnNovo);
        grid.add(hbBtnNovo, 1, 1);

        //Botão de carregar o jogo
        Button btnCarregar = new Button("Carregar");
        btnCarregar.setPrefWidth(100);
        HBox hbBtnCarregar = new HBox(10);
        hbBtnCarregar.setAlignment(Pos.CENTER);
        hbBtnCarregar.getChildren().add(btnCarregar);
        grid.add(hbBtnCarregar, 1, 2);

        //Botão de sair
        Button btnSair = new Button("Sair");
        btnSair.setPrefWidth(100);
        HBox hbBtnSair = new HBox(10);
        hbBtnSair.setAlignment(Pos.CENTER);
        hbBtnSair.getChildren().add(btnSair);
        grid.add(hbBtnSair, 1, 3);

        //Ação do botão Novo
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaCriacaoJogo JJogo = new JanelaCriacaoJogo();
                JJogo.start(primaryStage);
            }
        });

        //Ação do botão carregar
        btnCarregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaCarregarJogo JCarregar = new JanelaCarregarJogo();
                JCarregar.start(primaryStage);
            }
        });

        //Ação do botão sair
        btnSair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });

        //Mostrar
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaPrincipal.css");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
