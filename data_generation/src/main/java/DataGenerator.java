import Types.Service.RandomRequest;
import Types.User.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DataGenerator {
    // user information
    private ArrayList<RandomUser> randomUsers;
    private ArrayList<RandomAddress> randomAddresses;
    private ArrayList<RandomBilling> randomBillings;
    private ArrayList<RandomClient> randomClients;
    private ArrayList<RandomProfessional> randomProfessionals;
    private ArrayList<RandomRequest> randomRequests;

    private int numberOfUsers;
    private int numberOfRequests;

    public DataGenerator(int numberOfUsers, int numberOfRequests) {
        this.randomUsers = new ArrayList<>();
        this.randomAddresses = new ArrayList<>();
        this.randomBillings = new ArrayList<>();
        this.randomClients = new ArrayList<>();
        this.randomProfessionals = new ArrayList<>();
        this.randomRequests = new ArrayList<>();
        this.numberOfUsers = numberOfUsers;
        this.numberOfRequests = numberOfRequests;
    }

    public void GenerateData() {

        this.GenerateUsers(numberOfUsers);
        this.GenerateRequests(numberOfRequests);
    }
    // can remove, but for readability might be good to keep
    private void GenerateUsers(int numberOfUsers) {
        // generate random users
        for (int i = 0; i < numberOfUsers; i++) randomUsers.add(RandomUser.GenerateUser());


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
        for (int i = clients; i < randomUsers.size(); i++) randomClients.add(RandomClient.GenerateClient(randomUsers.get(i).user_id));

    }

    private void GenerateRequests(int numberOfRequests) {
        Random random = new Random();  // to randomly choose client from randomClients

        // iterate and generate requests based on set numberOfRequests
        for (int i = 0; i < numberOfRequests; i++) {
            // randomly pick client to associate request to
            RandomClient client = randomClients.get(random.nextInt(randomClients.size() - 1) + 1);

            // find associated random user and match to generated address
            int user_id = randomUsers.get(client.user_id).user_id;
            String postcode = String.valueOf(randomAddresses.stream().filter(address -> address.user_id == user_id)
                    .findFirst());

            // pass client_id and calculated postcode and add randomly generated client to ArrayList
            randomRequests.add(RandomRequest.GenerateRequest(client.client_id, postcode));
        }

        SqlGenerator<RandomRequest> generator = new SqlGenerator<>(randomRequests, RandomRequest.TABLE);
        System.out.println(generator.GenerateScript());

    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
