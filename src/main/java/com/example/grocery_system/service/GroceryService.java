package com.example.grocery_system.service;

import com.example.grocery_system.model.GroceryCategory;
import com.example.grocery_system.model.GroceryItem;
import com.example.grocery_system.repository.GroceryRepository;
import com.example.grocery_system.util.PixabayImageSearch;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroceryService {

    @Autowired
    private final GroceryRepository groceryRepository;
    private final Random random = new Random();

    private static final Map<String, Object[]> POSSIBLE_GROCERIES = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("Apple", new Object[]{5.0, GroceryCategory.FRUITS}),
            new AbstractMap.SimpleEntry<>("Banana", new Object[]{10.0, GroceryCategory.FRUITS}),
            new AbstractMap.SimpleEntry<>("Carrot", new Object[]{10.0, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Donut", new Object[]{13.5, GroceryCategory.BAKERY}),
            new AbstractMap.SimpleEntry<>("Egg", new Object[]{40.0, GroceryCategory.DAIRY}),
            new AbstractMap.SimpleEntry<>("Flour", new Object[]{20.0, GroceryCategory.BAKERY}),
            new AbstractMap.SimpleEntry<>("Grape", new Object[]{30.0, GroceryCategory.FRUITS}),
            new AbstractMap.SimpleEntry<>("Honey", new Object[]{50.0, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Ice Cream", new Object[]{40.0, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Juice", new Object[]{30.0, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Kale", new Object[]{24.5, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Lemons", new Object[]{10.5, GroceryCategory.FRUITS}),
            new AbstractMap.SimpleEntry<>("Milk", new Object[]{28.0, GroceryCategory.DAIRY}),
            new AbstractMap.SimpleEntry<>("Nuts", new Object[]{40.0, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Onions", new Object[]{10.0, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Potatoes", new Object[]{20.0, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Quinoa", new Object[]{40.5, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Rice", new Object[]{30.0, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Spinach", new Object[]{20.0, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Tomatoes", new Object[]{20.5, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Ube", new Object[]{30.0, GroceryCategory.VEGETABLES}),
            new AbstractMap.SimpleEntry<>("Vanilla", new Object[]{40.0, GroceryCategory.BAKERY}),
            new AbstractMap.SimpleEntry<>("Watermelon", new Object[]{60.0, GroceryCategory.FRUITS}),
            new AbstractMap.SimpleEntry<>("Xanthan Gum", new Object[]{50.5, GroceryCategory.SNACKS}),
            new AbstractMap.SimpleEntry<>("Yogurt", new Object[]{20.5, GroceryCategory.DAIRY}),
            new AbstractMap.SimpleEntry<>("Zucchini", new Object[]{20.0, GroceryCategory.VEGETABLES})
    );

    public GroceryService(GroceryRepository groceryRepository) {
        this.groceryRepository = groceryRepository;
    }

    @PostConstruct
    public void loadFakeData() {
        try {
            for (int i = 0; i < 25; i++) {
                GroceryItem randomItem = createRandomGroceryItem();
                addGroceryItem(randomItem);
            }
            System.out.println("Fake data loaded successfully");
        } catch (Exception e) {
            System.err.println("Failed to load fake data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<GroceryItem> getAllGroceryItems() {
        return groceryRepository.findAll();
    }

    public GroceryItem getGroceryItemById(String id) {
        return groceryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grocery item not found with ID: " + id));
    }

    public List<GroceryItem> getItemsByCategory(GroceryCategory category) {
        return groceryRepository.findByCategory(category);
    }

    public GroceryItem addGroceryItem(GroceryItem item) {
        return groceryRepository.save(item);
    }

    public GroceryItem updateGroceryItem(Long id, GroceryItem updatedItem) {
        if (groceryRepository.existsById(id.toString())) {
            updatedItem.setId(id);
            return groceryRepository.save(updatedItem);
        }
        return null;
    }

    public boolean deleteGroceryItem(String id) {
        if (groceryRepository.existsById(id)) {
            groceryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public GroceryItem createRandomGroceryItem() {
        String name = generateUniqueName();

        Object[] groceryData = POSSIBLE_GROCERIES.get(name);
        double price = (double) groceryData[0];
        GroceryCategory category = (GroceryCategory) groceryData[1];
        int quantity = 1 + random.nextInt(20);

        String imageUrl = generateImageUrl(name);

        // Hibernate will auto-generate the ID
        return new GroceryItem(
                null,
                name,
                price,
                quantity,
                category,
                imageUrl
        );
    }

    /**
     * @return unique name from grocery list
     * */
    private String generateUniqueName() {
        for (String name : POSSIBLE_GROCERIES.keySet()) {
            boolean exists = groceryRepository.findByName(name) != null;
            if (!exists) {
                return name;
            }
        }

        // if no unique name is available, generate a random one
        List<String> availableNames = new ArrayList<>(POSSIBLE_GROCERIES.keySet());
        return availableNames.get(random.nextInt(availableNames.size())) + "_" + random.nextInt(10000);
    }

    /**
     * @param groceryName The name of the grocery item
     * @return URL image of the item
     * */

    private String generateImageUrl(String groceryName) {
        try {
            return PixabayImageSearch.getPixabayImageUrl(groceryName);
        } catch (Exception e) {
            System.err.println("Failed to get Pixabay Image for " + groceryName + ": " +
                    Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

}
