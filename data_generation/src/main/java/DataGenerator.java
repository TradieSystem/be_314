import Types.User.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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
    }

    // can remove, but for readability might be good to keep
    private void GenerateUsers(int numberOfUsers) {
        // generate random users
        for (int i = 0; i < numberOfUsers; i++) {
            randomUsers.add(RandomUser.GenerateUser());
        }

        // generate address for each user
        for (int i = 0; i < randomUsers.size(); i++) {
            // address generation goes here
        }

        // separate number of users into client and professionals: 25% of users will be professionals, rest clients
        int professionals = (int) (randomUsers.size() * 0.25);
        int clients = randomUsers.size() - professionals;

        // generate professional data
        for (int i = 0; i < professionals; i++) {
            // professional generation goes here
        }

        // generate client data
        for (int i = clients; i < randomUsers.size(); i++) {
            randomClients.add(RandomClient.GenerateClient(randomUsers.get(i).user_id));
        }
    }

    private void GenerateRequests(int numberOfRequests) {

    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
