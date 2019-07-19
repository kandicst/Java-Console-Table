import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;

public class Main {

    public static void main(String[] args) {



        String[] headers = {"Name", "Last Name", "Team", "Salary"};
        Table table = new Table(headers);

        table.addRow(new String[] {"Lebron", "James", "LAL", "$33,285,709"});
        table.addRow(new String[] {"Klay", "Thompson", "GSW", "$18,000,000"});
        table.addRow(new String[] {"Kevin", "Durant", "BRK", "$25,000,000"});
        table.addRow(new String[] {"Giannis", "LongLastNameCantBotherToCopy", "MIL", "$23,500,000"});

        table.deleteRow(3);
        table.show();


    }
}
