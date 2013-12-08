package view.stages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.Ataxx;

public class BoardConfigurationStage extends AbstractStage {

    private Button btn;
    protected static AbstractStage instance;
    private ComboBox<Integer> sizeComboBox;
    private ComboBox<Integer> boardComboBox;

    protected BoardConfigurationStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Configuration du plateau de jeu");
    }

    @Override
    protected void createView() {
        btn = new Button("Modifier la configuration");
        ObservableList<Integer> sizeOptions =
                FXCollections.observableArrayList(
                        7,
                        8,
                        9,
                        10
                );
        sizeComboBox = new ComboBox<>(sizeOptions);

        //TODO SET enums
        ObservableList<Integer> boardOptions =
                FXCollections.observableArrayList(
                        7,
                        8,
                        9,
                        10
                );
        boardComboBox = new ComboBox<>(boardOptions);
    }

    @Override
    protected void placeComponents() {
        GridPane  grid = ((GridPane)scene.getRoot());
        //Title
        Text sceneTitle = new Text("Plateau");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        //Button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        Label boardSize = new Label("Taille du plateau :");
        grid.add(boardSize, 0, 1);

        grid.add(sizeComboBox, 1, 1);

        Label boardConfig = new Label("Type de plateau :");
        grid.add(boardConfig, 0, 2);

        grid.add(boardComboBox, 1, 2);
    }

    @Override
    protected void createController() {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                parent.setBoardSize(sizeComboBox.getValue());
                //TODO board
                parent.reset();
                close();
            }
        });
    }

    @Override
    public void render() {
        sizeComboBox.setValue(parent.getBoardSize());
        boardComboBox.setValue(parent.getBoardSize());
        show();
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new BoardConfigurationStage(parent);
        }
        return instance;
    }
}
