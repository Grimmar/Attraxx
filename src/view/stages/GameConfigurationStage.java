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
import model.ai.AlgorithmType;
import model.ai.DifficultyType;
import view.Ataxx;
import view.component.NameableCallback;

public class GameConfigurationStage extends AbstractStage {
    protected static AbstractStage instance;
    private Button btn;
    private ToggleGroup group;
    private RadioButton singlePlayer;
    private RadioButton twoPlayers;
    private ComboBox<DifficultyType> difficultyComboBox;
    private ComboBox<AlgorithmType> algorithmComboBox;
    private Label difficulty;
    private Label algorithm;
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
        ObservableList<DifficultyType> difficultyEnumOptions =
                FXCollections.observableArrayList(DifficultyType.values());
        difficultyComboBox = new ComboBox<>();
        NameableCallback<DifficultyType> difficultyCellFactory = new NameableCallback<>();
        difficultyComboBox.setItems(difficultyEnumOptions);
        difficultyComboBox.setButtonCell(difficultyCellFactory.call(null));
        difficultyComboBox.setCellFactory(difficultyCellFactory);

        ObservableList<AlgorithmType> algorithmOptions =
                FXCollections.observableArrayList(AlgorithmType.values());
        algorithmComboBox = new ComboBox<>();
        NameableCallback<AlgorithmType> algorithmCellFactory = new NameableCallback<>();
        algorithmComboBox.setItems(algorithmOptions);
        algorithmComboBox.setButtonCell(algorithmCellFactory.call(null));
        algorithmComboBox.setCellFactory(algorithmCellFactory);
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
                parent.getConfiguration().setGameVSComputer(vsIa);
                parent.getConfiguration().setDifficulty(difficultyComboBox.getValue());
                parent.getConfiguration().setAlgorithm(algorithmComboBox.getValue());
                parent.resetGame();
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
        difficultyComboBox.setValue(parent.getConfiguration().getDifficulty());
        algorithmComboBox.setValue(parent.getConfiguration().getAlgorithm());
        if(parent.getConfiguration().isGameVSComputer()) {
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
