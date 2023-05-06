package Types.User;
import Types.Service.RandomService;
import java.util.List;

public class RandomProfessional {
    private int professional_id;
    private int subscription_id = 1; // id of subscription based
    private List<RandomService> services;
    private RandomBilling CCin;
    private int user_id; // should not be randomised

    public RandomProfessional(int user_id) {
        this.user_id = user_id;
    }
}
