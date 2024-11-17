public class Product {
    private String id;
    private String name;
    private String category;
    private double price;

    public Product(String id, String name, String category, String price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = parsePrice(price);
    }

    private double parsePrice(String priceStr) {
        try {
            return Double.parseDouble(priceStr.replace("$", "").replace(",", "").trim());
        } catch (NumberFormatException e) {
            // handle price ranges by taking lower value
            if (priceStr.contains("-")) {
                return parsePrice(priceStr.split("-")[0]);
            }
            return 0.0;
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("Product ID: %s\nName: %s\nCategory: %s\nPrice: $%.2f", id, name, category, price);
    }
}
