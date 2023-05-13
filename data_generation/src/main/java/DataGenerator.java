import Types.Service.RandomAssociatedService;
import Types.Service.RandomRequest;
import Types.Service.RandomTransaction;
import Types.Service.Service;
import Types.User.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import Types.Service.RandomRequest;
import Types.Service.RandomRequestBid;
import Types.User.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

public class DataGenerator {
    // user information
    private final ArrayList<RandomUser> randomUsers;
    private final ArrayList<RandomAddress> randomAddresses;
    private final ArrayList<RandomBilling> randomBillings;
    private final ArrayList<RandomClient> randomClients;
    private final ArrayList<RandomProfessional> randomProfessionals;
    private final ArrayList<RandomAssociatedService> randomAssociatedServices;
    private ArrayList<RandomUserQuestion> randomUserQuestions;
    private final ArrayList<RandomAuthorisation> randomAuthorisations;
    private final ArrayList<RandomSession> randomSessions;
    private final ArrayList<RandomTransaction> randomTransactions;
    private final ArrayList<RandomRequest> randomRequests;
    private final ArrayList<RandomRequestBid> randomRequestBids;
    private int numberOfUsers;
    private int numberOfRequests;

    public DataGenerator(int numberOfUsers, int numberOfRequests) {
        this.randomUsers = new ArrayList<>();
        this.randomAddresses = new ArrayList<>();
        this.randomBillings = new ArrayList<>();
        this.randomClients = new ArrayList<>();
        this.randomProfessionals = new ArrayList<>();
        this.randomUserQuestions = new ArrayList<>();        
        this.randomAssociatedServices = new ArrayList<>();
        this.randomAuthorisations = new ArrayList<>();
        this.randomUserQuestions = new ArrayList<>();
        this.randomSessions= new ArrayList<>();
        this.randomTransactions= new ArrayList<>();
        this.randomRequests = new ArrayList<>();
        this.randomRequestBids = new ArrayList<>();
        this.numberOfUsers = numberOfUsers;
        this.numberOfRequests = numberOfRequests;
    }

    public void GenerateData() {
        this.GenerateUsers(numberOfUsers);
        this.GenerateRequests(numberOfRequests);

        SqlGenerator<RandomUser> users = new SqlGenerator<>(RandomUser.class, randomUsers);
        SqlGenerator<RandomClient> clients = new SqlGenerator<>(RandomClient.class, randomClients);
        SqlGenerator<RandomProfessional> professionals = new SqlGenerator<>(RandomProfessional.class, randomProfessionals);
        SqlGenerator<RandomRequest> requests = new SqlGenerator<>(RandomRequest.class, randomRequests);
        System.out.println(users.generateScript());
        System.out.println();
        System.out.println(clients.generateScript());
        System.out.println();
        System.out.println(professionals.generateScript());
        System.out.println();
        System.out.println(requests.generateScript());
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
            
            int numAuth = RandomAssociatedService.getRandomInt(1, 4); 
            for (int j=0; j<numAuth;j++) {
                 
                RandomAuthorisation auth = RandomAuthorisation.generate(user);
                if (j + 1 < numAuth) {
                    auth.invalidated = "Y";
                }                
                randomAuthorisations.add(auth);
                int numSessions = RandomAssociatedService.getRandomInt(1, 4);
                for (int k=0; k < numSessions;k++) {
                    boolean current = (j + 1 == numAuth &&  k + 1  == numSessions);
                    randomSessions.add(RandomSession.generate(auth, current));
                }
            }
        }
    }

    private void GenerateRequests(int numberOfRequests) {
        Random random = new Random();  // to randomly choose client from randomClients

        // iterate and generate requests based on set numberOfRequests
        for (int i = 0; i < numberOfRequests; i++) {
            // randomly pick client to associate request to
            RandomClient client = randomClients.get(random.nextInt(randomClients.size() - 1) + 1);

            // find associated random user and match to generated address
            int user_id = randomUsers.get(client.user_id - 1).user_id;
            String postcode = String.valueOf(randomAddresses.stream().filter(address -> address.user_id == user_id)
                    .findFirst());

            // pass client_id and calculated postcode and add randomly generated client to ArrayList
            randomRequests.add(RandomRequest.GenerateRequest(client.client_id, postcode));
        }

        // assign generate requestBids based on request status
        randomRequests.forEach(request -> {
            if (request.request_status_id <= 2) {
                randomProfessionals.forEach(professional -> {
                    randomAssociatedServices.forEach(associatedService -> {
                        if (associatedService.service_id == request.service_id) {
                            randomRequestBids.add(RandomRequestBid.generateRequestBid(request.request_id, professional.professional_id));
                        }
                    });
                });
            }
        });
    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
