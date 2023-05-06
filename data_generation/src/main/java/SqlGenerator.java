import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SqlGenerator<T> {
    T instance;
    String table;
    private Field[] fields;

    public SqlGenerator(T instance, String table) {
        this.instance = instance;
        this.fields = instance.getClass().getDeclaredFields();
        this.table = table;
    }

    public String generateQuery() throws NoSuchFieldException, IllegalAccessException {
        String fields = "";
        String values = "";
        for (int i = 0; i < this.fields.length; i++) {
            if (Modifier.isStatic(this.fields[i].getModifiers())) continue; // skip if static field

            // if it is the last field do not add ','
            if (i + 1 < this.fields.length) {
                fields += String.format("%s,", this.fields[i].getName());
                values += "'" + String.valueOf(this.instance.getClass().getDeclaredField(this.fields[i].getName()).get(instance)) + "',";
            }
            else {
                fields += String.format("%s", this.fields[i].getName());
                values += "'" + String.valueOf(this.instance.getClass().getDeclaredField(this.fields[i].getName()).get(instance)) + "'";
            }
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s)", table, fields, values);
    }
}
