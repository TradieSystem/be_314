import Types.Service.RandomAssociatedService_2;
import Types.Service.Service;
import Types.User.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class DataGenerator {
    // user information
    private ArrayList<RandomUser> randomUsers;
    private ArrayList<RandomAddress> randomAddresses;
    private ArrayList<RandomBilling> randomBillings;
    private ArrayList<RandomClient> randomClients;
    private ArrayList<RandomProfessional> randomProfessionals;
    private ArrayList<RandomAssociatedService_2> randomAssociatedServices;
    private ArrayList<RandomUserQuestion> randomUserQuestions;

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
        this.randomUserQuestions = new ArrayList<>();
    }

    public void GenerateData() {
        this.GenerateUsers(numberOfUsers);
    }

    // can remove, but for readability might be good to keep
    private void GenerateUsers(int numberOfUsers) {
        // generate random users
        for (int i = 0; i < numberOfUsers; i++) {
            RandomUser user = RandomUser.GenerateUser();
            randomUsers.add(user);
            // The list of available questions gets smaller for question already assigned to user
            List<SecurityQuestion> sqList = Arrays.asList(SecurityQuestion.values());
            for (int j = 0; j < 3; j++) {
                randomUserQuestions.add(RandomUserQuestion.generate(user, sqList));
            }
            // generate address for each user
            randomAddresses.add(RandomAddress.GenerateAddress(user.user_id));
            
            // Half the users are professional
            if (i < numberOfUsers / 2) {
                RandomProfessional professional = RandomProfessional.GenerateProfessional(user.user_id);
                // 1/6th of the users professionals and client at same time.
                if (i > numberOfUsers / 3) {
                    randomClients.add(RandomClient.GenerateClient(randomUsers.get(i).user_id));
                    randomBillings.add(RandomBilling.generate(user, true));
                }
                
                // Assign 1 to 4 services that the professional provides...
                List<Service> serviceList = Arrays.asList(Service.values());
                int numServices = RandomAssociatedService_2.getRandomInt(1,4);
                for (int j = 0; j< numServices; j++ ) {
                    RandomAssociatedService_2.generate(professional, serviceList);
                }
                randomBillings.add(RandomBilling.generate(user, false));
                
            } else { 
                randomClients.add(RandomClient.GenerateClient(randomUsers.get(i).user_id));
                randomBillings.add(RandomBilling.generate(user, true));
            }
        }

    }

    private void GenerateRequests(int numberOfRequests) {

    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
