package com.library.db;
import com.library.model.items.Item;
import java.util.List;

public interface ItemDAO {
    // Returnerar en lista med alla träffar baserat på sökordet
    List<Item> search(String title, String creator, String categoryId);
}
