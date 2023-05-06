import Types.User.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataGenerator {
    // user information
    private ArrayList<RandomUser> randomUsers;
    private ArrayList<RandomAddress> randomAddresses;
    private ArrayList<RandomBilling> randomBillings;
    private ArrayList<RandomClient> randomClients;
    private ArrayList<RandomProfessional> randomProfessionals;

    private int numberOfUsers;
    private int numberOfRequests;

    public DataGenerator(int numberOfUsers, int numberOfRequests) {
        this.randomUsers = new ArrayList<>();
        this.randomAddresses = new ArrayList<>();
        this.randomBillings = new ArrayList<>();
        this.randomClients = new ArrayList<>();
        this.randomProfessionals = new ArrayList<>();
        this.numberOfUsers = numberOfUsers;
        this.numberOfRequests = numberOfRequests;

    }

    public void GenerateData() {
        this.GenerateUsers(numberOfUsers);
        SqlGenerator<RandomUser> sqlGenerator = new SqlGenerator<RandomUser>(randomUsers, RandomUser.TABLE);
        System.out.println(sqlGenerator.GenerateScript());
    }

    // can remove, but for readability might be good to keep
    private void GenerateUsers(int numberOfUsers) {
        // generate random users
        for (int i = 0; i < numberOfUsers; i++) {
            randomUsers.add(RandomUser.GenerateUser());
        }
    }

    private void GenerateRequests(int numberOfRequests) {

    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
