package Types.User;
import Types.Service.RandomService;
import java.util.ArrayList;

public class RandomProfessional {
    public static final String TABLE = "professional";
    public int professional_id;
    public int subscription_id = 1; // id of subscription based
    public ArrayList<RandomService> services;
    public RandomBilling CCin;
    public int user_id; // should not be randomised

    public RandomProfessional(int user_id) {
        this.user_id = user_id;
    }
}
