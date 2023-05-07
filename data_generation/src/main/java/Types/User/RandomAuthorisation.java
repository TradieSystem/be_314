package Types.User;

import net.datafaker.Faker;

public class RandomAuthorisation {

    public static final String TABLE = "authorisation";
    private static int CURRENT_ID = 1; 
    
    public int authorisation_id;
    public int user_id;
    public String refresh_token;
    public int  number_of_uses;   
    public String invalidated;
    
    public RandomAuthorisation(int user_id) {
        this.user_id = user_id;
    }
    
    public static RandomAuthorisation generate(RandomUser user) {
        Faker faker = new Faker();
        RandomAuthorisation entity = new RandomAuthorisation(user.user_id);
        entity.authorisation_id = CURRENT_ID++;
        entity.refresh_token = java.util.UUID.randomUUID().toString();
        entity.number_of_uses = faker.random().nextInt(0,5);
        if (entity.number_of_uses==0) {
            entity.invalidated = "Y"; 
        } else {
            entity.invalidated = "N";
        }
        return entity;
    }
    
}
