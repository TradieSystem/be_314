package Types.User;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

public class RandomUser {
    public static final String TABLE = "user";
    public static int CURRENT_USER_ID = 1;
    public int user_id;
    public String first_name;
    public String last_name;
    public String email_address; // simple way to handle this may be to do a combination of first and last name
    public String mobile;
    public String password;



    public RandomUser() {}

    public static RandomUser GenerateUser() {
        Faker faker = new Faker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();
        Schema<Object, ?> userSchema= Schema.of(
                Field.field("user_id", () -> RandomUser.CURRENT_USER_ID++),
                Field.field("first_name", () -> faker.name().firstName()),
                Field.field("last_name", () -> faker.name().lastName()),
                Field.field("email_address", () -> faker.name().username() + "@outlook.com"),
                Field.field("mobile", () -> faker.phoneNumber().phoneNumberNational()),
                Field.field("password", () -> faker.regexify("[a-zA-Z0-9_.!@#$%^&*()]{6,12}"))
        );

        return (RandomUser) transfomer.apply(RandomUser.class, userSchema);
    }
}
