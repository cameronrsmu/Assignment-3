import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        String line;
        boolean firstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader("amazon-product-data.csv"))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (fields.length >= 4) {
                    String id = fields[0].trim();
                    String name = fields[1].replaceAll("\"", "").trim();
                    String category = fields[2].trim();
                    String price = fields[3].trim();

                    Product product = new Product(id, name, category, price);
                    tree.insert(product);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            return;
        }

        String[] testIds = {
                "4c69b61db1fc16e7013b43fc926e502d",
                "954fb803841ce010626740c8a776e981",
                "78c66630b1b56bb6ea887ea781b07d47",
                "nonexistent_id"
        };

        System.out.println("Search Results:");
        System.out.println("---------------");
        for (String id : testIds) {
            Product found = tree.search(id);
            if (found != null) {
                System.out.println("\nFound product:");
                System.out.println(found);
            } else {
                System.out.println("\nProduct with ID " + id + " not found.");
            }
        }

        System.out.println("\nTesting Duplicate Product Insertion");
        String duplicateId = "4c69b61db1fc16e7013b43fc926e502d";
        Product existingProduct = tree.search(duplicateId);
        System.out.println("Original product with ID " + duplicateId + ":");
        System.out.println(existingProduct);

        System.out.println("\nAttempting to insert duplicate product with same ID...");
        try {
            Product duplicateProduct = new Product(
                    duplicateId,
                    "Duplicate Product",
                    "Test Category",
                    "$99.99"
            );
            tree.insert(duplicateProduct);
            System.out.println("WARNING: Duplicate insertion succeeded when it should have failed!");
        } catch (IllegalArgumentException e) {
            System.out.println("Successfully caught duplicate insertion: " + e.getMessage());
        }

        System.out.println("\nTesting New Product Insertion");
        String newId = "new_unique_id_12345";
        System.out.println("Attempting to insert new product with ID: " + newId);
        try {
            Product newProduct = new Product(
                    newId,
                    "New Product",
                    "New Category",
                    "$50.00"
            );
            tree.insert(newProduct);

            Product foundNew = tree.search(newId);
            if (foundNew != null) {
                System.out.println("Successfully inserted and found new product:");
                System.out.println(foundNew);
            } else {
                System.out.println("Failed to find newly inserted product!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error inserting new product: " + e.getMessage());
        }

        System.out.println("\nEnter a product ID to search (or type 'exit' to stop): ");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine().trim();
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            Product found = tree.search(userInput);
            if (found != null) {
                System.out.println("\nFound product:");
                System.out.println(found);
            } else {
                System.out.println("\nProduct with ID " + userInput + " not found.");
            }
            System.out.print("\nEnter a product ID to search (or type 'exit' to stop): ");
        }
        scanner.close();
    }
}
