package view;

import controller.AtaxxMouseDraggedHandler;
import controller.AtaxxMousePressedHandler;
import controller.AtaxxMouseReleasedHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.AtaxxModel;
import model.PieceModel;
import model.ai.Algorithm;
import model.ai.MiniMax;
import view.stages.AbstractStage;
import view.stages.BoardConfigurationStage;
import view.stages.GameConfigurationStage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ataxx extends Application {

    private static final int BOARD_DEFAULT_SIZE = 7;

    private AtaxxModel model;
    private List<PieceView> pieceViews;
    private List<TileView> tileViews;
    private Scene scene;
    private AnchorPane pane;
    private Label clock;
    private int boardSize;
    private int startingPieces;
    private boolean gameVSComputer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ataxx");
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(700);
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);
        scene = new Scene(new VBox());

        createModel();
        createEventHandlers();
        createAndInstallMenuBar();
        createView();
        placeComponents();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
            AbstractStage stage = GameConfigurationStage.getInstance(Ataxx.this);
            if (stage != null) {
                stage.close();
            }
            stage = BoardConfigurationStage.getInstance(Ataxx.this);
            if (stage != null) {
                stage.close();
            }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void placeComponents() {
        BorderPane borderPane = new BorderPane();
        borderPane.setRight(clock);
        borderPane.setCenter(pane);
        ScrollPane sp = new ScrollPane();
        sp.setContent(borderPane);
        VBox.setVgrow(sp, Priority.ALWAYS);
        BorderPane.setMargin(pane, new Insets(0, 10, 0, 10));
        VBox.setMargin(sp, new Insets(10, 0, 10, 0));
        sp.setStyle("-fx-box-border: transparent; -fx-border-width: 0; -fx-background-color: white;");
        borderPane.setStyle("-fx-box-border: transparent; -fx-background-color: white;");
        pane.setStyle("-fx-box-border: transparent; -fx-background-color: white;");
        ((VBox) (scene.getRoot())).getChildren().addAll(sp);
    }

    private void createModel() {
        startingPieces = AtaxxModel.TWO_TOKENS;
        boardSize = BOARD_DEFAULT_SIZE;
        gameVSComputer = true;
        model = AtaxxModel.getInstance();
        Algorithm o = new MiniMax(3);
        model.setAlgorithm(o);
        model.generate(boardSize, startingPieces, gameVSComputer);
    }

    private void createView() {
        pieceViews = new ArrayList<>();
        tileViews = new ArrayList<>();
        clock = new Label();
        final DateFormat format = DateFormat.getInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final Calendar cal = Calendar.getInstance();
                clock.setText(format.format(cal.getTime()));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        pane = new AnchorPane();
        pane.setMaxWidth(600);
        pane.setMaxHeight(600);
        pane.setPrefSize(600, 600);
        initBoard(pane, boardSize);
    }

    private void initBoard(Pane pane, int size) {
        NumberBinding gridSize = Bindings.min(
                pane.widthProperty().subtract(20),
                pane.heightProperty().subtract(20));

        for (int i = 0; i < size; i++) {
            Text text = new Text(""+i);
            text.prefHeight(20);
            text.prefWidth(20);
            text.xProperty().bind(gridSize.multiply(i)
                    .divide(size).add(20).add(gridSize.divide(size).divide(2)));
            text.setY(15);
            pane.getChildren().add(text);
        }


        for (int i = 0; i < size; i++) {
            Text text = new Text(""+i);
            text.prefHeight(20);
            text.prefWidth(20);
            text.setX(0);
            text.yProperty().bind(gridSize.multiply(i)
                    .divide(size).add(20).add(gridSize.divide(size).divide(2)));
            pane.getChildren().add(text);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileView tileView = new TileView(model.get(i, j));
                tileView.xProperty().bind(gridSize.multiply(j)
                        .divide(size).add(20));
                tileView.yProperty().bind(gridSize.multiply(i)
                        .divide(size).add(20));
                tileView.widthProperty().bind(tileView.heightProperty());
                tileView.heightProperty().bind(gridSize.divide(size));

                PieceModel pieceModel = model.get(i,j).getPieceModel();
                if (pieceModel != null) {
                    pieceViews.add(makeAtaxxPiece(pieceModel, tileView));
                }
                tileViews.add(tileView);
                pane.getChildren().add(tileView);
            }
        }
        for (Circle c : pieceViews) {
            pane.getChildren().add(c);
        }
    }

    private void createAndInstallMenuBar() {
        MenuBar menuBar = new MenuBar();
        for (MenuEnum m : MenuEnum.values()) {
            Menu menu = new Menu(m.getName());

            List<ItemEnum> items = MenuEnum.getItems(m);
            for (ItemEnum i : items) {
                if (i == null) {
                    menu.getItems().add(new SeparatorMenuItem());
                } else {
                    MenuItem menuItem = new MenuItem(i.getName());
                    menuItem.setOnAction(i.getEvent());
                    menu.getItems().add(menuItem);
                }
            }
            menuBar.getMenus().add(menu);
        }
        ((VBox) scene.getRoot()).getChildren().add(menuBar);
    }

    public void reset() {
        pieceViews.clear();
        tileViews.clear();
        pane.getChildren().clear();
        model.generate(boardSize, startingPieces, gameVSComputer);
        initBoard(pane, boardSize);
    }

    private void createEventHandlers() {
        ItemEnum.NEW_GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reset();
            }
        });
        ItemEnum.CLOSE.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        ItemEnum.GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AbstractStage stage = GameConfigurationStage.getInstance(Ataxx.this);
                stage.render();
            }
        });
        ItemEnum.BOARD.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AbstractStage stage = BoardConfigurationStage.getInstance(Ataxx.this);
                stage.render();
            }
        });
        ItemEnum.HELP.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    private PieceView makeAtaxxPiece(PieceModel p, TileView t) {
        PieceView pieceView = new PieceView(p, t);
        pieceView.setOnMousePressed(new AtaxxMousePressedHandler(model, this));
        pieceView.setOnMouseDragged(new AtaxxMouseDraggedHandler(model, this));
        pieceView.setOnMouseReleased(new AtaxxMouseReleasedHandler(model, this));
        return pieceView;
    }

    public List<TileView> getTileViews() {
        return tileViews;
    }

    public double getSceneWidth() {
        return scene.getWidth();
    }

    public double getSceneHeight() {
        return scene.getHeight();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public boolean isGameVSComputer() {
        return gameVSComputer;
    }

    public void setGameVSComputer(boolean gameVSComputer) {
        this.gameVSComputer = gameVSComputer;
    }

    public void refresh() {
        for (PieceView p : pieceViews) {
            pane.getChildren().remove(p);
        }
        pieceViews.clear();
        for (TileView r : tileViews) {
            PieceModel pieceModel = r.getModel().getPieceModel();
            if (pieceModel != null) {
                PieceView pieceView = makeAtaxxPiece(pieceModel, r);
                pieceViews.add(pieceView);
                pane.getChildren().add(pieceView);
            }
        }
    }
}
