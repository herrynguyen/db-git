package sight;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import static sun.org.mozilla.javascript.internal.ScriptRuntime.name;

public class ServerCode {

    public static void main(String args[]) throws SAXException, ParserConfigurationException, IOException, ClassNotFoundException, ParseException {
        System.out.println("Waiting for a client ...");
        ServerSocket serverSocket = new ServerSocket(3000);
        Socket s = serverSocket.accept();
        /*Recivce data from client*/
        InputStream inStream = s.getInputStream();
        ObjectInputStream objInStream = new ObjectInputStream(inStream);
        MessageSight msRecieveSight = (MessageSight) objInStream.readObject();
        String name = msRecieveSight.getName();
        String mindate = msRecieveSight.getStartDate();
        String maxdate = msRecieveSight.getEndDate();
        /*Send listSightings to client*/
        OutputStream outStream = s.getOutputStream();
        ObjectOutputStream objOutStream = new ObjectOutputStream(outStream);
        objOutStream.writeObject(listSighting(name, mindate, maxdate));
        /*Close server when finish*/
        outStream.close();
        objOutStream.close();
        s.close();
        serverSocket.close();
    }

    public static List listSighting(String inputName, String mindate, String maxdate) throws SAXException, ParserConfigurationException, IOException {

        SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        File xmlFile = new File("C:\\Data\\sightingss.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        List<MessageSight> empList = new ArrayList<MessageSight>();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Sightings");
            //if (nodeList.getLength() > 0) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node nNode = nodeList.item(i);
                    Element eElement = (Element) nNode;
                    String name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    String date = eElement.getElementsByTagName("date").item(0).getTextContent();
                    try {
                        Date datemin = simpleFormat.parse(mindate);
                        Date datemax = simpleFormat.parse(maxdate);
                        Date dateOut = simpleFormat.parse(date);
                        int resuilt1 = datemin.compareTo(dateOut);
                        int resuilt2 = datemax.compareTo(dateOut);
                        if (name.indexOf(inputName) >= 0 && resuilt1 <= 0 && resuilt2 >= 0) {
                            empList.add(getSightings(nodeList.item(i)));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }

        return empList;
    }

    private static MessageSight getSightings(Node node) {
        MessageSight emp = new MessageSight();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            emp.setName(getTagValue("name", element));
            emp.setLocation(getTagValue("location", element));
            emp.setDate(getTagValue("date", element));
        }
        return emp;
    }

    private static String getTagValue(String tag, org.w3c.dom.Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
