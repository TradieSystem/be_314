package Types.User;
import net.datafaker.Faker;


public class RandomProfessional {
    public static final String TABLE = "professional";
    private static int CURRENT_ID = 1;
            
    public int professional_id;
    public int subscription_id = 1; // id of subscription based
    public int user_id; // should not be randomised

    public RandomProfessional() {}

    public static RandomProfessional generate(int user_id) { 
        RandomProfessional entity = new RandomProfessional();
        entity.professional_id = CURRENT_ID++;
        entity.user_id = user_id;
        Faker faker = new Faker();
        entity.subscription_id =faker.random().nextInt(1,2);
        return entity; 
        
    }
}
