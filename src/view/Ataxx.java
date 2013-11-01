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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.AtaxxModel;
import model.Piece;

import java.util.ArrayList;
import java.util.List;

public class Ataxx extends Application {

    private static final int BOARD_SIZE = 7;
    private int numberOfLock;
    private int startingPieces;
    private AtaxxModel model;
    private List<AtaxxPiece> pieces;
    private List<AtaxxTile> tiles;
    private AnchorPane pane;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Sample");
        scene = new Scene(new VBox(), 600, 800);
        scene.setFill(Color.OLDLACE);

        createModel();
        createEventHandlers();
        createAndInstallMenuBar(scene);
        createView(scene);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void createModel() {
        numberOfLock = 1;
        startingPieces = AtaxxModel.TWO_TOKENS;
        model = AtaxxModel.getInstance();
        model.generate(BOARD_SIZE, startingPieces, numberOfLock);
    }

    private void createView(Scene scene) {
        pieces = new ArrayList<>();
        tiles = new ArrayList<>();

        pane = new AnchorPane();
        initBoard(BOARD_SIZE);

        ((VBox) scene.getRoot()).getChildren().add(pane);
    }

    private void initBoard(int size) {
        NumberBinding rectsAreaSize = Bindings.min(scene.heightProperty(),
                scene.widthProperty());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                AtaxxTile r = new AtaxxTile(model.get(i, j));
                r.xProperty().bind(rectsAreaSize.multiply(i).divide(size));
                r.yProperty().bind(rectsAreaSize.multiply(j).divide(size));
                r.heightProperty().bind(rectsAreaSize.divide(size));
                r.widthProperty().bind(r.heightProperty());

                Piece piece = model.get(i, j).getPiece();
                if (piece != null) {
                    pieces.add(makeAtaxxPiece(piece, r));
                }
                tiles.add(r);
                pane.getChildren().add(r);
            }
        }
        for (Circle c : pieces) {
            pane.getChildren().add(c);
        }
    }

    private void createAndInstallMenuBar(Scene scene) {
        MenuBar menuBar = new MenuBar();
        for (MenuEnum m : MenuEnum.values()) {
            Menu menu = new Menu(m.getName());

            List<Item> items = MenuEnum.getItems(m);
            for (Item i : items) {
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
        Item.NEW_GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pieces.clear();
                tiles.clear();
                pane.getChildren().clear();
                int size = BOARD_SIZE;
                model.generate(size, startingPieces, numberOfLock);
                initBoard(size);
            }
        });
    }

    public AtaxxPiece makeAtaxxPiece(Piece p, AtaxxTile t) {
        AtaxxPiece piece = new AtaxxPiece(p, t);
        piece.radiusProperty().bind(t.heightProperty().divide(2).subtract(10));
        piece.setOnMousePressed(new AtaxxMousePressedHandler(model, this));
        piece.setOnMouseDragged(new AtaxxMouseDraggedHandler(model, this));
        piece.setOnMouseReleased(new AtaxxMouseReleasedHandler(model, this));
        return piece;
    }

    public List<AtaxxTile> getTiles() {
        return tiles;
    }

    public double getSceneWidth() {
        return scene.getWidth();
    }

    public double getSceneHeight() {
        return scene.getHeight();
    }

    public void clearPieces() {
        for (AtaxxPiece p : pieces) {
            pane.getChildren().remove(p);
        }
        pieces.clear();
    }

    public void addPiece(AtaxxPiece piece) {
        pieces.add(piece);
    }

    public AnchorPane getPane() {
        return pane;
    }
}
