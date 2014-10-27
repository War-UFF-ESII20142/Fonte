/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.GameManager;
import br.uff.es2.war.JogoSalvo;
import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObserver;
import br.uff.es2.war.model.GameLoop;
import br.uff.es2.war.model.Pais;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import br.uff.es2.war.util.AdministradorDeArquivo;
import br.uff.es2.war.util.Tools;
import br.uff.es2.war.util.types;
import java.io.FileNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


/**
 *
 * @author AleGomes
 */
public class JanelaJogo extends Application implements iObserver
{
    private AnchorPane pane;
    private ImageView gameImage;
    private Stage stage;
    private Label info;
    private ObservableList<Player> olPlayers;//essa lista será incluida na tabela que executará para informar qual jogador está na jogada
    private GameManager gameController;
    private GameLoop gameLoop;
    private Button btnTerminarRodada;
    private Button btnAtaque;
    private HBox horizontalBox;
    private ArrayList<TerritorioTela> listaTerritorioTela;
    private DataManager dataManager;
    
    public JanelaJogo(DataManager data)
    {
        super();
        this.dataManager = data;
    }
    
    @Override
    public void start(Stage primaryStage) 
    {
        initComponents();
        
        stage = primaryStage;
        Scene scene = new Scene(pane);
        scene.getStylesheets().add("/stylesheet/JanelaJogo.css");
        primaryStage.setTitle("War UFF");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        initCircles();
        initListeners();
        initLayout();
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    
    public void setGameLoop( GameLoop game )
    {
        this.gameLoop = game;
    }
    
    public void setGameControler(GameManager manager)
    {
        this.gameController = manager;
    }
    
    public void setPlayerList(ObservableList<Player> players)
    {
        this.olPlayers = players;
    }
    
    public ObservableList<Player> getPlayerList()
    {
        return olPlayers;
    }
    
    private void initComponents()
    {
        olPlayers= FXCollections.observableArrayList();
        
        pane = new AnchorPane();
        pane.setPrefSize(1000, 600);
        pane.getStyleClass().add("pane");
        
        gameImage = new ImageView(new Image("resources/gamePlus1.png"));
        gameImage.getStyleClass().add("gameImage");
        gameImage.setFitHeight(575);
        gameImage.setFitWidth(564);
        
        info = new Label();
        
        btnTerminarRodada = new Button("Terminar");
        btnTerminarRodada.getStyleClass().add("button");
        
        btnAtaque = new Button("Ataque");
        btnAtaque.getStyleClass().add("button");
        
        horizontalBox = new HBox(15);
        horizontalBox.getChildren().addAll(btnAtaque,btnTerminarRodada);
        
        pane.getChildren().addAll(gameImage,info,horizontalBox);
        //constroiCirculos();
        
        gameLoop.addObserver(this);
        
    }
    
    private void initLayout()
    {
        gameImage.setLayoutX(10);
        gameImage.setLayoutY(20);
        
        horizontalBox.setLayoutX( pane.getWidth() - 10 - horizontalBox.getWidth() );
        horizontalBox.setLayoutY( pane.getHeight() - 10 - horizontalBox.getHeight() );
    }
    
    public Stage getStage()
    {
        return stage;
    }
    
    private void initCircles() 
    {
        listaTerritorioTela = new ArrayList<>();
        try {
            listaTerritorioTela = AdministradorDeArquivo.listaTerritorios(dataManager);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for(TerritorioTela t : listaTerritorioTela)
        {
            Circle circle = t.getCirculo();
            pane.getChildren().addAll(circle);
        }
        
        for(TerritorioTela t:listaTerritorioTela)
        {
            Label label = t.getLabel();
            pane.getChildren().add(label);
        }
        
    }
    
    @Override
    public void updateGameImage()
    {
        Player aux = gameLoop.getCurrentPlayer();
        stage.setTitle(aux.getNome());
        int value = (int)(Math.random()*21);
        System.out.println(value);
        Label temp = listaTerritorioTela.get(value).getLabel();
        temp.setText(Integer.toString(Integer.parseInt(temp.getText()) + 1));
        listaTerritorioTela.get(value).setLabel(temp);
    }
    
    @Override
    public void updateCircles() 
    {
        for(TerritorioTela tl : listaTerritorioTela)
        {
            System.out.println(tl.getPais().getNome());
        }
    }
    
    
    private void initListeners() {
        gameImage.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) 
            {
                String temp = "";
                double r = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getRed();
                double g = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getGreen();
                double b = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getBlue();
                
                int red =(int) (r*255);
                int green = (int) (g*255);
                int blue = (int) (b*255);
                
                if(red >= 250)
                {
                    if(green >= 250)
                    {
                        temp = inValonguinho(red);
                        gameImage.setCursor(Cursor.HAND);
                    }else
                    {
                        temp = inGragoata(red);
                        gameImage.setCursor(Cursor.HAND);
                    }
                }
                else if(green >= 250)
                {
                    temp = inUnidadeIsol(green);
                    gameImage.setCursor(Cursor.HAND);
                }
                else if(blue >= 250)
                {
                    temp = inPraiaVerm(blue);
                    gameImage.setCursor(Cursor.HAND);
                }else
                {
                    gameImage.setCursor(Cursor.DEFAULT);
                }
                info.setText(temp);
            }
        });
        
        gameImage.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event) {
                String temp = "";
                double r = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getRed();
                double g = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getGreen();
                double b = gameImage.getImage().getPixelReader().getColor((int)event.getX(), (int)event.getY()).getBlue();
                
                int red =(int) (r*255);
                int green = (int) (g*255);
                int blue = (int) (b*255);
                
                if(red >= 250)
                {
                    if(green >= 250)
                    {
                        temp = inValonguinho(red);
                        gameImage.setCursor(Cursor.HAND);
                    }else
                    {
                        temp = inGragoata(red);
                        gameImage.setCursor(Cursor.HAND);
                    }
                }
                else if(green >= 250)
                {
                    temp = inUnidadeIsol(green);
                    gameImage.setCursor(Cursor.HAND);
                }
                else if(blue >= 250)
                {
                    temp = inPraiaVerm(blue);
                    gameImage.setCursor(Cursor.HAND);
                }else
                {
                    gameImage.setCursor(Cursor.DEFAULT);
                }
                
                System.out.println(temp + " "+event.getSceneX()+" "+event.getSceneY());
            }
            
        });
        
        btnTerminarRodada.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gameController.roundTerminou();
            }
        });
    }
    
    private String inValonguinho(int red)
    {
        String pais = "";
        switch(red)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
            case 250:
                pais = "pais 6";
                break;
        }
        
        Pais pPais = dataManager.getPais(pais,types.sVALONGUINHO);
        
        if(pPais.getNome() != null && pPais.getContinente().getNome() != null)
        {
            return ("Está no: " + pPais.getNome()+" "+pPais.getContinente().getNome());
        }
        return "";
    }
    
    private String inGragoata(int red)
    {
        String pais = "";
        switch(red)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        Pais pPais = dataManager.getPais(pais,types.sGRAGOATA);
        
        if(pPais.getNome() != null && pPais.getContinente().getNome() != null)
        {
            return ("Está no: " + pPais.getNome()+" "+pPais.getContinente().getNome());
        }
        return "";
    }
    
    private String inPraiaVerm(int blue)
    {
        String pais = "";
        switch(blue)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        
        Pais pPais = dataManager.getPais(pais,types.sPRAIAVERMELHA);
        
        if(pPais.getNome() != null && pPais.getContinente().getNome() != null)
        {
            return ("Está no: " + pPais.getNome()+" "+pPais.getContinente().getNome());
        }
        return "";
    }
    
    private String inUnidadeIsol(int green)
    {
        String pais = "";
        switch(green)
        {
            case 255:
                pais = "pais 1";
                break;
            case 254:
                pais = "pais 2";
                break;
            case 253:
                pais = "pais 3";
                break;
            case 252:
                pais = "pais 4";
                break;
            case 251:
                pais = "pais 5";
                break;
        }
        
        Pais pPais = dataManager.getPais(pais,types.sUNIDADEISOLADAS);

        
        if(pPais.getNome() != null && pPais.getContinente().getNome() != null)
        {
            return ("Está no: " + pPais.getNome()+" "+pPais.getContinente().getNome());
        }
        return "";
    }
    
}
