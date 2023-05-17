package Types.User;

import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.JavaObjectTransformer;
import net.datafaker.transformations.Schema;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static RandomUser generate() {
        Faker faker = new Faker();
        JavaObjectTransformer transfomer = new JavaObjectTransformer();

        // generate password to match front end MD5 method
        StringBuilder password = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("MyPassword1!".getBytes());
            //faker.regexify("[a-zA-Z0-9_.!@#$%^&*()]{14,16}")
            byte[] digest = md.digest();
            BigInteger integer = new BigInteger(1, digest);
            password = new StringBuilder(integer.toString(16));
            while (password.length() < 32) {
                password.insert(0, "0");
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        StringBuilder finalPassword = password;
        Schema<Object, ?> userSchema= Schema.of(
                Field.field("user_id", () -> RandomUser.CURRENT_USER_ID++),
                Field.field("first_name", () -> faker.name().firstName()),
                Field.field("last_name", () -> faker.name().lastName()),
                Field.field("email_address", () -> faker.name().username() + "@outlook.com"),
                Field.field("mobile", () -> faker.phoneNumber().phoneNumberNational()),
                Field.field("password", finalPassword::toString)
        );

        return (RandomUser) transfomer.apply(RandomUser.class, userSchema);
    }
}
