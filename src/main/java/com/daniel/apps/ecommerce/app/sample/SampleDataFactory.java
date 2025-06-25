package com.daniel.apps.ecommerce.app.sample;

import com.daniel.apps.ecommerce.app.model.Address;
import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Product;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.model.enums.CategoryName;
import com.daniel.apps.ecommerce.app.model.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SampleDataFactory {

    public static List<Product> createSampleProducts( Category electronics) {


        List<Product> products = List.of(
                new Product("Smartphone", "Latest 5G smartphone with high-end specs",
                        new BigDecimal("799.99"), 50, electronics),

                new Product("Laptop", "Powerful laptop for work and gaming",
                        new BigDecimal("1299.99"), 30, electronics),

                new Product("Bluetooth Headphones", "Noise-canceling wireless headphones",
                        new BigDecimal("199.99"), 100, electronics),

                new Product("Smartwatch", "Fitness and notification tracker with OLED display",
                        new BigDecimal("149.99"), 75, electronics),

                new Product("4K Monitor", "27-inch 4K UHD monitor for professionals",
                        new BigDecimal("349.99"), 20, electronics),

                new Product("Gaming Mouse", "High DPI mouse with RGB lighting",
                        new BigDecimal("59.99"), 150,  electronics),

                new Product("Mechanical Keyboard", "Tactile keys with backlight",
                        new BigDecimal("89.99"), 80, electronics),

                new Product("External SSD", "1TB USB-C portable SSD drive",
                        new BigDecimal("119.99"), 60,  electronics),

                new Product("Wireless Charger", "Fast wireless charging pad",
                        new BigDecimal("39.99"), 120,  electronics),

                new Product("Webcam", "1080p HD webcam for video conferencing",
                        new BigDecimal("69.99"), 40, electronics)
        );

        electronics.setProducts(products);

        return products;
    }

    public static List<Product> createSampleProducts() {

        return List.of(
                new Product("Smartphone", "Latest 5G smartphone with high-end specs",
                        new BigDecimal("799.99"), 50),

                new Product("Laptop", "Powerful laptop for work and gaming",
                        new BigDecimal("1299.99"), 30),

                new Product("Bluetooth Headphones", "Noise-canceling wireless headphones",
                        new BigDecimal("199.99"), 100),

                new Product("Smartwatch", "Fitness and notification tracker with OLED display",
                        new BigDecimal("149.99"), 75),

                new Product("4K Monitor", "27-inch 4K UHD monitor for professionals",
                        new BigDecimal("349.99"), 20),

                new Product("Gaming Mouse", "High DPI mouse with RGB lighting",
                        new BigDecimal("59.99"), 150),

                new Product("Mechanical Keyboard", "Tactile keys with backlight",
                        new BigDecimal("89.99"), 80),

                new Product("External SSD", "1TB USB-C portable SSD drive",
                        new BigDecimal("119.99"), 60),

                new Product("Wireless Charger", "Fast wireless charging pad",
                        new BigDecimal("39.99"), 120),

                new Product("Webcam", "1080p HD webcam for video conferencing",
                        new BigDecimal("69.99"), 40)
        );
    }


    public static List<Address> createAddresses() {
        return List.of(
                new Address("123 Maple Street", "Springfield", "Illinois", "62701", "USA"),
                new Address("456 Oak Avenue", "Madison", "Wisconsin", "53703", "USA"),
                new Address("789 Pine Road", "Austin", "Texas", "73301", "USA"),
                new Address("101 Birch Lane", "Denver", "Colorado", "80201", "USA"),
                new Address("202 Cedar Boulevard", "Seattle", "Washington", "98101", "USA"),
                new Address("303 Walnut Drive", "Columbus", "Ohio", "43085", "USA"),
                new Address("404 Cherry Court", "Tallahassee", "Florida", "32301", "USA"),
                new Address("505 Ash Place", "Albany", "New York", "12201", "USA"),
                new Address("606 Poplar Terrace", "Phoenix", "Arizona", "85001", "USA"),
                new Address("707 Elm Square", "Portland", "Oregon", "97201", "USA")
        );
    }
    public static List<Category> createCategories() {
        return Arrays.stream(CategoryName.values())
                .map(cat->new Category(cat.name()))
                .collect(Collectors.toList());
    }

    public static List<User> createUsers(List<Address> addresses) {
        return List.of(
                createUser("Alice", "Smith", "alice@example.com", addresses.get(0)),
                createUser( "Bob", "Johnson", "bob@example.com", addresses.get(1)),
                createUser( "Carol", "Williams", "carol@example.com", addresses.get(2)),
                createUser("David", "Brown", "david@example.com", addresses.get(3)),
                createUser( "Eva", "Jones", "eva@example.com", addresses.get(4)),
                createUser( "Frank", "Garcia", "frank@example.com", addresses.get(5)),
                createUser( "Grace", "Miller", "grace@example.com", addresses.get(6)),
                createUser( "Henry", "Davis", "henry@example.com", addresses.get(7)),
                createUser( "Ivy", "Martinez", "ivy@example.com", addresses.get(8)),
                createUser( "Jack", "Taylor", "jack@example.com", addresses.get(9))
        );
    }
    private static User createUser(String firstName, String lastName, String email, Address address) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword("password123");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRoles(new HashSet<>(Set.of(Role.ROLE_USER)));
        user.setEnabled(true);
        user.setNonLocked(true);
        user.setAddress(address);
        address.setUser(user);
        return user;
    }
}
