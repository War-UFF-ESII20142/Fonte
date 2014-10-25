/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.view;

import br.uff.es2.war.WindowManager;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.model.Partida;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author AleGomes
 */
public class JanelaCarregarJogo extends Application implements iWindow{
    private AnchorPane pane;
    private Button btnCarregar;
    private Button btnVoltar;
    private HBox horizontalButtonBox;
    private TableView<Partida> tbPartidas;
    private ImageView imgLogo;
    private WindowManager windowController;
    private Stage stage;
    
    TableColumn<Partida,String> tcNomeJogo;
    TableColumn<Partida,String> tcDataCriacao;
    TableColumn<Partida,String> tcDataSalvo;
    TableColumn<Partida,String> tcNomeJogador ;
    TableColumn<Partida,String> tcEmailCol ;
    TableColumn<Partida,String> tcNumeroDeJogadores ;
    TableColumn<Partida,String> tcNumeroDeBots ;
    TableColumn<Partida,String> tcMapa ;

    @Override
    public void start(Stage primaryStage) {
        initComponents();
        setStage(primaryStage);
        primaryStage.setTitle("War UFF");

        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaCarregarJogo.css");
        primaryStage.setTitle("Carregar Jogo Salvo");
               
        primaryStage.setScene(scene);
        primaryStage.show();
        
        initLayout();
        initListeners();
    }
    
    public void setWindowController(WindowManager manager)
    {
        this.windowController = manager;
    }
    
    private void initLayout()
    {
        imgLogo.setLayoutX(200);
        imgLogo.setLayoutY(10);
        
        tbPartidas.setLayoutX( (pane.getWidth() - tbPartidas.getWidth())/2 );
        tbPartidas.setLayoutY(110);
        
        horizontalButtonBox.setLayoutX( (pane.getWidth() - horizontalButtonBox.getWidth())/2 );
        horizontalButtonBox.setLayoutY( (pane.getHeight() - 40 - horizontalButtonBox.getHeight()) );
        
    }
    
    private void initListeners()
    {
        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                windowController.startMainWindow(JanelaCarregarJogo.this);
            }
        });
    }
    
    private void initComponents()
    {
        pane = new AnchorPane();
        pane.getStyleClass().add("pane");
        pane.setPrefSize(800, 600);
        
        tbPartidas = new TableView();
        tbPartidas.getStyleClass().add("tbpartidas");
        tbPartidas.setMinWidth(580);
        tbPartidas.setPrefWidth(580);
        tbPartidas.setEditable(true);
        

        tcNomeJogo = new TableColumn<>("Jogo");
        tcNomeJogo.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcDataCriacao = new TableColumn<>( "Criacao" );
        tcDataCriacao.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcDataSalvo = new TableColumn<>( "Pausa" );
        tcDataSalvo.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcNomeJogador = new TableColumn<>("Nome");
        tcNomeJogador.setPrefWidth(tbPartidas.getMinWidth() / 8);
                
        tcEmailCol = new TableColumn<>("Cor");
        tcEmailCol.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcNumeroDeJogadores = new TableColumn<>( "Nº Jogadores" );
        tcNumeroDeJogadores.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcNumeroDeBots = new TableColumn<>( "Nº Bots" );
        tcNumeroDeBots.setPrefWidth(tbPartidas.getMinWidth() / 8);
        
        tcMapa = new TableColumn<>( "Mapa " );
        tcMapa.setPrefWidth(tbPartidas.getMinWidth() / 8);
        

        tbPartidas.getColumns().addAll(tcNomeJogo,
                                       tcDataCriacao,
                                       tcDataSalvo,
                                       tcNomeJogador,
                                       tcEmailCol,
                                       tcNumeroDeJogadores,
                                       tcNumeroDeBots,
                                       tcMapa);
                
        btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");
        
        btnCarregar = new Button("Carregar");
        btnCarregar.getStyleClass().add("button");
        
        horizontalButtonBox = new HBox(15);
        horizontalButtonBox.getChildren().addAll(btnVoltar,btnCarregar);
        
        imgLogo = new ImageView(new Image("resources/war-uff.png"));
        
        pane.getChildren().addAll(imgLogo, tbPartidas,horizontalButtonBox);
        
    }
    
    @Override
    public Stage getStage()
    {
        return stage;
    }
    
    @Override
    public void setStage(Stage stage) 
    {
        this.stage = stage;
    }
    
}
