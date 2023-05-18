package Types.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import Provider.CustomFaker;
import Types.Service.RandomRequestBid;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

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

    public RandomBilling() {}

    public static RandomBilling generate(RandomUser user, boolean outgoing) {
        /*RandomBilling entity = new RandomBilling(user.user_id);
        entity.billing_id = CURRENT_ID++;
        entity.name= user.first_name + " " +user.last_name;
        Faker faker = new Faker();
        entity.card_number = faker.numerify("#### #### #### ####");
        entity.ccv = faker.numerify("###");
        int months =  faker.random().nextInt(0,36);
        LocalDate someDate  = LocalDate.now().plus(months, ChronoUnit.MONTHS);        
        entity.expiry_date = someDate.format(DateTimeFormatter.ofPattern("MM/yy"));
        entity.user_id = user.user_id;
        if (outgoing) {
            entity.billing_type_id = 1;
        } else {
            entity.billing_type_id = 2; 
        }
        return entity;
         */

        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        Schema<Object, ?> billingSchema = Schema.of(
                Field.field("billing_id", () -> RandomBilling.CURRENT_ID++),
                Field.field("name", () -> user.first_name + " " + user.last_name),
                Field.field("card_number", () -> faker.numerify("#### #### #### ####")),
                Field.field("expiry_date", () -> faker.regexify("[1-9]{2}/[1-9]{4}")),
                Field.field("ccv", () -> faker.regexify("[1-9]{3}")),
                Field.field("billing_type_id", () -> outgoing ? 1 : 2),
                Field.field("user_id", () -> user.user_id)
        );

        return (RandomBilling) transfomer.apply(RandomBilling.class, billingSchema);
    }
}
