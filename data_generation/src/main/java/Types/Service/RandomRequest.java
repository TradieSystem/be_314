package Types.Service;

import Provider.CustomFaker;
import Types.User.RandomProfessional;
import Types.User.RandomUser;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*
We are not using start_date and completion_date in the final product.
Will confirm this but at the moment it is not included
 */
public class RandomRequest {
    public static final String TABLE = "request";
    public static int CURRENT_REQUEST_ID = 1;
    public int request_id;
    public Date request_date;
    public String instruction; // this will use a custom provider
    public String postcode; // should be based on the already generated postcodes
    public int client_id; // should be based on already generated client_ids
    public Integer professional_id; // can either be null or based on an already generated professional_id
    public int service_id; // must be based on the provided services
    public int request_status_id; // possibly should be generated first and all other attributes based off it

    public static RandomRequest GenerateRequest(int client_id, String postcode) {
        CustomFaker faker = new CustomFaker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        Schema<Object, ?> requestSchema = Schema.of(
                Field.field("request_id", () -> RandomRequest.CURRENT_REQUEST_ID++),
                Field.field("request_date", () -> faker.date().future(30, TimeUnit.DAYS)),
                Field.field("instruction", () -> faker.request().description().tree()),
                Field.field("postcode", () -> faker.regexify("[1-3]{1}[0-9]{1}[0-9]{1}[0-9]{1}")),
                Field.field("client_id", () -> client_id),
                Field.field("professional_id", () -> null),
                Field.field("service_id", () -> faker.random().nextInt(1,5)), // first service_id is 1 and last is 5
                Field.field("request_status_id", () -> faker.random().nextInt(1,5))  // first status_id is 1 and last is 5
        );

        return (RandomRequest) transfomer.apply(RandomRequest.class, requestSchema);
    }
}
