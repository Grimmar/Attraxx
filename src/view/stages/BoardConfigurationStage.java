package view.stages;

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
import model.board.BoardType;
import view.Ataxx;
import view.component.NameableCallback;

public class BoardConfigurationStage extends AbstractStage {

    private Button btn;
    protected static AbstractStage instance;
    private ComboBox<Integer> sizeComboBox;
    private ComboBox<BoardType> boardComboBox;
    private ToggleGroup group;
    private RadioButton singleToken;
    private RadioButton twoTokens;

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

        ObservableList<BoardType> boardOptions =
                FXCollections.observableArrayList(BoardType.values());

        NameableCallback<BoardType> boardCellFactory = new NameableCallback<>();
        boardComboBox = new ComboBox<>();
        boardComboBox.setItems(boardOptions);
        boardComboBox.setButtonCell(boardCellFactory.call(null));
        boardComboBox.setCellFactory(boardCellFactory);

        group = new ToggleGroup();

        singleToken = new RadioButton("Une pièce");
        singleToken.setToggleGroup(group);

        twoTokens = new RadioButton("Deux pièces");
        twoTokens.setToggleGroup(group);
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
        grid.add(hbBtn, 1, 6);

        Label boardSize = new Label("Taille du plateau :");
        grid.add(boardSize, 0, 1);
        grid.add(sizeComboBox, 1, 1);

        Label boardConfig = new Label("Type de plateau :");
        grid.add(boardConfig, 0, 2);
        grid.add(boardComboBox, 1, 2);

        Label numberOfToken = new Label("Nombre de pièces :");
        grid.add(numberOfToken, 0, 3);
        grid.add(singleToken, 1, 3);
        grid.add(twoTokens, 1, 4);
    }

    @Override
    protected void createController() {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                parent.getConfiguration().setBoardSize(sizeComboBox.getValue());
                parent.getConfiguration().setBoardType(boardComboBox.getValue());
                parent.getConfiguration().setSingleToken(singleToken.isSelected());
                parent.reset();
                close();
            }
        });
    }

    @Override
    public void render() {
        sizeComboBox.setValue(parent.getConfiguration().getBoardSize());
        boardComboBox.setValue(parent.getConfiguration().getBoardType());
        if(parent.getConfiguration().isSingleToken()) {
            singleToken.setSelected(true);
        } else {
            twoTokens.setSelected(true);
        }
        show();
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new BoardConfigurationStage(parent);
        }
        return instance;
    }
}
