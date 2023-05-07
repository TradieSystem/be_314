package Types.Service;

import Types.User.RandomUser;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.util.Date;

/*
We are not using start_date and completion_date in the final product.
Will confirm this but at the moment it is not included
 */
public class RandomRequest {
    public static final String TABLE = "request";
    public static int CURRENT_REQUEST_ID = 1;
    public Date request_date;
    public String instruction; // this will use a custom provider
    public String postcode; // should be based on the already generated postcodes
    public int client_id; // should be based on already generated client_ids
    public int professional_id; // can either be null or based on an already generated professional_id
    public int service_id; // must be based on the provided services
    public int request_status_id; // possibly should be generated first and all other attributes based off it

    public static RandomRequest GenerateRequest() {
        Faker faker = new Faker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();
        Schema<Object, ?> requestSchema = Schema.of(
                Field.field("user_id", () -> RandomRequest.CURRENT_REQUEST_ID++),
                Field.field("first_name", () -> faker.name().firstName()),
                Field.field("last_name", () -> faker.name().lastName()),
                Field.field("email_address", () -> faker.name().username() + "@outlook.com"),
                Field.field("mobile", () -> faker.phoneNumber().phoneNumberNational()),
                Field.field("password", () -> faker.regexify("[a-zA-Z0-9_.!@#$%^&*()]{6,12}"))
        );

        return (RandomRequest) transfomer.apply(RandomRequest.class, requestSchema);
    }
}
