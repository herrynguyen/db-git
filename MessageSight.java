package sight;
import java.io.Serializable;

public class MessageSight implements Serializable{
private String query;
private String name;
private String location;
private String date;
private String startDate;
private String endDate;

public String getName() {
 return name;
}
public String getLocation() {
 return location;
}
public String getDate() {
 return date;
}
public String getQuery() {
 return query;
}
public void setName(String name ) {
        this.name = name;
    }
public void setLocation(String location ) {
        this.location = location;
    }
public void setDate(String date ) {
        this.date = date;
    }
public void setQuery(String query){
    this.query=query;
}
public MessageSight(){
}
public MessageSight(String name, String startDate, String endDate){
	this.name = name;
	this.startDate = startDate;
	this.endDate = endDate;
}
public String getStartDate() {
	return startDate;
}
public String getEndDate() {
	return endDate;
}
@Override
public String toString() {
        return "Name: " + this.name + "; Location: " + this.location + "; Date: " + this.date;
    }
}