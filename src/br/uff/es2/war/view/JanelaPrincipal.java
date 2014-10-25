package br.uff.es2.war.view;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaPrincipal extends Application {
    private AnchorPane pane;
    private Button btnNovo;
    private Button btnCarregar;
    private Button btnSair;
    private ImageView imgLogo;
    private VBox verticalButtonBox;
    private Stage stage;
    
    @Override
    public void start(Stage primaryStage) 
    {
        initComponents();
        
        setStage(primaryStage);
        //Título da janela
        primaryStage.setTitle("War UFF");
        primaryStage.setResizable(false);

        //Mostrar
        Scene scene = new Scene(pane, 800, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaPrincipal.css");
        primaryStage.show();
        
        initLayout();
        initListeners();
    }
    
    private void initListeners()
    {
        //Ação do botão Novo
        btnNovo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaCriacaoJogo JJogo = new JanelaCriacaoJogo();
               // JJogo.start(primaryStage);
            }
        });

        //Ação do botão carregar
        btnCarregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JanelaCarregarJogo JCarregar = new JanelaCarregarJogo();
               // JCarregar.start(primaryStage);
            }
        });

        //Ação do botão sair
        btnSair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //primaryStage.close();
            }
        });        
    }
    
    private Stage getStage()
    {
        return stage;
    }
    
    private void setStage(Stage stage)
    {
        this.stage = stage;
    }
    
    private void initComponents()
    {
        //a grade onde ficam os botões
        pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.getStyleClass().add("pane");
        
        imgLogo = new ImageView(new Image("resources/war-uff.png"));
        
        //botão de novo jogo
        btnNovo = new Button("Novo jogo");
        btnNovo.getStyleClass().add("botton");
        
        //Botão de carregar o jogo
        btnCarregar = new Button("Carregar");
        btnCarregar.getStyleClass().add("botton");
        

        //Botão de sair
        btnSair = new Button("Sair");
        btnSair.getStyleClass().add("botton");
        
        verticalButtonBox = new VBox(15);
        verticalButtonBox.getChildren().addAll(btnNovo,btnCarregar,btnSair);
        verticalButtonBox.setAlignment(Pos.CENTER);
        
        pane.getChildren().addAll(imgLogo,verticalButtonBox);
        
    }
    
    private void initLayout()
    {
        imgLogo.setLayoutX( 200 );
        imgLogo.setLayoutY(10);
        
        verticalButtonBox.setLayoutX( (pane.getWidth() - verticalButtonBox.getWidth())/2 );
        verticalButtonBox.setLayoutY( 200 );
    }
}
