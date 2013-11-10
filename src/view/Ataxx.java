package view;

import controller.AtaxxMouseDraggedHandler;
import controller.AtaxxMousePressedHandler;
import controller.AtaxxMouseReleasedHandler;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.AtaxxModel;
import model.PieceModel;
import model.ai.Algorithm;
import model.ai.MiniMax;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ataxx extends Application {

    private static final int BOARD_DEFAULT_SIZE = 10;

    private AtaxxModel model;
    private List<PieceView> pieceViews;
    private List<TileView> tileViews;
    private Scene scene;
    private AnchorPane pane;
    private int boardSize;
    private int startingPieces;

    /*final Label clock = new Label();
     final DateFormat format = DateFormat.getInstance();
     final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
     @Override
     public void handle(ActionEvent event) {
     final Calendar cal = Calendar.getInstance();
     clock.setText(format.format(cal.getTime());
     }
     });
     timeline.setCycleCount(Animation.INDEFINITE);
     timeline.play();*/
    public static void main(String[] args) {

        int startingPieces = AtaxxModel.TWO_TOKENS;
        int boardSize = BOARD_DEFAULT_SIZE;
        AtaxxModel model = AtaxxModel.getInstance();
        model.generate(boardSize, startingPieces);
        Algorithm o = new MiniMax(4);
        o.buildTree(model);
        System.out.println(o.getRoot());

    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ataxx");
        scene = new Scene(new VBox(), 600, 620);

        createModel();
        createEventHandlers();
        createAndInstallMenuBar(scene);
        createView(scene);

        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void createModel() {
        startingPieces = AtaxxModel.TWO_TOKENS;
        boardSize = BOARD_DEFAULT_SIZE;
        model = AtaxxModel.getInstance();
        model.generate(boardSize, startingPieces);
    }

    private void createView(Scene scene) {
        pieceViews = new ArrayList<>();
        tileViews = new ArrayList<>();

        pane = new AnchorPane();
        initBoard(boardSize);

        ((VBox) scene.getRoot()).getChildren().add(pane);
    }

    private void initBoard(int size) {
        NumberBinding rectsAreaSize = Bindings.min(
                scene.heightProperty().subtract(20),
                scene.widthProperty());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                TileView r = new TileView(model.get(i, j));
                r.xProperty().bind(rectsAreaSize.multiply(i).divide(size));
                r.yProperty().bind(rectsAreaSize.multiply(j).divide(size));
                r.heightProperty().bind(rectsAreaSize.divide(size));
                r.widthProperty().bind(r.heightProperty());

                PieceModel pieceModel = model.get(i, j).getPieceModel();
                if (pieceModel != null) {
                    pieceViews.add(makeAtaxxPiece(pieceModel, r));
                }
                tileViews.add(r);
                pane.getChildren().add(r);
            }
        }
        for (Circle c : pieceViews) {
            pane.getChildren().add(c);
        }
    }

    private void createAndInstallMenuBar(Scene scene) {
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

    private void createEventHandlers() {
        ItemEnum.NEW_GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pieceViews.clear();
                tileViews.clear();
                pane.getChildren().clear();
                model.generate(boardSize, startingPieces);
                initBoard(boardSize);
            }
        });
        ItemEnum.CLOSE.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
    }

    public PieceView makeAtaxxPiece(PieceModel p, TileView t) {
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

    public void clearPieces() {
        for (PieceView p : pieceViews) {
            pane.getChildren().remove(p);
        }
        pieceViews.clear();
    }

    public void addPiece(PieceView pieceView) {
        pieceViews.add(pieceView);
    }

    public AnchorPane getPane() {
        return pane;
    }
}
