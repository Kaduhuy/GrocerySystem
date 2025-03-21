package com.example.grocery_system.service;

import com.example.grocery_system.model.GroceryItem;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GroceryService {

    // Thread safe map for concurrency if needed
    private final Map<String, GroceryItem> groceryItemMap = new ConcurrentHashMap<>();
    private final Random random = new Random();

    private static final String[] POSSIBLE_NAMES = {
            "Apple", "Banana", "Carrot", "Donut", "Egg", "Fork", "Grape", "Honey",
            "Ice Pack", "Juice", "Kale", "Lemons", "Milk", "Nuts", "Onions", "Potatoes",
            "Quinoa", "Rice", "Spinach", "Tomatoes", "Ube", "Vanila Ice Cream", "Watermelon",
            "Xanthan Gum", "Yogurt", "Zucchini"
    };

    @PostConstruct
    public void loadFakeData(){
        for (int i = 0; i < 5; i++) {
            GroceryItem randomItem = createRandomGroceryItem();
            addGroceryItem(randomItem);
        }
    }

    public List<GroceryItem> getAllGroceryItems(){
        return new ArrayList<>(groceryItemMap.values());
    }

    public GroceryItem getGroceryItemById(String id){
        return groceryItemMap.get(id);
    }

    public GroceryItem addGroceryItem(GroceryItem item){
        // give id if item is missing it
        if(item.getId() == null || item.getId().isEmpty()){
            item.setId(UUID.randomUUID().toString());
        }
        groceryItemMap.put(item.getId(), item);
        return item;
    }

    public GroceryItem updateGroceryItem(String id, GroceryItem updatedItem){
        // check if item exists
        if(groceryItemMap.containsKey(id)){
            updatedItem.setId(id);
            groceryItemMap.put(id, updatedItem);
            return updatedItem;
        }
        return null;
    }

    public boolean deleteGroceryItem(String id){
        return groceryItemMap.remove(id) != null;
    }

    public GroceryItem createRandomGroceryItem(){
        //random attributes
        String randomId;
        String name = POSSIBLE_NAMES[random.nextInt(POSSIBLE_NAMES.length)];
        int price = 1 + random.nextInt(20);

        //keeps generating random id until it is unique
        do{
            randomId = String.valueOf(random.nextInt(999_999) + 1);
        }while (groceryItemMap.containsKey(randomId));

        //store new random item
        GroceryItem item = new GroceryItem(randomId, name, price);
        groceryItemMap.put(randomId, item);

        return item;
    }

}
