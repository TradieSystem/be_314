package Types.User;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class RandomClient {
    public static final String TABLE = "client";
    public static int CURRENT_CLIENT_ID = 1;
    public int client_id;
    public int user_id; // should not be randomised
    public int subscription_id;

    public RandomClient() {}

    public static RandomClient generate(int user_id) {
        Faker faker = new Faker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();
        Schema<Object, ?> clientSchema = Schema.of(
                Field.field("client_id", () -> RandomClient.CURRENT_CLIENT_ID++),
                Field.field("user_id", () -> user_id),
                Field.field("subscription_id", () -> faker.random().nextInt(1,2)) // subscription_id is either 1 or 2
        );

        return (RandomClient) transfomer.apply(RandomClient.class, clientSchema);
    }
}
