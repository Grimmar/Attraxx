package view;

import javax.swing.Action;

public enum Item {
    NEW_GAME("Nouvelle partie");
    private final String name;
    private Action action;
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
}
