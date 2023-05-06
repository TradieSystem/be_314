package Types.User;

public class RandomBilling {
    private int billing_id;
    private String name;
    private String card_number;
    private String expiry_date;
    private String ccv;
    private int billing_type_id; // should be outgoing if class type is User else should be incoming i.e. no randomness
    private int user_id; // should not be randomised

    public RandomBilling(int user_id) {
        this.user_id = user_id;
    }
}
