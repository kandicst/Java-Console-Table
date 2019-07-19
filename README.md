# Java Console Table - Simple and clean way to display data in a tabular form

[![license](https://img.shields.io/github/license/ssttefann/Java-Console-Table.svg)]()

This is a Java class that makes it easy to build console tables, which can be printed as a single String.

The main features are:
- adding headers 
- adding new rows
- deleting already added rows
- cells changing size dynamically
- extracting data from a row or a column



# Building a Table


```java
String[] headers = {"Name", "Last Name", "Team", "Salary"};
Table table = new Table(headers);

table.addRow(new String[] {"Lebron", "James", "LAL", "$33,285,709"});  
table.addRow(new String[] {"Klay", "Thompson", "GSW", "$18,000,000"});  
table.addRow(new String[] {"Kevin", "Durant", "BRK", "$25,000,000"});
table.addRow(new String[] {"Giannis", "LongLastNameCantBotherToCopy", "MIL", "$23,500,000"});

table.show();
```

After executing the given code we get:
```
+----------------+----------------------------------------+----------------+------------------+
|      Name      |               Last Name                |      Team      |      Salary      |
+----------------+----------------------------------------+----------------+------------------+
|     Lebron     |                 James                  |      LAL       |   $33,285,709    |
|      Klay      |                Thompson                |      GSW       |   $18,000,000    |
|     Kevin      |                 Durant                 |      BRK       |   $25,000,000    |
|    Giannis     |      LongLastNameCantBotherToCopy      |      MIL       |   $23,500,000    |
+----------------+----------------------------------------+----------------+------------------+

```

## Deleting a row

```java
table.deleteRow(3);
table.show();
```
```
+------------------+----------------+--------------+----------------------+
|       Name       |   Last Name    |     Team     |        Salary        |
+------------------+----------------+--------------+----------------------+
|      Lebron      |     James      |     LAL      |     $33,285,709      |
|       Klay       |    Thompson    |     GSW      |     $18,000,000      |
|      Kevin       |     Durant     |     BRK      |     $25,000,000      |
+------------------+----------------+--------------+----------------------+
```

## Displaying without headers


```java
table.showWithoutHeaders();
```
```
+------------------+----------------+--------------+----------------------+
|      Lebron      |     James      |     LAL      |     $33,285,709      |
|       Klay       |    Thompson    |     GSW      |     $18,000,000      |
|      Kevin       |     Durant     |     BRK      |     $25,000,000      |
+------------------+----------------+--------------+----------------------+
```
