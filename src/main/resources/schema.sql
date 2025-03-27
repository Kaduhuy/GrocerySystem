-- User table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100),
    password VARCHAR(255) NOT NULL
);

-- GroceryList table
CREATE TABLE IF NOT EXISTS groceryList (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- GroceryItem table (if not created yet)
CREATE TABLE IF NOT EXISTS groceryItem (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION,
    quantity INT,
    unit VARCHAR(20),
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Join table for many-to-many between GroceryList and GroceryItem
CREATE TABLE IF NOT EXISTS groceryListItem (
    grocery_list_id INT,
    grocery_item_id INT,
    quantity INT DEFAULT 1,
    PRIMARY KEY (grocery_list_id, grocery_item_id),
    FOREIGN KEY (grocery_list_id) REFERENCES grocery_list(id),
    FOREIGN KEY (grocery_item_id) REFERENCES grocery_item(id)
);
