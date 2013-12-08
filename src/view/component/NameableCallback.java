package view.component;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import model.Nameable;

public class NameableCallback<E extends Nameable> implements Callback<ListView<E>, ListCell<E>> {

    @Override
    public ListCell<E> call(ListView<E> listView) {
        final ListCell<E> cell = new ListCell<E>() {
            @Override
            public void updateItem(E item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        };
        return cell;
    }
}
