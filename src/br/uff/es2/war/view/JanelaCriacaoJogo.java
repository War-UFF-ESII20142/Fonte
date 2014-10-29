/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.WindowManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.stage.Stage;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.model.BotPlayer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import br.uff.es2.war.model.HumanPlayer;
import java.util.Iterator;
import javax.swing.JOptionPane;


/**
 *
 * @author Grupo1
 */
public class JanelaCriacaoJogo extends Application implements iWindow{

    private ObservableList<Player> olPlayers;
    private ComboBox cBoxCor;
    private AnchorPane pane;
    private Button btnAddHuman;
    private Button btnAddBot;
    private Button btnRemovePlayers;
    private Button btnVoltar;
    private Button btnCreateGame;
    private HBox horizontalButtonBox;
    private TableView<Player> tbPlayers;
    private TableColumn<Player,String> tcNamePlayer; 
    private TableColumn<Player,String> tcColorPlayer;
    private TableColumn<Player,String> tcTypePlayer;
    private Stage stage;
    private WindowManager windowController;
    private ImageView imgLogo;

    @Override
    public void start(Stage primaryStage) {
        initComponenets();
        
        this.stage = primaryStage;
        
        primaryStage.setTitle("War UFF");
        Scene scene = new Scene(pane, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        scene.getStylesheets().add("/stylesheet/JanelaCriacaoJogo.css");
        primaryStage.setTitle(" Criação de novo Jogo ");
        primaryStage.show();
        
        initListeners();
        initLayout();
    }
    
    public void setWindowController( WindowManager manager )
    {
        windowController = manager;
    }
    
    private void initLayout()
    {
        imgLogo.setLayoutX(200);
        imgLogo.setLayoutY(10);
        
        tbPlayers.setLayoutX( (pane.getWidth() - tbPlayers.getWidth())/2 );
        tbPlayers.setLayoutY(110);
        
        horizontalButtonBox.setLayoutX( (pane.getWidth() - horizontalButtonBox.getWidth())/2 );
        horizontalButtonBox.setLayoutY( pane.getHeight() - 40 - horizontalButtonBox.getHeight());
    }
    
    public ObservableList<Player> getPlayerList()
    {
        return olPlayers;
    }
    
    private void initListeners()
    {
        //Remover jogador
        btnRemovePlayers.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                removePlayer();
            }
        });

        //Adicionar bot
        btnAddBot.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addNewPlayer(cBoxCor, "Bot", new Stage());
            }
        });

        // Adicionar jogador humano
        btnAddHuman.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                addNewPlayer(cBoxCor, "Humano", new Stage());
            }
        });

        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                windowController.startMainWindow(JanelaCriacaoJogo.this);
            }
        });
        
        btnCreateGame.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) 
            {
                if(tbPlayers.getItems().size() > 1)
                {
                    windowController.startGamePlay(JanelaCriacaoJogo.this);
                }
                else
                {
                    JOptionPane.showConfirmDialog(null, "Há somente: "+tbPlayers.getItems().size()+"  elementos na lista ","Quantidade Minima não alcancaçada",JOptionPane.OK_CANCEL_OPTION);
                }
            }
        });
    }
    
    private void initComponenets()
    {
        olPlayers = FXCollections.observableArrayList();
        
        pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.getStyleClass().add("pane");

        tbPlayers = new TableView<>();
        tbPlayers.getStyleClass().add("tbPlayers");
        tbPlayers.setMinWidth(500);
        tbPlayers.setItems(olPlayers);
        tbPlayers.setEditable(true);
        
        btnAddHuman = new Button("+H");
        btnAddHuman.getStyleClass().add("button");
        
        btnAddBot = new Button("+B");
        btnAddBot.getStyleClass().add("button");
        
        btnRemovePlayers = new Button("Remove");
        btnRemovePlayers.getStyleClass().add("button");
        
        btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");
        
        btnCreateGame = new Button("Criar Jogo");
        btnCreateGame.getStyleClass().add("button");
        
        horizontalButtonBox = new HBox(15);
        horizontalButtonBox.getChildren().addAll(btnAddHuman,btnAddBot,btnRemovePlayers,btnVoltar,btnCreateGame);

        cBoxCor = new ComboBox();
        cBoxCor.getItems().addAll(
                "Verde", "Vermelho", "Preto", "Azul", "Branco",
                "Amarelo"
        );
        
        tcNamePlayer = new TableColumn<>("Nome");
        tcColorPlayer = new TableColumn<>("Cor");
        tcTypePlayer = new TableColumn("Tipo");

        tcNamePlayer.setCellValueFactory( new PropertyValueFactory<Player, String>("nome") );
        tcNamePlayer.setPrefWidth( tbPlayers.getMinWidth()/3 );
        
        tcColorPlayer.setCellValueFactory( new PropertyValueFactory<Player, String>("cor") );
        tcColorPlayer.setPrefWidth( tbPlayers.getMinWidth()/3 );
        
        tcTypePlayer.setCellValueFactory( new PropertyValueFactory<Player, String>("tipo") );
        tcTypePlayer.setPrefWidth( tbPlayers.getMinWidth()/3 );

        tbPlayers.getColumns().addAll(tcNamePlayer, tcColorPlayer, tcTypePlayer);
        
        imgLogo = new ImageView(new Image("resources/war-uff.png"));
        
        pane.getChildren().addAll(imgLogo,tbPlayers,horizontalButtonBox);
    }
    
    @Override
    public void setStage( Stage stage )
    {
        this.stage = stage;
    }
    
    @Override
    public Stage getStage()
    {
        return stage;
    }
    
    private void addNewPlayer(ComboBox cBoxCor, String tipo, Stage primaryStage) {
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
            if(cBoxCor.getValue() != null)
            {
                Player p = getPlayerFromFactory(TFNome.getText(), cBoxCor.getValue().toString(), tipo);
                olPlayers.add(p);
                cBoxCor.getItems().remove(cBoxCor.getValue().toString());
                newPlayerStage.close();
            }
            else
            {
                JOptionPane.showConfirmDialog(null, "nenhuma cor selecionada", "Erro!", JOptionPane.OK_CANCEL_OPTION);
            }
        });

    }

    private void removePlayer() 
    {
        ObservableList<Player> tempList = tbPlayers.getSelectionModel().getSelectedItems();
        
        Iterator<Player> itObservableList = tempList.iterator();
        
        while(itObservableList.hasNext())
        {
            Player aux = itObservableList.next();
            
            cBoxCor.getItems().add(aux.getCor());
            
            olPlayers.remove(aux);
            
        }
    }
    
    private Player getPlayerFromFactory(String nome, String cor, String tipo)
    {
        if(tipo.equals("Humano"))
        {
            return new HumanPlayer(nome,cor,tipo);
        }
        else if (tipo.equals("Bot"))
        {
            return new BotPlayer(nome,cor,tipo);
        }
        
        return null;
    }
}
