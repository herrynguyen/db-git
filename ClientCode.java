package sight;

import dnl.utils.text.table.TextTable;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.lang.StringUtils;

public class ClientCode {

    public static void main(String args[]) {
        try {
            Socket s = new Socket("localhost", 3000);
            /*Send data from client to server*/
            OutputStream outStream = s.getOutputStream();
            ObjectOutputStream objOutStream = new ObjectOutputStream(outStream);
            FillListSight fillOut = new FillListSight();
            fillOut.input();
            MessageSight msSend = new MessageSight(fillOut.getNameSight(), fillOut.getStartDate(), fillOut.getEndDate());
            objOutStream.writeObject(msSend);
            /*Recivce data from server*/
            InputStream inStream = s.getInputStream();
            ObjectInputStream objInStream = new ObjectInputStream(inStream);
            List<MessageSight> listbird = (List<MessageSight>) objInStream.readObject();

            printListSightings(listbird);
            //showListBirds(listbird);
            /*Close client when finish*/
            inStream.close();
            objInStream.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void showListBirds(List<MessageSight> listsight) {
        for (MessageSight me : listsight) {
            System.out.println();
            System.out.println("Name: " + me.getName());
            System.out.println("Location: " + me.getLocation());
            System.out.println("Date: " + me.getDate());
        }
        if (listsight.size() == 0) {
            System.out.println("\nEmpty data from server!!!");
        } else {
            System.out.println("\nThere are " + listsight.size() + " sighting(s)");
        }

        System.out.println("Name 1: " + listsight.get(1).getName());
        System.out.println("Location 1: " + listsight.get(1).getLocation());
        System.out.println("Date 1: " + listsight.get(1).getDate());
    }

    public static void printListSightings(List<MessageSight> listsight) {
        String[] columnNames = {"Name", "Location", "Date"};
        Object[][] data = new Object[listsight.size()][3];
        for (int i = 0; i < listsight.size(); i++) {
            data[i][0] = listsight.get(i).getName();
            data[i][1] = listsight.get(i).getLocation();
            data[i][2] = listsight.get(i).getDate();
        }
        TextTable tt = new TextTable(columnNames, data);
        tt.setAddRowNumbering(true);
        tt.setSort(0);
        tt.printTable();
    }
}
