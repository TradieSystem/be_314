package Types.User;

public class RandomClient {
    private int client_id;
    private int user_id; // should not be randomised
    private int subscription_id;

    public RandomClient(int user_id) {
        this.user_id = user_id;
    }
}
