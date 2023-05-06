import javax.swing.*;
import java.lang.reflect.Field;

public class SqlGenerator<T> {
    T instance;
    String table;
    private Field[] fields;

    public SqlGenerator(T instance, String table) {
        this.instance = instance;
        fields = instance.getClass().getFields();
        this.table = table;
    }

    private String generateQuery() throws NoSuchFieldException, IllegalAccessException {


        String values = "";
        for (int i = 0; i <= this.fields.length; i++) {
            if (i + 1 < this.fields.length) {
                values += "'" + this.instance.getClass().getDeclaredField(this.fields[i].toString()).get(this) + ",'";
            }
            else values += "'" + this.instance.getClass().getDeclaredField(this.fields[i].toString()).get(this) + "'";
        }

        return String.format("INSERT INTO %s VALUES (%s)", table);
    }
}
