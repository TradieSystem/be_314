package Types.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import net.datafaker.Faker;

public class RandomBilling {
    public static final String TABLE = "billing";
    private static int CURRENT_ID = 1; 
    
    
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

    public static RandomBilling generate(RandomUser user, boolean outgoing) {
        RandomBilling entity = new RandomBilling(user.user_id);
        entity.billing_id = CURRENT_ID++;
        entity.name= user.first_name + " " +user.last_name;
        Faker faker = new Faker();
        entity.card_number = faker.numerify("#### #### #### ####");
        entity.ccv = faker.numerify("###");
        int months =  faker.random().nextInt(0,36);
        LocalDate someDate  = LocalDate.now().plus(months, ChronoUnit.MONTHS);        
        entity.expiry_date = someDate.format(DateTimeFormatter.ofPattern("MM/yy"));
        if (outgoing) {
            entity.billing_type_id = 1;
        } else {
            entity.billing_type_id = 2; 
        }
        return entity;
    }
}
