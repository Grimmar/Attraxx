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
import model.PieceModel;

import java.util.ArrayList;
import java.util.List;

public class Ataxx extends Application {

    private static final int BOARD_SIZE = 7;
    private int numberOfLock;
    private int startingPieces;
    private AtaxxModel model;
    private List<PieceView> pieceViews;
    private List<TileView> tileViews;
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
        pieceViews = new ArrayList<>();
        tileViews = new ArrayList<>();

        pane = new AnchorPane();
        initBoard(BOARD_SIZE);

        ((VBox) scene.getRoot()).getChildren().add(pane);
    }

    private void initBoard(int size) {
        NumberBinding rectsAreaSize = Bindings.min(scene.heightProperty(),
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
                int size = BOARD_SIZE;
                model.generate(size, startingPieces, numberOfLock);
                initBoard(size);
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
        pieceView.radiusProperty().bind(t.heightProperty().divide(2).subtract(10));
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
