import java.io.*;

public class Main {
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();

        // read and load csv file
        String line;
        boolean firstLine = true;
        try (BufferedReader br = new BufferedReader(new FileReader("amazon-product-data.csv"))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // split csv line, handle quoted fields
                String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (fields.length >= 4) {
                    String id = fields[0].trim();
                    String name = fields[1].replaceAll("\"", "").trim();
                    String category = fields[2].trim();
                    String price = fields[3].trim();

                    try {
                        Product product = new Product(id, name, category, price);
                        tree.insert(product);
                    } catch (IllegalArgumentException e) {
                        System.out.println("error");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("error");
            return;
        }

        // search operations
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

        // insertion of duplicate product
        try {
            Product duplicateProduct = new Product(
                    "4c69b61db1fc16e7013b43fc926e502d",
                    "Duplicate Product",
                    "Test Category",
                    "$99.99"
            );
            tree.insert(duplicateProduct);
        } catch (IllegalArgumentException e) {
            System.out.println("error");
        }
    }
}
