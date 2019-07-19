
import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    /**
     *
     */
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

    private String[] headers;       // headers of the table
    private int colNum, rowNum;     // number of rows and columns in the table (number of columns can't be changed!)
    private int[] maxDataLength;    // maximum lengths for a cell in a each row of the table (determines widths for all cells in that row)
    private String[][] data;        // matrix containing all data (excluding headers)
    private int spacing = 11;       // spacing between a cell value and the beginning and the end of that cell
    private boolean isHeaders;      // does the table have headers or not


    /**
     * Constructor for header-less table
     */
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


    /**
     * Constructor for table with headers
     * @param headers_ - arrays of Strings representing table headers
     */
    public Table(String [] headers_) {
        this.headers = headers_;
        this.colNum = headers_.length;
        this.rowNum = 0;
        this.data = new String[50][colNum];
        this.maxDataLength = new int[colNum]; //bilo 50
        isHeaders = true;
        Arrays.fill(maxDataLength, 0);
        Arrays.fill(data, null);
        fillHeaders();
    }


    /**
     * Adds a new row to the contents of the table
     * @param row - row to be added
     */
    public void addRow(String[] row){

        // if array is full we will double its size and then insert a row
        if(data[data.length - 1] != null) {
            resize();
        }

        //incorrect number of items to be inserted
        if (row.length != this.colNum) {
            System.out.println("Nemoguce je dodati podatke u red jer broj podataka nije isti sa brojem kolona!");
            return;
        }

        //update max column values
        for (int i = 0; i < row.length; i++) {
            if(row[i] != null && row[i].length() > maxDataLength[i]) {
                changeMaxLength(maxDataLength, i, row[i].length());
            }
        }

        //write into first non null element in data array
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
                // reseting the max column values
                for( int j = 0; j < data[i].length; j++){
                    if( data[i][j].length() > newLength[j]){
                        changeMaxLength(newLength, j, newLength[j]);
                    }
                }
            }
        }

        data = newData;
        maxDataLength = newLength;
        fillHeaders();
    }


    /**
     * Updates maxDataLength with the
     * header values
     */
    private void fillHeaders() {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].length() > maxDataLength[i]) {
                changeMaxLength(maxDataLength, i, headers[i].length());
            }
        }
    }


    /**
     * Changes maximum length of a cell in an entire
     * column of the table
     * @param array - array which holds maximum lengths
     * @param index - index of column that needs to be changed
     * @param newLength - new maximum value
     */
    private void changeMaxLength(int[] array, int index, int newLength){

        array[index] = newLength;
        // value must always be even to ensure centering of text
        if((array[index] % 2) == 1 )
            array[index] += spacing;
        else
            array[index] += spacing + 1;
    }


    /**
     * Returns the string containing
     * header row of the table
     */
    private String getHeaders() {
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


    /**
     * Returns the string containing all data
     * rows from the table
     */
    private String getData() {
        String ret = "";
        for (int i = 0; i < data.length; i++) {
            if(data[i] != null) {
                for (int j = 0; j < data[i].length; j++) {
                    if(data[i][j] != null) {
                        // if size is odd add a space to make it even
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
        return ret + getLastRow();
    }


    /**
     * Returns the string containing
     * the last horizontal line in the table
     */
    private String getLastRow() {
        String ret = "";
        for (int i = 0; i < headers.length; i++) {
            ret += "+" + repeatString("-", maxDataLength[i]);
        }
        return ret + "+\n";
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



    /**
     * Sets the spacing of the cell related to the item inside
     * @param newWidth - number of characters
     */
    public void setWidth(int newWidth) {
        if (spacing > 2 && (spacing % 2) == 1) {
            this.spacing = newWidth;
        }
        else if(spacing > 2) {
            this.spacing = newWidth+1;
        }
    }


    @Override
    public String toString() {
        String ret;
        if(isHeaders)
            ret = getHeaders();
        else
            ret = getLastRow();
        ret += getData();
        return ret;
    }


    /**
     * Displays the table without headers to the console output
     */
    public void showWithoutHeaders() {
        String ret = getLastRow();
        ret += getData();
        System.out.println(ret);
    }


    /**
     * Displays the table to the console output
     */
    public void show() {

        //if the table has headers
        if(isHeaders){
            String ret = getHeaders();
            ret += getData();
            System.out.println(ret);
        }
        else
            showWithoutHeaders();
    }

}
