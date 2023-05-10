package Types.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import Types.User.RandomBilling;
import net.datafaker.Faker;

public class RandomTransaction {

    public static final String TABLE = "transaction";
    public static int CURRENT_ID = 1;
        
    public int transaction_id;
    public double amount;
    public LocalDateTime transaction_date;
    public int transaction_status_id; // 1== accepted, 2= rejected
    public int user_id;
    public int billing_type_id; // 1== Out, 2 = in
    public int billing_id;
    
    public RandomTransaction() {
    }
    
    public static RandomTransaction generate(RandomBilling billing) {
        RandomTransaction entity = new RandomTransaction();
        entity.transaction_id = CURRENT_ID++;
        entity.user_id = billing.user_id;
        entity.billing_type_id = billing.billing_type_id;
        entity.billing_id = billing.billing_id;   
        Faker faker = new Faker();        
        int period =faker.random().nextInt(1, 60*60*10);
        entity.transaction_date = LocalDateTime.now().minus(period, ChronoUnit.SECONDS);
        return entity;
    } 
}
