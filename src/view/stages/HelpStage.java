package view.stages;

import javafx.scene.layout.GridPane;
import view.Ataxx;
//TODO
public class HelpStage extends AbstractStage {

    protected static AbstractStage instance;

    protected HelpStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Aide");
    }

    @Override
    protected void createView() {

    }

    @Override
    protected void placeComponents() {
        GridPane  grid = ((GridPane)scene.getRoot());
    }

    @Override
    protected void createController() {

    }

    @Override
    public void render() {
        show();
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new HelpStage(parent);
        }
        return instance;
    }
}
