package sight;

import dnl.utils.text.table.TextTable;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static sun.text.normalizer.UTF16.charAt;

public class FillListSight {

    private String nameSight;
    private String startDate;
    private String endDate;
    private Date today = new Date();

    public void input() throws ParseException {
        String name;
        String start;
        String end;
        Scanner scanner = new Scanner(System.in);
        //Regular Expressions
        String specialCharacters = "[a-zA-Z0-9 ]+$";
        OutLoop:
        while (true) {
            System.out.print("Name bird: ");
            name = scanner.nextLine();
            char whiteplace = ' ';
            char firstChar = name.charAt(0);
            if (name.equals("")) {  //Check empty name
                System.out.println("Warning! Name can not be empty");
            } else if (firstChar == whiteplace) {   //Check whiteplace in first characters
                System.out.println("Warning! Firt character can not be whiteplace");
            } else if (name.matches(specialCharacters)) {    //Check Special characters
                nameSight = name;
                break OutLoop;
            } else {
                System.out.println("Warning! Name can not contain special characters");
            }
        }
        OutLoop:
        while (true) {
            System.out.print("Start Date: ");
            start = scanner.nextLine();
            if (isValidDate(start)) {//Check dateTime format
                String dateFormat = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
                Date date = simpleFormat.parse(start);
                if (date.compareTo(today) <= 0) {
                    startDate = start;
                    break OutLoop;
                } else {
                    System.out.println("Warning! Start date can not more than the current date");
                }
            } else {
                System.out.println("Warning! DateTime Format is dd/MM/yyyy hh:mm:ss");
            }
        }
        OutLoop:
        while (true) {
            System.out.print("End Date: ");
            end = scanner.nextLine();
            if (isValidDate(end)) {//Check dateTime format
                String dateFormat = "dd/MM/yyyy HH:mm:ss";
                SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
                Date date = simpleFormat.parse(end);
                if (date.compareTo(today) <= 0) {
                    endDate = end;
                    break OutLoop;
                } else {
                    System.out.println("Warning! End date can not more than the current date");
                }
            } else {
                System.out.println("Warning! DateTime Format is dd/MM/yyyy hh:mm:ss");
            }
        }
    }

    //Method check input dateTime format
    public boolean isValidDate(String dateToValiDate) {
        String dateFormat = "dd/MM/yyyy HH:mm:ss";
        if (dateToValiDate.equals("")) {
            return false;
        }
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateFormat);
        simpleFormat.setLenient(false);
        try {
            Date date = simpleFormat.parse(dateToValiDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public String getNameSight() {
        return nameSight;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    //Method print listsighting infomation
    public void showListBirds(List<MessageSight> listsight) {
        for (MessageSight me : listsight) {
            System.out.println();
            System.out.println("Name: " + me.getName());
            System.out.println("Location: " + me.getLocation());
            System.out.println("Date: " + me.getDate());
        }
        System.out.println("\nThere are " + listsight.size() + " sighting(s)");
    }
    
    public void printListSightings(List<MessageSight> listsight) {
        String[] columnNames = {"Name","Location","Date"};
        Object[][] data = {                                            
            {"Kathy", "Smith", "Snowboarding"},  
            {"John", "Doe", "Rowing"},         
            {"Sue", "Black", "Knitting"},
            {"Jane", "White","Speed reading"}, 
            {"Joe", "Brown","Pool"}
        };
        for (MessageSight me : listsight) {
           TextTable tt = new TextTable(columnNames, data);  
           tt.setAddRowNumbering(true);
           tt.setSort(0);
           tt.printTable(); 
        }
        System.out.println("\nThere are " + listsight.size() + " sighting(s)");
    }
}
