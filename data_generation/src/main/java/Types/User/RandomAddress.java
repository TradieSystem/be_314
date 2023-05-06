package Types.User;

public class RandomAddress {
    private int address_id;
    private int street_number;
    private String street_name;
    private String suburb;
    private String postcode;
    private int user_id; // should not be randomised here

    public RandomAddress(int user_id) {
        this.user_id = user_id;
    }
}
