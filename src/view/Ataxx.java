package view;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.AtaxxModel;
import model.Cell;
import model.Piece;

import java.util.ArrayList;
import java.util.List;

public class Ataxx extends Application {

    private static final int BOARD_SIZE = 7;
    private AtaxxModel model;
    private List<AtaxxPiece> pieces;
    private List<AtaxxRegion> rectangles;
    private AtaxxPiece dragged;
    private AtaxxRegion arrival;
    private AnchorPane pane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Sample");
        Scene scene = new Scene(new VBox(), 600, 800);
        scene.setFill(Color.OLDLACE);

        createModel();
        createEventHandlers();
        createAndInstallMenuBar(scene);
        createView(scene);
        createController();
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private void createModel() {
        model = AtaxxModel.getInstance();
        model.generate(BOARD_SIZE, AtaxxModel.TWO_TOKENS, 1);
    }

    private void createView(Scene scene) {
        pieces = new ArrayList<>();
        rectangles = new ArrayList<>();

        pane = new AnchorPane();
        NumberBinding rectsAreaSize = Bindings.min(scene.heightProperty(), scene.widthProperty());
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                AtaxxRegion r = new AtaxxRegion(model.get(i, j));
                r.xProperty().bind(rectsAreaSize.multiply(i).divide(BOARD_SIZE));
                r.yProperty().bind(rectsAreaSize.multiply(j).divide(BOARD_SIZE));
                r.heightProperty().bind(rectsAreaSize.divide(BOARD_SIZE));
                r.widthProperty().bind(r.heightProperty());

                Piece piece = model.get(i, j).getPiece();
                if (piece != null) {
                    AtaxxPiece pieceRegion = makeAtaxxPiece(piece, r);
                    pieces.add(pieceRegion);
                }
                rectangles.add(r);
                pane.getChildren().add(r);
            }
        }
        for (Circle c : pieces) {
            pane.getChildren().add(c);
        }

        ((VBox) scene.getRoot()).getChildren().add(pane);
    }
    private void setPieceHandlers(AtaxxPiece piece) {
        piece.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            AtaxxPiece piece = (AtaxxPiece) mouseEvent.getSource();
            if (model.isCurrentPlayerTurn(piece.getModel().getOwner())) {
                dragged = piece;
                piece.startMoving();
                for (AtaxxRegion r : rectangles) {
                    if (r != piece.getOrigin() && !r.getModel().isLocked()) {
                        if (r.getModel().isNear(piece.getOrigin().getModel(), 1)
                                || r.getModel().isNear(piece.getOrigin().getModel(), 2))
                            if (r.getModel().getPiece() != null || !Cell.isCellAvailable(r.getModel())) {
                                r.invalidate();
                            } else if (dragged.getOrigin().getModel().canMove(r.getModel())) {
                                r.validate();
                            }
                    }
                }
            }
            }
        });

        piece.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) { //TODO ajouter bornes supérieures
                if (dragged != null && mouseEvent.getX() > 0 && mouseEvent.getY() > 0) {
                    dragged.move(mouseEvent.getX(), mouseEvent.getY());
                }
                for (AtaxxRegion r : rectangles) {
                    if (r.contains(mouseEvent.getX(), mouseEvent.getY())) {
                        arrival = r;
                    }
                }
            }
        });

        piece.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (dragged != null) {
                    for (AtaxxRegion r : rectangles) {
                        if (r != dragged.getOrigin() && !r.getModel().isLocked()) {
                            if (r.getModel().isNear(dragged.getOrigin().getModel(), 1)
                                    || r.getModel().isNear(dragged.getOrigin().getModel(), 2))
                                r.clear();
                        }
                    }
                    if (arrival != null) {
                        if (!Cell.isCellAvailable(arrival.getModel()) || !dragged.getOrigin().getModel().canMove(arrival.getModel())) {
                            dragged.cancel();
                        } else {
                            try {
                                model.move(dragged.getOrigin().getModel(), arrival.getModel());
                                for (AtaxxPiece p : pieces) {
                                    pane.getChildren().remove(p);
                                }
                                pieces.clear();

                                for (AtaxxRegion r : rectangles) {
                                    Piece piece = r.getModel().getPiece();
                                    if (piece != null) {
                                        AtaxxPiece pieceRegion = makeAtaxxPiece(piece, r);
                                        pieces.add(pieceRegion);
                                        pane.getChildren().add(pieceRegion);
                                    }

                                }
                                model.changePlayer();
                            } catch (IllegalAccessException ex) {
                            }
                            System.out.println("[" + model.getBlueTokens() + ", " + model.getRedTokens() + "]");
                        }
                    } else {
                        dragged.cancel();
                    }
                }
                dragged = null;
                arrival = null;
            }
        });
    }
    private AtaxxPiece makeAtaxxPiece(Piece piece, AtaxxRegion r) {
        AtaxxPiece pieceRegion = new AtaxxPiece(piece, r);
        pieceRegion.radiusProperty().bind(r.heightProperty().divide(2).subtract(10));
        setPieceHandlers(pieceRegion);
        return pieceRegion;
    }

    private void createEventHandlers() {
        Item.NEW_GAME.setEvent(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(Item.NEW_GAME.getName());
            }
        });
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

    private void createController() {

    }
}
