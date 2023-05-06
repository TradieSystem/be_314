package Types.User;

public class RandomUser {
    public static final String TABLE = "user";
    public int user_id;
    public String first_name;
    public String last_name;
    public String email_address; // simple way to handle this may be to do a combination of first and last name
    public String mobile;
    public String password;

    public RandomUser() {}
}
