import Types.User.*;
import net.datafaker.Faker;
import net.datafaker.transformations.Field;
import net.datafaker.transformations.Schema;
import net.datafaker.transformations.JavaObjectTransformer;
import java.util.List;
import java.util.Objects;

public class DataGenerator {
    // user information
    private List<RandomUser> randomUsers;
    private List<RandomAddress> randomAddresses;
    private List<RandomBilling> randomBillings;
    private List<RandomClient> randomClients;
    private List<RandomProfessional> randomProfessionals;

    public void GenerateData(int numberOfUsers, int numberOfRequests) {
        this.GenerateUsers(10);
    }

    // can remove, but for readability might be good to keep
    private void GenerateUsers(int numberOfUsers) {
        RandomUser user = RandomUser.GenerateUser();
        SqlGenerator<RandomUser> sqlGenerator = new SqlGenerator<RandomUser>(user, "user");
        try {
            System.out.println(sqlGenerator.generateQuery());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void GenerateRequests(int numberOfRequests) {

    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
