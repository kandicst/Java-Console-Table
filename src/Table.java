
import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    /**
     * kod kreiranja kao parametar morate proslediti String[] koji predstavlja zaglavnja tabele
     * i onda metodom addRow dodavati svaki red takodje kao String[]
     *
     * parametar sirina predstavlja koliko ce biti mesta izmedju kraja reci i kraja polja gde se rec nalazi,
     * gde je sirina neparan broj (najbolje je da je bar veci od 2) - default vrednost je 9
     *
     * - moze se setovati metodom setSirina ciji je jedini parametar int veci od 2
     * - ona se mora promeniti pre dodavanja redova inace nece nista promeniti u prikazu tabele
     *
     *
     * default velicina liste sa podacima je 50,ali se ona duplira svaki put kada se do limita stigne
     */

    private String[] headers;
    private int colNum, rowNum;
    private int[] maxDataLength;
    private String[][] data;
    private int width = 11;

    public Table() {
        this.colNum = 0;
        this.rowNum = 0;
        this.data = new String[50][colNum];
        this.maxDataLength = new int[colNum]; //bilo 50
        Arrays.fill(maxDataLength, 0);
        Arrays.fill(data, null);
        Arrays.fill(headers, "");
        fillHeaders();
    }


    public Table(String [] headeri) {
        this.headers = headeri;
        this.colNum = headeri.length;
        this.rowNum = 0;
        this.data = new String[50][colNum];
        this.maxDataLength = new int[colNum]; //bilo 50
        Arrays.fill(maxDataLength, 0);
        Arrays.fill(data, null);
        fillHeaders();
    }


    /**
     * Adds a new row to the contents of the table
     * @param row - row to be added
     */
    public void addRow(String[] row){

        if(data[data.length - 1] != null) {
            //ako se popunio niz sa podacima
            resize();
        }
        if (row.length != this.colNum) {
            System.out.println("Nemoguce je dodati podatke u red jer broj podataka nije isti sa brojem kolona!");
            return;
        }

        for (int i = 0; i < row.length; i++) {

            if(row[i] != null && row[i].length() > maxDataLength[i]) {
                maxDataLength[i] = row[i].length();
                if ((maxDataLength[i] % 2) == 1) {
                    maxDataLength[i] += width;
                }else {
                    maxDataLength[i] += width+1;
                }
            }
        }

        for (int i = 0; i < data.length; i++) {
            if(data[i] == null) {
                data[i] = row;
                rowNum++;
                return;
            }
        }
    }


    /**
     * Deletes a row in a contents of the table
     * @param rowID - index of a row to be deleted (between 0 and rowNum - 1)
     */
    public void deleteRow(int rowID){

        if( rowID > rowNum + 1 ){
            //err
            return;
        }

        String[][] newData = new String[data.length][data[0].length];
        Arrays.fill(newData, null);
        int[] newLength = new int[maxDataLength.length*2];
        Arrays.fill(newLength, 0);

        for(int i = 0; i < data.length; i++ ){

            if(i != rowID && data[i] != null){
                newData[i] = data[i];
                for( int j = 0; j < data[i].length; j++){
                    if( data[i][j].length() > newLength[j]){
                        newLength[j] = data[i][j].length();
                        if ((maxDataLength[i] % 2) == 1) {
                            maxDataLength[i] += width;
                        }else {
                            maxDataLength[i] += width+1;
                        }
                    }
                }
            }
        }

        data = newData;
        maxDataLength = newLength;
        fillHeaders();
    }


    private String printHeaders() {
        int ratio = 0;
        String horizontal = "";
        String vertical = "";
        for (int i = 0; i < headers.length; i++) {
            if((headers[i].length() % 2) == 1) {
                headers[i] += " ";
            }
            ratio = maxDataLength[i] - headers[i].length();
            horizontal += "+" + repeatString("-", maxDataLength[i]);
            vertical += "|" + repeatString(" ", ratio / 2)+headers[i]+repeatString(" ", ratio / 2);
        }
        return horizontal + "+\n" + vertical + "|\n" + horizontal + "+\n";
    }


    private String printData() {
        String ret = "";
        for (int i = 0; i < data.length; i++) {
            if(data[i] != null) {
                for (int j = 0; j < data[i].length; j++) {
                    if(data[i][j] != null) {
                        if((data[i][j].length() % 2) == 1) {
                            data[i][j] += " ";
                        }
                        int ratio = maxDataLength[j] - data[i][j].length();
                        ret += "|" + repeatString(" ", ratio / 2) + data[i][j] + repeatString(" ", ratio / 2);
                    }
                }
                ret += "|\n";
            }
        }
        return ret;
    }


    private String printLastRow() {
        int ratio = 0;
        String ret = "";
        for (int i = 0; i < headers.length; i++) {
            ratio = maxDataLength[i] - headers[i].length();
            ret += "+" + repeatString("-", maxDataLength[i]);
        }
        return ret + "+\n";
    }


    private String printZaglavnje(int duzina,String zaglavlje) {
        if((zaglavlje.length()%2)==1) {
            zaglavlje+=" ";
        }
        int odnos = duzina-zaglavlje.length();
        String header = "+"+repeatString("-", duzina)+"\n";
        header+="|"+repeatString(" ", odnos/2)+zaglavlje+repeatString(" ", odnos/2)+"\n";
        header+="+"+repeatString("-", duzina)+"\n";
        return header;
    }


    /**
     * Adds a string to itself given number of times
     * @param string - string itself
     * @param times - number of times
     * @return - new String
     */
    private String repeatString(String string, int times) {
        return new String(new char[times]).replace("\0", string);
    }


    /**
     * Doubles the size of data array once it reaches its capacity
     */
    private void resize() {

        String[][] newData = new String[data.length*2][colNum];
        Arrays.fill(newData, null);

        System.arraycopy(newData,0,data,0, data.length);
        this.data = newData;
        /**
         for (int i = 0; i < data.length; i++) {
         newData[i] = data[i];
         }
        */


        // vrv ne treba
        //int[] newLength = new int[maxDataLength.length*2];
        //Arrays.fill(newLength, 0);
        //System.arraycopy(newLength,0,maxDataLength,0, maxDataLength.length);
        //this.maxDataLength = newLength;
        //for (int i = 0; i < this.maxDataLength.length; i++) {
          //  newLength[i] = maxDataLength[i];
        //}
    }


    /** */
    private void fillHeaders() {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].length() > maxDataLength[i]) {
                maxDataLength[i] = headers[i].length();
                if ((maxDataLength[i] % 2) == 1) {
                    maxDataLength[i] += width;
                }else {
                    maxDataLength[i] += width+1;
                }
            }
        }
    }


    /**
     * Sets the spacing of the cell related to the item inside
     * @param newWidth - number of characters
     */
    public void setWidth(int newWidth) {
        if (width > 2 && (width % 2) == 1) {
            this.width = newWidth;
        }
        else if(width > 2) {
            this.width = newWidth+1;
        }
    }


    @Override
    public String toString() {
        String pov = printHeaders();
        pov += printData();
        pov += printLastRow();
        return pov;
    }


    public void showWithoutHeaders() {
        String pov = printLastRow();
        pov += printData();
        pov +=printLastRow();
        System.out.println(pov);
    }


    public void show() {
        String pov = printHeaders();
        pov += printData();
        pov += printLastRow();
        System.out.println(pov);
    }

}
