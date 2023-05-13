import Types.User.RandomClient;

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
        body.append(String.format("ALTER TABLE %s AUTO_INCREMENT = 1\n\n", table));
        
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
}
