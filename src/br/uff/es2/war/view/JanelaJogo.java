/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.GameManager;
import br.uff.es2.war.JogoSalvo;
import br.uff.es2.war.WindowManager;
import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObserver;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.model.GameLoop;
import br.uff.es2.war.model.Pais;
import br.uff.es2.war.model.HumanPlayer; //A
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
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;


/**
 *
 * @author AleGomes
 */
public class JanelaJogo extends Application implements iObserver,iWindow
{
    private AnchorPane pane;
    private ImageView gameImage;
    private Stage stage;
    private Label info;
    private Label infoQtdSoldados;
    private ObservableList<Player> olPlayers;//essa lista será incluida na tabela que executará para informar qual jogador está na jogada
    private GameManager gameController;
    private GameLoop gameLoop;
    private Button btnTerminarRodada;
    private Button btnAtaque;
    private Button btnFinalizaDistribuicao;
    private Button btnMostrarCartas; //A
    private Button btnMostrarObjetivo; //A
    private HBox horizontalBox;
    private HBox ObjetivoECartasBox; //A
    private HBox hLabels;
    private ArrayList<TerritorioTela> listaTerritorioTela;
    private DataManager dataManager;
    private String paisAtacante, paisAtacado;
    private boolean auxiliarEscolha = true;
    private int qtdExercitoAtaque;
    private ComboBox cBox;
    private Stage pStage = new Stage();
    private WindowManager windowController;
    
    public JanelaJogo(DataManager data,WindowManager controller)
    {
        super();
        this.dataManager = data;
        this.windowController = controller;
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
        infoQtdSoldados = new Label();
        hLabels = new HBox(10);
        hLabels.getChildren().addAll(info,infoQtdSoldados);
        
        btnTerminarRodada = new Button("Terminar");
        btnTerminarRodada.getStyleClass().add("button");
        btnTerminarRodada.setVisible(false);
        
        btnAtaque = new Button("Ataque");
        btnAtaque.getStyleClass().add("button");
        btnAtaque.setVisible(false);
        
        btnFinalizaDistribuicao = new Button("Finaliza Distruibuição");
        btnFinalizaDistribuicao.getStyleClass().add("button");
        btnFinalizaDistribuicao.setVisible(false);
        
        btnMostrarCartas = new Button("Mostrar cartas");
        btnMostrarCartas.getStyleClass().add("button");
        
        btnMostrarObjetivo = new Button("Mostrar objetivo");
        btnMostrarObjetivo.getStyleClass().add("button");
                
        ObjetivoECartasBox = new HBox(10);
        
        ObjetivoECartasBox.getChildren().addAll(btnMostrarCartas, btnMostrarObjetivo);
        
        horizontalBox = new HBox(15);
        horizontalBox.getChildren().addAll(btnAtaque,btnTerminarRodada);
        
        //pane.getChildren().addAll(gameImage,info,horizontalBox);
        pane.getChildren().addAll(gameImage,hLabels,horizontalBox,ObjetivoECartasBox);
        //constroiCirculos();
        
        gameLoop.addObserver(this);
        
    }
    
    private void initLayout()
    {
        gameImage.setLayoutX(10);
        gameImage.setLayoutY(20);
        
        horizontalBox.setLayoutX( pane.getWidth() - 10 - horizontalBox.getWidth() );
        horizontalBox.setLayoutY( pane.getHeight() - 10 - horizontalBox.getHeight() );
        
        hLabels.setLayoutX(10);
        
        ObjetivoECartasBox.setLayoutX( pane.getWidth() - 30 - ObjetivoECartasBox.getWidth() );
        ObjetivoECartasBox.setLayoutY( 20 );
    }
    
    @Override
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
        infoQtdSoldados.setText("Você tem "+gameLoop.getQtdTropa()+" para alocar");
        //if( gameLoop.getQtdTropa() == 0 ) this.avisaAcabouTropas();
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
                btnAtaque.setVisible(false);
                btnAtaque.setText("Ataque");
                btnAtaque.setDisable(false);
                btnTerminarRodada.setVisible(false);
                infoQtdSoldados.setVisible(true);
            }
        });
        
        btnAtaque.setOnAction(new EventHandler<ActionEvent>(){
            
            @Override
            public void handle(ActionEvent event){
                if(!gameController.isInAttack())
                {
                    btnAtaque.setText("F ataq");
                }
                else
                {
                    btnAtaque.setText("Ataque");
                    btnAtaque.setDisable(true);
                }
                gameController.attack();
                infoQtdSoldados.setVisible(false);
            }
        });
        
        btnFinalizaDistribuicao.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                gameController.terminaDistruibuicao();
            }
        });
        
        btnMostrarCartas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Abrir janela e mostrar cartas em posse do atual jogador.
                JanelaCartas jCartas = new JanelaCartas();
                try {
                    jCartas.start(pStage);
                } catch (Exception ex) {
                    Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        btnMostrarObjetivo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Abrir janela com o objetivo do atual jogador
                String objetivo = gameController.mostrarObjetivo().getNome();
                JanelaObjetivo obj = new JanelaObjetivo(objetivo);
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
    
    public void getQtdExercito(Pais paisAtacante)
    {
        AnchorPane acPane = new AnchorPane();
        acPane.setPrefSize(200, 200);
        acPane.getStyleClass().add("pane");
        
        
        int numeroTropas ;
        Label lbNumeroTropas = new Label();
        if(gameController.isInAttack())
        {
            numeroTropas = ( (paisAtacante.getNumeroDeTroopas()-1)>3?3:paisAtacante.getNumeroDeTroopas()-1 );
            lbNumeroTropas.setText("Selecione o numero de\ntropas para ataque");
        }
        else
        {
            numeroTropas = paisAtacante.getNumeroDeTroopas() -1;
            lbNumeroTropas.setText("Selecione o numero de\ntropas para realocar");
        }
        cBox.getItems().clear();
        
        for(int i = 1;i <= numeroTropas;i++)
        {
            cBox.getItems().add(Integer.toString(i));
        }
        
        Button btnOk = new Button("Ok");
        Button btnCancel = new Button("cancel");
        
        HBox hbox = new HBox(10,btnOk,btnCancel);
        
        acPane.getChildren().addAll(cBox,lbNumeroTropas,hbox);
        
        Scene scene = new Scene(acPane);
        
        pStage.setScene(scene);
        pStage.show();
        
        cBox.setLayoutX( (acPane.getWidth() - cBox.getWidth())/2 );
        cBox.setLayoutY((acPane.getHeight() - cBox.getHeight())/2);
        
        lbNumeroTropas.setLayoutX( (acPane.getWidth() - lbNumeroTropas.getWidth())/2 );
        lbNumeroTropas.setLayoutY( 50 );
        
        
        hbox.setLayoutX( (acPane.getWidth() - hbox.getWidth())/2 );
        hbox.setLayoutY(150);
        
        btnOk.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) 
            {
                qtdExercitoAtaque = Integer.parseInt(cBox.getValue().toString());
                gameController.informaQtdSoldadosSelec(qtdExercitoAtaque);
                pStage.close();
            }
        });
        
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) 
            {
                gameController.setAtaque(true);
                pStage.close();
            }
        });
        
        pStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                gameController.setAtaque(true);
                pStage.close();
         
            }
        });
    }

    public void avisaAcabouTropas() 
    {
        btnAtaque.setVisible(true);
        btnTerminarRodada.setVisible(true);
        JOptionPane.showConfirmDialog(null, "Você não tem mais tropas para distribuir.\nCom isso, sua rodada de distribuição acabou", "Alerta", JOptionPane.OK_CANCEL_OPTION);
    }
    
    public void avisaTrocaDeCartas() 
    {
        //Caso se tenha 5 cartas é necessário fazer uma troca.
        JOptionPane.showConfirmDialog(null, "Você possui 5 cartas. Precisa trocá-las.", "Alerta", JOptionPane.OK_CANCEL_OPTION);
    }
    
    public void finalizaJogo()
    {
        try {
            this.windowController.mostraJanelaFimJogo(JanelaJogo.this,gameLoop.getCurrentPlayer());
        } catch (Exception ex) {
            Logger.getLogger(JanelaJogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
}
