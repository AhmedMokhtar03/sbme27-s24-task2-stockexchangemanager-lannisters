package backend;

public class SecurityFactory {
    private static SecurityFactory instance;

    private SecurityFactory() {
    }

    public static SecurityFactory getInstance() {
        if (instance == null) {
            instance = new SecurityFactory();
        }
        return instance;
    }

    public static Securities createSecurity(String type, String label, int quantity, String state) {
        if (type.equalsIgnoreCase("stock")) {
            return new Stock(label, quantity, state);
        } else {
            throw new RuntimeException("Unsupported security type: " + type);
        }
    }
}