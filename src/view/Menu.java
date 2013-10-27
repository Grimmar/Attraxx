package view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Menu {
    FICHIER("Fichier");

    private static final Map<Menu, List<Item>> menus;
    private final String name;

    static {
        menus = new HashMap<>();
        menus.put(FICHIER, new ArrayList<Item>(){{
            add(Item.NEW_GAME);
        }});
    }

    Menu(String name) {
        this.name = name;
    }

    public static List<Item> getItems(Menu menu) {
        return menus.get(menu);
    }

    public String getName() {
        return name;
    }
}
