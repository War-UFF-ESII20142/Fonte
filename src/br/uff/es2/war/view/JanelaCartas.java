package br.uff.es2.war.view;

import br.uff.es2.war.WindowManager;
import br.uff.es2.war.model.Carta;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class JanelaCartas extends Application {

    private ObservableList<Carta> olCarta;
    private AnchorPane pane;
    private Button btnTrocar;
    private Button btnVoltar;
    private HBox horizontalButtonBox;
    private TableView<Carta> tbCarta;
    private TableColumn<Carta, String> tcContinente;
    private TableColumn<Carta, String> tcPais;
    private TableColumn<Carta, String> tcForma;
    private TableColumn<Carta, Boolean> tcEscolhida;
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
                trocarCartas();
                //Pegar as cartas marcadas
                //TrocarCartas();
                //Aumentar o numero de tropas para o jogador atual
            }
        });

        btnVoltar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                //windowController.startMainWindow(JanelaJogo.this);
            }
        });
    }

    private void initComponenets() {
        olCarta = FXCollections.observableArrayList();

        pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.getStyleClass().add("pane");

        tbCarta = new TableView<>();
        tbCarta.getStyleClass().add("tbCarta");
        tbCarta.setMinWidth(500);
        tbCarta.setItems(olCarta);
        tbCarta.setEditable(true);

        btnVoltar = new Button("Voltar");
        btnVoltar.getStyleClass().add("button");

        btnTrocar = new Button("Trocar");
        btnTrocar.getStyleClass().add("button");

        horizontalButtonBox = new HBox(15);
        horizontalButtonBox.getChildren().addAll(btnVoltar, btnTrocar);

        tcContinente = new TableColumn<>("Continente");
        tcPais = new TableColumn<>("Pais");
        tcForma = new TableColumn("Forma");
        tcEscolhida = new TableColumn<>("Escolhido");

        tcContinente.setCellValueFactory(new PropertyValueFactory<Carta, String>("continente"));
        tcContinente.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcPais.setCellValueFactory(new PropertyValueFactory<Carta, String>("pais"));
        tcPais.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcForma.setCellValueFactory(new PropertyValueFactory<Carta, String>("forma"));
        tcForma.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcEscolhida.setCellValueFactory(new PropertyValueFactory<Carta, Boolean>("escolhido"));
        tcEscolhida.setPrefWidth(tbCarta.getMinWidth() / 3);

        tcEscolhida.setCellFactory(
                new Callback<TableColumn<Carta, Boolean>, TableCell<Carta, Boolean>>() {
                    public TableCell<Carta, Boolean> call(TableColumn<Carta, Boolean> p) {
                        return new CheckBoxTableCell<Carta, Boolean>();
                    }
                });

        tbCarta.getColumns().addAll(tcEscolhida, tcContinente, tcPais, tcForma);

        //imgLogo = new ImageView(new Image("resources/war-uff.png"));
        //pane.getChildren().addAll(imgLogo,tbCarta,horizontalButtonBox);
        pane.getChildren().addAll(tbCarta, horizontalButtonBox);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public class CheckBoxTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;

        //private ObservableValue<T> ov;

        public CheckBoxTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
            }
        }
    }

    private void trocarCartas() {
        //Se uma linha da tabela esta marcada, add a lista
        ObservableList<Carta> data = tbCarta.getItems();
        ObservableList<Carta> cartasATrocar = FXCollections.observableArrayList();
        for (Carta item : data) {
            if (item.getEscolhida() == true) {
                cartasATrocar.add(item);
            }
        }
    }
}
