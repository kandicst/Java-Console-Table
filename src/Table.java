
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
        this.maxDataLength = new int[50];
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
        this.maxDataLength = new int[50];
        Arrays.fill(maxDataLength, 0);
        Arrays.fill(data, null);
        fillHeaders();
    }

    public void addRow(String[] podatak){
        if(data[data.length-1]!=null) {
            //ako se popunio niz sa podacima
            duplirajVelicinu();
        }
        if (podatak.length!=this.colNum) {
            System.out.println("Nemoguce je dodati podatke u red jer broj podataka nije isti sa brojem kolona!");
            return;
        }

        for (int i = 0; i < podatak.length; i++) {

            if(podatak[i]!= null && podatak[i].length()>maxDataLength[i]) {
                maxDataLength[i] = podatak[i].length();
                if ((maxDataLength[i]%2)==1) {
                    maxDataLength[i]+=width;
                }else {
                    maxDataLength[i]+=width+1;
                }
            }
        }

        for (int i = 0; i < data.length; i++) {
            if(data[i]==null) {
                data[i] = podatak;
                rowNum++;
                return;
            }
        }
    }


    public void deleteRow(int rowID){
        //TODO
        if( rowID > rowNum ){

        }
    }

    @Override
    public String toString() {
        String pov = printHeaders();
        pov += printData();
        pov += printLastRow();
        return pov;
    }

    private String printHeaders() {
        int odnos = 0;
        String prvi = "";
        String drugi = "";
        for (int i = 0; i < headers.length; i++) {
            if((headers[i].length()%2)==1) {
                headers[i]+=" ";
            }
            odnos = maxDataLength[i] - headers[i].length();
            prvi += "+"+repeatString("-", maxDataLength[i]);
            drugi+="|"+repeatString(" ", odnos/2)+headers[i]+repeatString(" ", odnos/2);
        }
        return prvi+"+\n"+drugi+"|\n"+prvi+"+\n";
    }



    private String printData() {
        String prvi = "";
        for (int i = 0; i < data.length; i++) {
            if(data[i]!=null) {
                for (int j = 0; j < data[i].length; j++) {
                    if(data[i][j]!=null) {
                        if((data[i][j].length()%2)==1) {
                            data[i][j]+=" ";
                        }
                        int odnos = maxDataLength[j] - data[i][j].length();
                        prvi+="|"+repeatString(" ", odnos/2)+data[i][j]+repeatString(" ", odnos/2);
                    }
                }
                prvi+="|\n";
            }
        }
        return prvi;
    }

    private String printLastRow() {
        int odnos = 0;
        String prvi = "";
        for (int i = 0; i < headers.length; i++) {
            odnos = maxDataLength[i] - headers[i].length();
            prvi += "+"+repeatString("-", maxDataLength[i]);
        }
        return prvi+"+\n";
    }

    public void showWithoutHeaders() {
        String pov = printData();
        pov+=printLastRow();
        System.out.println(pov);
    }


    public void show() {
        String pov = printHeaders();
        pov += printData();
        pov += printData();
        System.out.println(pov);
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


    private String repeatString(String string, int times) {
        return new String(new char[times]).replace("\0", string);
    }

    private void duplirajVelicinu() {

        String newData[][] = new String[data.length*2][colNum];

        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        this.data = newData;

        int newLength[] = new int[maxDataLength.length*2];
        for (int i = 0; i < this.maxDataLength.length; i++) {
            newLength[i] = maxDataLength[i];
        }
        this.maxDataLength = newLength;
    }


    public void setWidth(int newWidth) {
        if (width > 2 && (width % 2) == 1) {
            this.width = newWidth;
        }
        else if(width > 2) {
            this.width = newWidth+1;
        }
    }

    private void fillHeaders() {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].length()> maxDataLength[i]) {
                maxDataLength[i] = headers[i].length();
                if ((maxDataLength[i]%2)==1) {
                    maxDataLength[i]+=width;
                }else {
                    maxDataLength[i]+=width+1;
                }
            }
        }
    }



}
