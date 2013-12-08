package view.stages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import view.Ataxx;

public class BoardConfigurationStage extends AbstractStage {

    private Text sceneTitle;
    private Button btn;
    protected static AbstractStage instance;

    protected BoardConfigurationStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Configuration du plateau de jeu");
    }

    @Override
    protected void createView() {

        sceneTitle = new Text("Plateau");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


       /* userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);     */
        btn = new Button("Modifier la configuration");
    }

    @Override
    protected void placeComponents() {
        GridPane  grid = ((GridPane)scene.getRoot());
        //Title
        grid.add(sceneTitle, 0, 0, 2, 1);

        //Button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
    }

    @Override
    protected void createController() {
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
            }
        });
    }

    public static AbstractStage getInstance(Ataxx parent) {
        if (instance == null) {
            instance = new BoardConfigurationStage(parent);
        }
        return instance;
    }
}
