package view.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import view.Ataxx;

public class DialogStage extends AbstractStage {

    protected static AbstractStage instance;
    private Button btn;
    protected DialogStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void createController() {
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                parent.reset();
                close();
            }
        });
    }

    @Override
    protected void init() {
        setTitle("Fin de la partie");
        initModality(Modality.WINDOW_MODAL);
    }

    @Override
    protected void createView() {
        btn = new Button("Ok");
        btn.setDefaultButton(true);
    }

    @Override
    protected void placeComponents() {
        GridPane grid = ((GridPane)scene.getRoot());
        //Title
        Text sceneTitle = new Text("Gagnant : ");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        //Button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        String name = "";
        if (parent.getWinner() != null) {
              name = parent.getWinner().getName();
        }
        hbBtn.getChildren().add(new Label("Le gagnant est le joueur " + name));
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 2);
    }

    @Override
    public void render() {
        show();
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new DialogStage(parent);
        }
        return instance;
    }
}

