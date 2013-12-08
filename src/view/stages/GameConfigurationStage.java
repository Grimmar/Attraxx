package view.stages;

import javafx.scene.layout.GridPane;
import view.Ataxx;

public class GameConfigurationStage extends AbstractStage {
    protected static AbstractStage instance;

    protected GameConfigurationStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Configuration du mode de jeu");
    }

    @Override
    protected void createView() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void placeComponents() {
        GridPane grid = ((GridPane)scene.getRoot());
    }

    @Override
    protected void createController() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new GameConfigurationStage(parent);
        }
        return instance;
    }
}
