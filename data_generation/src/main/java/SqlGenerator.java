import Types.Service.*;
import Types.User.*;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class SqlGenerator<T> {
    
    private final ArrayList<T> items;
    private final String table;

    public SqlGenerator(Class<T> clazz, ArrayList<T> items) {
        this.items = new ArrayList<>(items);
        String tempTableName = "";
        try {
            Field f = clazz.getDeclaredField("TABLE");
            tempTableName =(String)f.get(null);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
        }
        this.table = tempTableName;
    }

    public String getTableName() {
        return this.table;
    }
   
    public String getTruncateSql() {
        return String.format("TRUNCATE TABLE %s;\r\n", table); 
    }
    
    public String generateScript() {
        final StringBuilder body = new StringBuilder();
        body.append(String.format("\nALTER TABLE %s AUTO_INCREMENT = 1;\n\n", table));
        
        for (int i = 0; i < this.items.size(); i++) {
            try {
                body.append(String.format("%s;\n",this.generateQuery(this.items.get(i))));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return body.toString();
    }

    private String generateQuery(T instance) throws NoSuchFieldException, IllegalAccessException {
        final StringBuilder columns = new StringBuilder(); // string to hold column names
        final StringBuilder values = new StringBuilder(); // string to hold values

        final Field[] fields = instance.getClass().getDeclaredFields();

        // iterate through number of class fields and skip static fields
        for (int i = 0; i < fields.length; i++) {
            if (Modifier.isStatic(fields[i].getModifiers())) continue; // skip if static field
            // if it is the last field do not add ','
            if (i + 1 < fields.length) {
                columns.append(String.format("%s,", fields[i].getName())); // adding the column name to columns string
                values.append("'").append(String.valueOf(instance
                        .getClass()
                        .getDeclaredField(fields[i].getName())
                        .get(instance))).append("',");
            }
            else {
                columns.append(String.format("%s", fields[i].getName()));
                values.append("'").append(String.valueOf(instance
                        .getClass()
                        .getDeclaredField(fields[i].getName())
                        .get(instance))).append("'");
            }
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns, values);
    }

    public static void CreateQuery(DataGenerator dataGenerator) {
        SqlGenerator<RandomUser> users = new SqlGenerator<>(RandomUser.class, dataGenerator.getRandomUsers());
        SqlGenerator<RandomBilling> billings = new SqlGenerator<>(RandomBilling.class, dataGenerator.getRandomBillings());
        SqlGenerator<RandomUserQuestion> questions = new SqlGenerator<>(RandomUserQuestion.class, dataGenerator.getRandomUserQuestions());
        SqlGenerator<RandomAssociatedService> associatedServices = new SqlGenerator<>(RandomAssociatedService.class, dataGenerator.getRandomAssociatedServices());
        SqlGenerator<RandomAuthorisation> authorisations = new SqlGenerator<>(RandomAuthorisation.class, dataGenerator.getRandomAuthorisations());
        SqlGenerator<RandomSession> sessions = new SqlGenerator<>(RandomSession.class, dataGenerator.getRandomSessions());
        SqlGenerator<RandomAddress> addresses = new SqlGenerator<>(RandomAddress.class, dataGenerator.getRandomAddresses());
        SqlGenerator<RandomClient> clients = new SqlGenerator<>(RandomClient.class, dataGenerator.getRandomClients());
        SqlGenerator<RandomProfessional> professionals = new SqlGenerator<>(RandomProfessional.class, dataGenerator.getRandomProfessionals());
        SqlGenerator<RandomRequest> requests = new SqlGenerator<>(RandomRequest.class, dataGenerator.getRandomRequests());
        SqlGenerator<RandomRequestBid> requestBids = new SqlGenerator<>(RandomRequestBid.class, dataGenerator.getRandomRequestBids());
        SqlGenerator<RandomReview> reviews = new SqlGenerator<>(RandomReview.class, dataGenerator.getRandomReviews());
        SqlGenerator<RandomTransaction> transactions = new SqlGenerator<>(RandomTransaction.class, dataGenerator.getRandomTransactions());

        String file = "USE Project;\n";
        file += users.generateScript();
        file += billings.generateScript();
        file += questions.generateScript();
        file += authorisations.generateScript();
        file += sessions.generateScript();
        file += addresses.generateScript();
        file += clients.generateScript();
        file += professionals.generateScript();
        file += associatedServices.generateScript();
        file += requests.generateScript();
        file += requestBids.generateScript();
        file += reviews.generateScript();
        file += transactions.generateScript();

        // replace 'null' with null
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
}
