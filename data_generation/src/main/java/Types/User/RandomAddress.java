package Types.User;

public class RandomAddress {
    public static final String TABLE = "address";
    public int address_id;
    public int street_number;
    public String street_name;
    public String suburb;
    public String postcode;
    public int user_id; // should not be randomised here

    public RandomAddress(int user_id) {
        this.user_id = user_id;
    }

    public static RandomAddress GenerateAddress(int user_id) { return null; }
}
