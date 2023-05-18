package Types.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Provider.CustomFaker;
import Types.User.RandomBilling;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class RandomTransaction {

    public static final String TABLE = "transaction";
    public static int CURRENT_ID = 1;
        
    public int transaction_id;
    public double amount;
    public Date transaction_date;
    public int transaction_status_id; // 1== accepted, 2= rejected
    public int user_id;
    public int billing_type_id; // 1== Out, 2 = in
    public int billing_id;
    
    public RandomTransaction() {
    }
    
    public static RandomTransaction generate(RandomBilling billing, RandomRequestBid requestBid) {
        /*
        RandomTransaction entity = new RandomTransaction();
        entity.transaction_id = CURRENT_ID++;
        entity.user_id = billing.user_id;
        entity.billing_type_id = billing.billing_type_id;
        entity.billing_id = billing.billing_id;   
        Faker faker = new Faker();        
        int period =faker.random().nextInt(1, 60*60*10);
        entity.transaction_date = LocalDateTime.now().minus(period, ChronoUnit.SECONDS);
        return entity;
        */

        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        Schema<Object, ?> transactionSchema = Schema.of(
                Field.field("transaction_id", () -> RandomTransaction.CURRENT_ID++),
                Field.field("amount", () -> requestBid.amount),
                Field.field("transaction_date", () -> faker.date().future(20, TimeUnit.DAYS, requestBid.sent_date)),
                Field.field("transaction_status_id", () -> faker.random().nextInt(1,2)),
                Field.field("user_id", () -> billing.user_id),
                Field.field("billing_type_id", () -> billing.billing_type_id),
                Field.field("billing_id", () -> billing.billing_id)
        );

        return (RandomTransaction) transfomer.apply(RandomTransaction.class, transactionSchema);
    } 
}
