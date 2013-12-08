package view.stages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.Ataxx;

public class GameConfigurationStage extends AbstractStage {
    protected static AbstractStage instance;
    private Button btn;
    private RadioButton singlePlayer;
    private RadioButton twoPlayers;
    private ComboBox<Integer> difficultyComboBox;
    private ComboBox<Integer> algorithmComboBox;
    private Label difficulty;
    private Label algorithm;
    private ToggleGroup group;
    private HBox hbBtn;

    protected GameConfigurationStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Configuration du mode de jeu");
    }

    @Override
    protected void createView() {
        btn = new Button("Modifier le mode de jeu");
        group = new ToggleGroup();

        singlePlayer = new RadioButton("Un joueur");
        singlePlayer.setToggleGroup(group);

        twoPlayers = new RadioButton("Deux joueurs");
        twoPlayers.setToggleGroup(group);

        difficulty = new Label("Difficulté :");
        algorithm = new Label("Algorithme :");
        //TODO SET enums
        ObservableList<Integer> difficultyOptions =
                FXCollections.observableArrayList(
                        7,
                        8,
                        9,
                        10
                );
        difficultyComboBox = new ComboBox<>(difficultyOptions);

        ObservableList<Integer> algorithmOptions =
                FXCollections.observableArrayList(
                        7,
                        8,
                        9,
                        10
                );
        algorithmComboBox = new ComboBox<>(algorithmOptions);
    }

    @Override
    protected void placeComponents() {
        GridPane grid = ((GridPane)scene.getRoot());
        Text sceneTitle = new Text("Mode de jeu");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        //Button
        hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        //grid.add(hbBtn, 1, 6);

        Label boardSize = new Label("Nombre de joueurs :");
        grid.add(boardSize, 0, 1);
        grid.add(singlePlayer, 1, 1);
        grid.add(twoPlayers, 1, 2);

        grid.add(difficulty, 0, 3);
        grid.add(difficultyComboBox, 1, 3);
;
        grid.add(algorithm, 0, 4);
        grid.add(algorithmComboBox, 1, 4);
    }

    @Override
    protected void createController() {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                boolean vsIa = singlePlayer.isSelected() ? true : false;
                parent.setGameVSComputer(vsIa);
                //TODO difficulty
                //TODO algorithm
                parent.reset();
                close();
            }
        });

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle2) {
                if (group.getSelectedToggle() != null) {
                    boolean visible = singlePlayer.isSelected();
                    algorithm.setVisible(visible);
                    difficulty.setVisible(visible);
                    difficultyComboBox.setVisible(visible);
                    algorithmComboBox.setVisible(visible);
                    GridPane grid = ((GridPane)scene.getRoot());
                    if (visible) {
                        grid.getChildren().remove(hbBtn);
                        grid.add(hbBtn, 1, 6);
                    } else {
                        grid.getChildren().remove(hbBtn);
                        grid.add(hbBtn, 1, 4);
                    }
                }

            }
        });

    }

    @Override
    public void render() {
        difficultyComboBox.setValue(parent.getBoardSize());
        algorithmComboBox.setValue(parent.getBoardSize());
        if(parent.isGameVSComputer()) {
            singlePlayer.setSelected(true);
        } else {
            twoPlayers.setSelected(true);
        }
        show();
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new GameConfigurationStage(parent);
        }
        return instance;
    }
}
