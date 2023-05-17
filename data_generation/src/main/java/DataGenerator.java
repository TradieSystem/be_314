import Types.Service.RandomAssociatedService;
import Types.Service.RandomRequest;
import Types.Service.RandomTransaction;
import Types.Service.Service;
import Types.User.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Types.Service.RandomRequestBid;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
        SqlGenerator<RandomUserQuestion> questions = new SqlGenerator<>(RandomUserQuestion.class, randomUserQuestions);
        SqlGenerator<RandomAuthorisation> authorisations = new SqlGenerator<>(RandomAuthorisation.class, randomAuthorisations);
        SqlGenerator<RandomSession> sessions = new SqlGenerator<>(RandomSession.class, randomSessions);
        SqlGenerator<RandomAddress> addresses = new SqlGenerator<>(RandomAddress.class, randomAddresses);
        SqlGenerator<RandomClient> clients = new SqlGenerator<>(RandomClient.class, randomClients);
        SqlGenerator<RandomProfessional> professionals = new SqlGenerator<>(RandomProfessional.class, randomProfessionals);
        SqlGenerator<RandomRequest> requests = new SqlGenerator<>(RandomRequest.class, randomRequests);
        SqlGenerator<RandomRequestBid> requestBids = new SqlGenerator<>(RandomRequestBid.class, randomRequestBids);

        String file = "USE Project;\n";
        file += users.generateScript();
        file += questions.generateScript();
        file += authorisations.generateScript();
        file += sessions.generateScript();
        file += addresses.generateScript();
        file += clients.generateScript();
        file += professionals.generateScript();
        file += requests.generateScript();
        file += requestBids.generateScript();

        file = file.replace("'null'", "null");

        // create sql file
        try {
            FileWriter outFile = new FileWriter("src\\..\\..\\data.sql");
            outFile.write(file);
            outFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            AtomicReference<String> postcode = new AtomicReference<>("");
            //String.valueOf(randomAddresses.stream().filter(address -> address.user_id == user_id)
            //                    .findFirst())
            randomAddresses.forEach(randomAddress -> { if (randomAddress.user_id == user_id) postcode.set(randomAddress.postcode); });

            // pass client_id and calculated postcode and add randomly generated client to ArrayList
            randomRequests.add(RandomRequest.GenerateRequest(client.client_id, postcode.get()));
        }

        // generate request bids for requests
        randomRequests.forEach(request -> {
            // iterate through addresses and find matching users to postcode
            var applicableUsers = new ArrayList<RandomUser>();
            randomAddresses.forEach(address -> {
                if (Objects.equals(address.postcode, request.postcode)) {
                    randomUsers.forEach(user -> { if (user.user_id == address.user_id) applicableUsers.add(user); });
                }
            });

            // check if user is a professional
            var professionals = new ArrayList<RandomProfessional>();
            applicableUsers.forEach(applicableUser -> {
                randomProfessionals.forEach(randomProfessional -> {
                    // find associated professional
                    if (applicableUser.user_id == randomProfessional.user_id) {
                        // find associated services for professional and check against request
                        var associatedServices = new ArrayList<RandomAssociatedService>();
                        randomAssociatedServices.forEach(randomAssociatedService -> {
                            if (randomAssociatedService.professional_id == randomProfessional.professional_id) associatedServices.add(randomAssociatedService);
                        });

                        // if the associated services match request service then add to professional list for request bid generation
                        associatedServices.forEach(associatedService -> {
                            if (associatedService.service_id == request.service_id) professionals.add(randomProfessional);
                        });
                    }
                });
            });

            AtomicBoolean approved = new AtomicBoolean(false);
            professionals.forEach(professional -> {
                // generate request bid
                var request_bid = RandomRequestBid.generateRequestBid(request.request_id, professional.professional_id, approved.get());

                if (request_bid.bid_status_id == 2) approved.set(true);

                randomRequestBids.add(request_bid);

                // change request status based on request bid
                if (request_bid.bid_status_id == 2) request.professional_id = professional.professional_id;
            });
        });
    }

    // based of number of completed requests
    private void GenerateReviews() {

    }
}
