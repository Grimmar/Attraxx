package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public enum ItemEnum {
    NEW_GAME("Nouvelle partie"), CLOSE("Fermer"), GAME("Mode de jeu"), BOARD("Plateau"), HELP("Aide");
    private final String name;
    private EventHandler<ActionEvent> event;
    ItemEnum(String name) {
        this.name = name;
    }

    public EventHandler<ActionEvent> getEvent() {
        return event;
    }

    public void setEvent(EventHandler<ActionEvent> event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }
}
