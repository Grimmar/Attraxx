package view.stages;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import view.Ataxx;

import java.net.URL;

public class HelpStage extends AbstractStage {

    protected static AbstractStage instance;
    private static final int TILE_NUMBER = 5;
    private static final String HISTORY = "history.html";
    private static final String GOAL = "goal.html";
    private static final String RULES = "rules.html";
    private static final String LINKS = "links.html";
    private static final String VIDEO = "ataxx.mp4";
    TitledPane[] tps = new TitledPane[TILE_NUMBER];

    protected HelpStage(Ataxx parent) {
        super(parent);
    }

    @Override
    protected void init() {
        setTitle("Aide");
        setHeight(500);
        setWidth(800);
    }

    @Override
    protected void createView() {
        tps = new TitledPane[TILE_NUMBER];
        tps[0] = new TitledPane("Histoire du jeu", buildWebView(HISTORY));
        tps[1] = new TitledPane("But du jeu", buildWebView(GOAL));
        tps[2] = new TitledPane("Règles du jeu", buildWebView(RULES));
        //MediaPlayer mp = new MediaPlayer(new Media(VIDEO));
        tps[3] = new TitledPane("Video", new MediaView());
        tps[4] = new TitledPane("Liens utiles", buildWebView(LINKS));
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
        accordion.getPanes().addAll(tps);
        accordion.setExpandedPane(tps[0]);
        scene.setRoot(accordion);
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
