package Types.User;

public class RandomClient {
    public static final String TABLE = "client";
    public int client_id;
    public int user_id; // should not be randomised
    public int subscription_id;

    public RandomClient(int user_id) {
        this.user_id = user_id;
    }
}
