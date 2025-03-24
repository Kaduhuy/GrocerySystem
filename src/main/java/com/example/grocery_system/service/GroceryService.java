package com.example.grocery_system.service;

import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.util.PixabayImageSearch;
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
            "Apple", "Banana", "Carrot", "Donut", "Egg", "Flour", "Grape", "Honey",
            "Ice Cream", "Juice", "Kale", "Lemons", "Milk", "Nuts", "Onions", "Potatoes",
            "Quinoa", "Rice", "Spinach", "Tomatoes", "Ube", "Vanila", "Watermelon",
            "Xanthan Gum", "Yogurt", "Zucchini"
    };

    @PostConstruct
    public void loadFakeData(){
        for (int i = 0; i < 25; i++) {
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
        String name = generateUniqueName();
        int price = 1 + random.nextInt(20);

        //keeps generating random id until it is unique
        do{
            randomId = String.valueOf(random.nextInt(999_999) + 1);
        }while (groceryItemMap.containsKey(randomId));

        String imageURL = generateImageUrl(name);

        GroceryItem item = new GroceryItem(randomId, name, price, imageURL);
        groceryItemMap.put(randomId, item);

        return item;
    }

    /**
     * @return unique name from grocery list
     * */
    private String generateUniqueName() {
        for (String name : POSSIBLE_NAMES) {
            boolean exists = false;
            // check if name is in grocery list
            for (GroceryItem existingItem : groceryItemMap.values()) {
                if (existingItem.getName().equalsIgnoreCase(name)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                return name; //if it does not exist in grocery list, return name
            }
        }
        //if there are no more unique name in grocery list, create a new one with a random number
        return POSSIBLE_NAMES[random.nextInt(POSSIBLE_NAMES.length)] + "_" + random.nextInt(10000);
    }

    /**
     * @param groceryName The name of the grocery item
     * @return URL image of the item
     * */

    private String generateImageUrl(String groceryName){
        try {
            return PixabayImageSearch.getPixabayImageUrl(groceryName);
        } catch (Exception e) {
            System.err.println("Failed to get Pexels Image for " + groceryName + ": " + e.getStackTrace());
            return null;
        }
    }

}
