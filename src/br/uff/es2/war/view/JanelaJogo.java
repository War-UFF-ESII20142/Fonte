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
import javafx.scene.control.ComboBox;
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
    private Button btnfinalizaDistribuicao;
    private Button btnMostrarCartas; //A
    private Button btnMostrarObjetivo; //A
    private HBox horizontalBox;
    private HBox ObjetivoECartasBox; //A
    private ArrayList<TerritorioTela> listaTerritorioTela;
    private DataManager dataManager;
    private String paisAtacante, paisAtacado;
    private boolean auxiliarEscolha = true;
    private int qtdExercitoAtaque;
    private ComboBox cBox;
    private Stage pStage = new Stage();
    
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
        qtdExercitoAtaque = 0;
        cBox = new ComboBox();
        
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
        
        btnfinalizaDistribuicao = new Button("Finaliza Distruibuição");
        btnfinalizaDistribuicao.getStyleClass().add("button");
        
        btnMostrarCartas = new Button("Mostrar cartas");
        btnMostrarCartas.getStyleClass().add("button");
        
        btnMostrarObjetivo = new Button("Mostrar objetivos");
        btnMostrarObjetivo.getStyleClass().add("button");
                
        ObjetivoECartasBox = new HBox(10);
        
        ObjetivoECartasBox.getChildren().addAll(btnMostrarCartas, btnMostrarObjetivo);
        
        horizontalBox = new HBox(15);
        horizontalBox.getChildren().addAll(btnAtaque,btnTerminarRodada,btnfinalizaDistribuicao);
        
        //pane.getChildren().addAll(gameImage,info,horizontalBox);
        pane.getChildren().addAll(gameImage,info,horizontalBox,ObjetivoECartasBox);
        //constroiCirculos();
        
        gameLoop.addObserver(this);
        
    }
    
    private void initLayout()
    {
        gameImage.setLayoutX(10);
        gameImage.setLayoutY(20);
        
        horizontalBox.setLayoutX( pane.getWidth() - 10 - horizontalBox.getWidth() );
        horizontalBox.setLayoutY( pane.getHeight() - 10 - horizontalBox.getHeight() );
        
        ObjetivoECartasBox.setLayoutX( pane.getWidth() - 30 - ObjetivoECartasBox.getWidth() );
        ObjetivoECartasBox.setLayoutY( pane.getHeight() - 500 - ObjetivoECartasBox.getHeight() );
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
        pane.setStyle("-fx-background-color:"+Tools.convertCorToColor(aux.getCor()));
        updateCircles();
        /*int value = (int)(Math.random()*21);
        System.out.println(value);
        Label temp = listaTerritorioTela.get(value).getLabel();
        temp.setText(Integer.toString(Integer.parseInt(temp.getText()) + 1));
        listaTerritorioTela.get(value).setLabel(temp);*/
    }
    
    @Override
    public void updateCircles() 
    {
        for(Player pl : gameLoop.getPlayers())
        {
            for(Pais p : pl.getMeusPaises())
            {
                for(TerritorioTela tl : listaTerritorioTela)
                {
                    if(tl.getNome().equals(p.getNome()))
                    {
                        tl.setPais(p);
                        tl.getCirculo().setStyle("-fx-fill:"+Tools.convertCorToColor(p.getDono().getCor()));
                        tl.getLabel().setText(Integer.toString(p.getNumeroDeTroopas()));
                    }
                }
            }
        }
    }
    
    
    private void initListeners() {
        gameImage.setOnMouseMoved(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event) 
            {
                Pais temp = new Pais("", "", null);
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
                info.setText(temp.getNome());
            }
        });
        
        gameImage.setOnMouseClicked(new EventHandler<MouseEvent>()
        {

            @Override
            public void handle(MouseEvent event) {
                Pais temp = new Pais("", "", null);
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
                
                //System.out.println(temp + " "+event.getSceneX()+" "+event.getSceneY());
                
                gameController.fazCoisa(temp);
                
            }
            
        });
        
        btnTerminarRodada.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gameController.roundTerminou();
            }
        });
        
        btnAtaque.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event){
                gameController.attack();
            }
        });
        
        btnfinalizaDistribuicao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gameController.terminaDistruibuicao();
            }
        });
        
        btnMostrarCartas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Abrir janela e mostrar cartas em posse do atual jogador.
            }
        });
        
        btnMostrarObjetivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Abrir janela com o objetivo do atual jogador
                //gameController.mostrarObjetivo();
                JanelaObjetivo obj = new JanelaObjetivo();
                try {
                    obj.start(pStage);
                } catch (Exception ex) {
                    Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private Pais inValonguinho(int red)
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
            return pPais;
        }
        return new Pais("", "", null);
    }
    
    private Pais inGragoata(int red)
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
            return pPais;
        }
        return new Pais("", "", null);
    }
    
    private Pais inPraiaVerm(int blue)
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
            return pPais;
        }
        return new Pais("", "", null);
    }
    
    private Pais inUnidadeIsol(int green)
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
            return pPais;
        }
        return new Pais("", "", null);
    }
    
    public String paisAtacante(){
        return this.paisAtacante;
    }
    
    
    public String paisAtacado(){
        return this.paisAtacado;
    }
    
    public int getQtdExercito()
    {
        AnchorPane acPane = new AnchorPane();
        acPane.setPrefSize(200, 200);
        acPane.getStyleClass().add("pane");
        
        
        int numeroTropas = 3;
        
        for(int i = 1;i <= numeroTropas;i++)
        {
            cBox.getItems().add(Integer.toString(i));
        }
        
        Label lbNumeroTropas = new Label();
        lbNumeroTropas.setText("Selecione o numero de\ntropas para ataque");
        
        Button btnOk = new Button("ok");
        
        acPane.getChildren().addAll(cBox,lbNumeroTropas,btnOk);
        
        Scene scene = new Scene(acPane);
        
        pStage.setScene(scene);
        pStage.show();
        
        cBox.setLayoutX( (acPane.getWidth() - cBox.getWidth())/2 );
        cBox.setLayoutY((acPane.getHeight() - cBox.getHeight())/2);
        
        lbNumeroTropas.setLayoutX( (acPane.getWidth() - lbNumeroTropas.getWidth())/2 );
        lbNumeroTropas.setLayoutY( 50 );
        
        btnOk.setLayoutX( (acPane.getWidth() - btnOk.getWidth())/2 );
        btnOk.setLayoutY(150);
        
        btnOk.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) 
            {
                qtdExercitoAtaque = Integer.parseInt(cBox.getValue().toString());
                pStage.close();
            }
        });
        return qtdExercitoAtaque;
    }
    
}
