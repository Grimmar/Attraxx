package view;

import controller.AlgorithmChangeListener;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.*;
import view.component.ItemEnum;
import view.component.MenuEnum;
import view.component.PieceView;
import view.component.TileView;
import view.stages.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ataxx extends Application {

    private AtaxxModel model;
    private AtaxxConfiguration configuration;
    private List<PieceView> pieceViews;
    private List<TileView> tileViews;
    private Scene scene;
    private AnchorPane pane;
    private Label clock;
    private Label blueTokensLabel;
    private Label redTokensLabel;
    private Label numberOfPlayLabel;
    private Date currentTime;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ataxx");
        primaryStage.setMinWidth(850);
        primaryStage.setMinHeight(700);
        primaryStage.setWidth(850);
        primaryStage.setHeight(700);
        scene = new Scene(new VBox());

        createModel();
        createEventHandlers();
        createAndInstallMenuBar();
        createView();
        placeComponents();
        createController(primaryStage);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void createModel() {
        configuration = new AtaxxConfiguration();
        model = AtaxxModel.getInstance();;
        model.generate(configuration);
        currentTime = new Date();
    }

    private void createView() {
        pieceViews = new ArrayList<>();
        tileViews = new ArrayList<>();
        clock = new Label();
        clock.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        blueTokensLabel = new Label(model.blueTokensProperty().get() + "");
        blueTokensLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        redTokensLabel = new Label(model.redTokensProperty().get() + "");
        redTokensLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        numberOfPlayLabel = new Label(model.numberOfPlayProperty().get() + "");
        numberOfPlayLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
    }

    private void placeComponents() {
        pane = new AnchorPane(); {
            pane.setMaxWidth(600);
            pane.setMaxHeight(600);
            pane.setPrefSize(600, 600);
            initBoardGame(pane, configuration.getBoardSize());
            BorderPane.setMargin(pane, new Insets(0, 10, 0, 10));
            pane.setStyle("-fx-box-border: transparent; " +
                    "-fx-background-color: white;");

            ScrollPane sp = new ScrollPane(); {
                VBox.setVgrow(sp, Priority.ALWAYS);
                VBox.setMargin(sp, new Insets(10, 0, 10, 0));
                sp.setStyle("-fx-box-border: transparent; -fx-border-width: 0;" +
                        " -fx-background-color: white;");

                BorderPane p = new BorderPane(); {
                    p.setStyle("-fx-box-border: transparent; " +
                            "-fx-background-color: white;");

                    GridPane q = new GridPane(); {
                        Label l = new Label("Temps écoulé");
                        l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                        q.add(l, 0, 3);
                        q.add(clock, 1, 3);
                        l = new Label("Jetons bleus");
                        l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                        q.add(l, 0, 5);
                        q.add(blueTokensLabel, 1, 5);
                        l = new Label("Jetons rouges");
                        l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                        q.add(l, 0, 6);
                        q.add(redTokensLabel, 1, 6);
                        l = new Label("Nombre de coups joués");
                        l.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
                        q.add(l, 0, 7);
                        q.add(numberOfPlayLabel, 1, 7);
                    }
                    p.setCenter(pane);
                    p.setRight(q);
                }
                sp.setContent(p);

            }
            ((VBox) (scene.getRoot())).getChildren().addAll(sp);
        }
    }

    private void createEventHandlers() {
        ItemEnum.NEW_GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetGame();
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
                AbstractStage stage = HelpStage.getInstance(Ataxx.this);
                stage.render();
            }
        });
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

    private void createController(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
                stage = DialogStage.getInstance(Ataxx.this);
                if (stage != null) {
                    stage.close();
                }
                stage = HelpStage.getInstance(Ataxx.this);
                if (stage != null) {
                    stage.close();
                }
            }
        });

        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentTime = Calendar.getInstance().getTime();
                long time = currentTime.getTime() - model.getStartTime().getTime();
                clock.setText(longToDateStringFormat(time));
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        model.algorithmStateProperty().addListener(new AlgorithmChangeListener(model, this));
    }

    private String longToDateStringFormat(long timeinMillis) {
        StringBuilder sb= new StringBuilder();
        timeinMillis = timeinMillis / 1000;
        long hours = timeinMillis / 3600;
        timeinMillis = timeinMillis % 3600;
        long minutes = timeinMillis / 60;
        timeinMillis = timeinMillis % 60;
        sb.append(hours).append("h:").append(minutes).append("m:").append(timeinMillis).append("s");
        return sb.toString();
    }

    private void initBoardGame(Pane pane, int size) {
        NumberBinding gridSize = Bindings.min(
                pane.widthProperty().subtract(20),
                pane.heightProperty().subtract(20));

        for (int i = 0; i < size; i++) {
            Text text = new Text("" + i);
            text.prefHeight(20);
            text.prefWidth(20);
            text.xProperty().bind(gridSize.multiply(i)
                    .divide(size).add(20).add(gridSize.divide(size).divide(2)));
            text.setY(15);
            pane.getChildren().add(text);
        }


        for (int i = 0; i < size; i++) {
            Text text = new Text("" + i);
            text.prefHeight(20);
            text.prefWidth(20);
            text.setX(0);
            text.yProperty().bind(gridSize.multiply(i)
                    .divide(size).add(20).add(gridSize.divide(size).divide(2)));
            pane.getChildren().add(text);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileModel tile = model.get(j, i);
                TileView tileView = new TileView(tile);
                tileView.xProperty().bind(gridSize.multiply(j)
                        .divide(size).add(20));
                tileView.yProperty().bind(gridSize.multiply(i)
                        .divide(size).add(20));
                tileView.widthProperty().bind(tileView.heightProperty());
                tileView.heightProperty().bind(gridSize.divide(size));

                PieceModel pieceModel = tile.getPieceModel();
                if (pieceModel != null) {
                    pieceViews.add(createAndConfigureAPieceView(pieceModel, tileView));
                }
                tileViews.add(tileView);
                pane.getChildren().add(tileView);
            }
        }
        for (Circle c : pieceViews) {
            pane.getChildren().add(c);
        }
    }

    private PieceView createAndConfigureAPieceView(PieceModel p, TileView t) {
        PieceView pieceView = new PieceView(p, t);
        pieceView.setOnMousePressed(new AtaxxMousePressedHandler(model, this));
        pieceView.setOnMouseDragged(new AtaxxMouseDraggedHandler(model, this));
        pieceView.setOnMouseReleased(new AtaxxMouseReleasedHandler(model, this));
        return pieceView;
    }

    public void resetGame() {
        pieceViews.clear();
        tileViews.clear();
        pane.getChildren().clear();
        model.generate(configuration);
        initBoardGame(pane, configuration.getBoardSize());
        model.algorithmStateProperty().addListener(new AlgorithmChangeListener(model, this));
        currentTime = new Date();
        blueTokensLabel.setText(model.blueTokensProperty().get() + "");
        redTokensLabel.setText(model.redTokensProperty().get() + "");
        numberOfPlayLabel.setText(model.numberOfPlayProperty().get() + "");
    }

    public void refreshView() {
        for (PieceView p : pieceViews) {
            pane.getChildren().remove(p);
        }
        pieceViews.clear();
        for (TileView r : tileViews) {
            PieceModel pieceModel = r.getModel().getPieceModel();
            if (pieceModel != null) {
                PieceView pieceView = createAndConfigureAPieceView(pieceModel, r);
                pieceViews.add(pieceView);
                pane.getChildren().add(pieceView);
            }
        }
        blueTokensLabel.setText(model.blueTokensProperty().get() + "");
        redTokensLabel.setText(model.redTokensProperty().get() + "");
        numberOfPlayLabel.setText(model.numberOfPlayProperty().get() + "");
    }

    public void gameOverAlert() {
        AbstractStage stage = DialogStage.getInstance(Ataxx.this);
        stage.render();
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

    public Owner getWinner() {
        return model.getWinner();
    }

    public String getSpentTime() {
        return longToDateStringFormat(currentTime.getTime()
                - model.getStartTime().getTime());
    }

    public AtaxxConfiguration getConfiguration() {
        return configuration;
    }
}
