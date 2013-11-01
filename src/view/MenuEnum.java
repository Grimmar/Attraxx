package view;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MenuEnum {
    FICHIER("Fichier");

    private static final Map<MenuEnum, List<ItemEnum>> menus;
    private final String name;

    static {
        menus = new HashMap<>();
        menus.put(FICHIER, new ArrayList<ItemEnum>(){{
            add(ItemEnum.NEW_GAME);
            add(null);
            add(ItemEnum.CLOSE);
        }});
    }

    MenuEnum(String name) {
        this.name = name;
    }

    public static List<ItemEnum> getItems(MenuEnum menu) {
        return menus.get(menu);
    }

    public String getName() {
        return name;
    }
}
