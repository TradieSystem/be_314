package Types.User;

public class RandomBilling {
    public static final String TABLE = "billing";
    public int billing_id;
    public String name;
    public String card_number;
    public String expiry_date;
    public String ccv;
    public int billing_type_id; // should be outgoing if class type is User else should be incoming i.e. no randomness
    public int user_id; // should not be randomised

    public RandomBilling(int user_id) {
        this.user_id = user_id;
    }

    public static RandomBilling GenerateBilling(int user_id) { return null; }
}
