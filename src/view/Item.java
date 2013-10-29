package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.swing.*;

public enum Item {
    NEW_GAME("Nouvelle partie");
    private final String name;
    //TODO REMOVE WHEN JAVAFX COMPLETE
    private Action action;
    private EventHandler<ActionEvent> event;
    Item(String name) {
        this.name = name;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        action.putValue(Action.NAME, name);
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
