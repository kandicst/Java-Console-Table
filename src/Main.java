public class Main {

    public static void main(String[] args) {
        String [] header = {"First Name", "Last Name", "Age"};
        Table tabela = new Table(header);

        String[] row1 = {"John", "Wick", "21"};
        String[] row2 = {"Rick", "Fox", "31"};
        String[] row3 = {"Dick", "Roxx", "33"};
        tabela.addRow(row1); tabela.addRow(row2); tabela.addRow(row3);

        tabela.deleteRow(1);

        tabela.show();
    }
}
