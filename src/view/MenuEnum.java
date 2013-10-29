package view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MenuEnum {
    FICHIER("Fichier");

    private static final Map<MenuEnum, List<Item>> menus;
    private final String name;

    static {
        menus = new HashMap<>();
        menus.put(FICHIER, new ArrayList<Item>(){{
            add(Item.NEW_GAME);
        }});
    }

    MenuEnum(String name) {
        this.name = name;
    }

    public static List<Item> getItems(MenuEnum menu) {
        return menus.get(menu);
    }

    public String getName() {
        return name;
    }
}
