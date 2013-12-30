package view.stages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.WindowEvent;
import view.Ataxx;

import java.net.URL;

public class HelpStage extends AbstractStage {

    protected static AbstractStage instance;
    private static final int TILE_NUMBER = 3;
    private static final String HISTORY = "resources/history.html";
    private static final String RULES = "resources/rules.html";
    private static final String VIDEO = "resources/tuto.mp4";
    private TitledPane[] tps;
    private MediaPlayer mediaPlayer;

    protected HelpStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Aide");
        setHeight(500);
        setWidth(700);
        URL url = HelpStage.class.getResource(VIDEO);
        mediaPlayer = new MediaPlayer(new Media(url.toString()));
    }

    @Override
    protected void createView() {
        tps = new TitledPane[TILE_NUMBER];
        tps[0] = new TitledPane("Histoire du jeu", buildWebView(HISTORY));
        tps[1] = new TitledPane("Règles du jeu", buildWebView(RULES));
        MediaView mv = new MediaView(mediaPlayer);
        mv.setFitHeight(480);
        mv.setFitWidth(640);
        tps[2] = new TitledPane("Video", mv);
    }

    private WebView buildWebView(String s) {
        URL url = HelpStage.class.getResource(s);
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url.toExternalForm());
        return webView;
    }

    @Override
    protected void placeComponents() {
        final Accordion accordion = new Accordion ();
        accordion.getPanes().addAll(tps[0], tps[1], tps[2]);
        accordion.setExpandedPane(tps[0]);
        scene.setRoot(accordion);
    }

    @Override
    protected void createController() {
        tps[2].expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    mediaPlayer.play();
                } else {
                    mediaPlayer.stop();
                }
            }
        });
        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                mediaPlayer.stop();
            }
        });
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
