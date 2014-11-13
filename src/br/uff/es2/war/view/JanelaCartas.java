package br.uff.es2.war.view;

import br.uff.es2.war.WindowManager;
import br.uff.es2.war.model.Carta;
import javafx.application.Application;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;

public class JanelaCartas extends Application {

    private ObservableList<Carta> olCarta;
    private AnchorPane pane;
    private Button btnTrocar;
    private Button btnVoltar;
    private HBox horizontalButtonBox;
    private TableView<Carta> tbCarta;
    //private TableColumn<Carta, String> tcContinente;
    private TableColumn<Carta, String> tcPais;
    private TableColumn<Carta, String> tcForma;
    private Stage stage;
    private WindowManager windowController;
    private ImageView imgLogo;

    @Override
    public void start(Stage primaryStage) {
        initComponenets();

        this.stage = primaryStage;

        primaryStage.setTitle("Troca de Cartas");
        Scene scene = new Scene(pane, primaryStage.getWidth(), primaryStage.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle(" Troca de Cartas ");
        primaryStage.show();

        initListeners();
        initLayout();
    }

    private void initLayout() {
        tbCarta.setLayoutX((pane.getWidth() - tbCarta.getWidth()) / 2);
        tbCarta.setLayoutY(110);

        horizontalButtonBox.setLayoutX((pane.getWidth() - horizontalButtonBox.getWidth()) / 2);
        horizontalButtonBox.setLayoutY(pane.getHeight() - 40 - horizontalButtonBox.getHeight());
    }

    public ObservableList<Carta> getPlayerList() {
        return olCarta;
    }

    private void initListeners() {
        //Trocar cartas
        btnTrocar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ObservableList<Carta> temp = tbCarta.getSelectionModel().getSelectedItems();
                if(temp.size() == 3){
                    //---> Chamar o método de troca de carta aqui. <-----
                    
                    //trocaCarta(temp);
                }
                else{
                    JOptionPane.showConfirmDialog(null, "Voce tem que selecionar 3 cartas", "Alerta", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        });

        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });

        //Desmarcar linhas ja selecionadas
        tbCarta.setRowFactory(new Callback<TableView<Carta>, TableRow<Carta>>() {
            @Override
            public TableRow<Carta> call(TableView<Carta> tableView2) {
                final TableRow<Carta> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < tbCarta.getItems().size() && tbCarta.getSelectionModel().isSelected(index)) {
                            tbCarta.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });
    }

    private void initComponenets() {
        olCarta = FXCollections.observableArrayList();
        olCarta.add(new Carta("A","B","C"));
        olCarta.add(new Carta("Z","E","C"));
        olCarta.add(new Carta("X","F","C"));
        olCarta.add(new Carta("C","G","C"));

        pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.getStyleClass().add("pane");

        tbCarta = new TableView<>();
        tbCarta.getStyleClass().add("tbCarta");
        tbCarta.setMinWidth(500);
        tbCarta.setItems(olCarta);
        tbCarta.setEditable(true);
        tbCarta.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");

        btnTrocar = new Button("Trocar");
        btnTrocar.getStyleClass().add("button");

        Button mostrar = new Button("Mostrar");

        horizontalButtonBox = new HBox(15);
        horizontalButtonBox.getChildren().addAll(btnVoltar, btnTrocar);

//        tcContinente = new TableColumn<>("Continente");
        tcPais = new TableColumn<>("Pais");
        tcForma = new TableColumn("Forma");

//        tcContinente.setCellValueFactory(new PropertyValueFactory<Carta, String>("continente"));
//        tcContinente.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcPais.setCellValueFactory(new PropertyValueFactory<Carta, String>("pais"));
        tcPais.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcForma.setCellValueFactory(new PropertyValueFactory<Carta, String>("forma"));
        tcForma.setPrefWidth(tbCarta.getMinWidth() / 3);

        tbCarta.getColumns().addAll(tcPais, tcForma);
        
        pane.getChildren().addAll(tbCarta, horizontalButtonBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
