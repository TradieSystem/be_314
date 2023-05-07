import Types.Service.RandomAssociatedService;
import Types.Service.Service;
import Types.User.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataGenerator {
    // user information
    private ArrayList<RandomUser> randomUsers;
    private ArrayList<RandomAddress> randomAddresses;
    private ArrayList<RandomBilling> randomBillings;
    private ArrayList<RandomClient> randomClients;
    private ArrayList<RandomProfessional> randomProfessionals;
    private ArrayList<RandomAssociatedService> randomAssociatedServices;
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
            RandomUser user = RandomUser.generate();
            randomUsers.add(user);
            // The list of available questions gets smaller for question already assigned to user
            List<SecurityQuestion> sqList = Arrays.asList(SecurityQuestion.values());
            for (int j = 0; j < 3; j++) {
                randomUserQuestions.add(RandomUserQuestion.generate(user, sqList));
            }
            // generate address for each user
            randomAddresses.add(RandomAddress.generate(user.user_id));
            
            // Half the users are professional
            if (i < numberOfUsers / 2) {
                RandomProfessional professional = RandomProfessional.generate(user.user_id);
                randomProfessionals.add(professional);
                // 1/6th of the users professionals and client at same time.
                if (i > numberOfUsers / 3) {
                    randomClients.add(RandomClient.generate(randomUsers.get(i).user_id));
                    randomBillings.add(RandomBilling.generate(user, true));
                }
                
                // Assign 1 to 4 services that the professional provides...
                List<Service> serviceList = Arrays.asList(Service.values());
                int numServices = RandomAssociatedService.getRandomInt(1,4);
                for (int j = 0; j< numServices; j++ ) {
                    randomAssociatedServices.add(RandomAssociatedService.generate(professional, serviceList));
                }
                randomBillings.add(RandomBilling.generate(user, false));
                
            } else { 
                randomClients.add(RandomClient.generate(randomUsers.get(i).user_id));
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
