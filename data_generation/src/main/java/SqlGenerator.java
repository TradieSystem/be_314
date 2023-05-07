import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class SqlGenerator<T> {
    ArrayList<T> items;
    String table;

    public SqlGenerator(ArrayList<T> items, String table) {
        this.items = items;
        this.table = table;
    }

    public String GenerateScript() {
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < this.items.size(); i++) {
            try {
                body.append(String.format("%s;\n",this.generateQuery(this.items.get(i))));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return body.toString();
    }

    public String generateQuery(T instance) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder columns = new StringBuilder(); // string to hold column names
        StringBuilder values = new StringBuilder(); // string to hold values

        Field[] fields = instance.getClass().getDeclaredFields();

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
