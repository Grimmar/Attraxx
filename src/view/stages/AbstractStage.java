package view.stages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import view.Ataxx;

public abstract class AbstractStage extends Stage {
    protected Ataxx parent;
    protected Scene scene;

    protected AbstractStage(Ataxx parent) {
        this.parent = parent;
        init();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        scene = new Scene(grid, 400, 300);

        createView();
        placeComponents();
        createController();

        setScene(scene);
        sizeToScene();
    }

    protected abstract void init();
    protected abstract void createView();
    protected abstract void placeComponents();
    protected abstract void createController();
    public abstract void render();
}
