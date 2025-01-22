package Even_Organizer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Event {
    private Connection connection;
    Scanner scanner;
    //Constructor
    public Event(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }
    //Method for booking
    public void createEvent(){
        System.out.println("Please tell us name of the Event : ");
        String eventName = scanner.next();
        System.out.println("What will be date for the Event : ");
        String eventDate = scanner.next();
        System.out.println("What would be the location for the Event : ");
        String eventLocation = scanner.next();
        System.out.println("Please tell us name of the Organizer : ");
        String eventOrganizerName = scanner.next();
        //Executing query using statement interface
        try{
            String query = "INSERT INTO event(eventName, eventDate, eventLocation, eventOrganizerName)values(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,eventName);
            preparedStatement.setString(2,eventDate);
            preparedStatement.setString(3,eventLocation);
            preparedStatement.setString(4,eventOrganizerName);
            int affectedRows = preparedStatement.executeUpdate(); //data insertion
            if(affectedRows >0){
                System.out.println("Event booking successfully");
            }else{
                System.out.println("Failed to Book Event");
            }
        }
        catch (SQLException e){
            e.getMessage();
        }
    }
    //Method
    public void viewEventDetails(){
        String query = "SELECT * FROM event";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query); //takes query as an argument
            ResultSet resultSet = preparedStatement.executeQuery(); // for read operation and holding the table
            System.out.println("Event details");
            System.out.println("+--------+--------------+---------------+-------------------+----------------------------------+");
            System.out.println("|   id   |     event Name     |   event Date   |   event Location   |   event Organizer Name   |");
            System.out.println("+--------+--------------+---------------+-------------------+----------------------------------+");

            while (resultSet.next()){
                int EventId = resultSet.getInt("id");
                String EventName = resultSet.getString("eventName");
                String eventDate = resultSet.getString("eventDate");
                String eventLocation = resultSet.getString("eventLocation");
                String organizerName = resultSet.getString("eventOrganizerName");
                System.out.printf("| %-6s | %-18s | %-14s | %-18s | %-24s |\n",EventId, EventName,eventDate,eventLocation,organizerName);
                System.out.println("+--------+--------------+---------------+-------------------+----------------------------------+");

            }
        }
        catch (SQLException e){
            e.getMessage();
        }
    }
    //Method
    public boolean getEventbyID(int id){
        String query = "SELECT * FROM event WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery(); //for data reading
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
                }
            }catch (SQLException e){
        e.printStackTrace();
    }
        return false;
    }
    public void cancelEvent(){
        System.out.print("Please Enter the event ID : ");
        String cancel_event = scanner.next();
        String query ="DELETE FROM event WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cancel_event);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0) {
                System.out.println("Event canceled successfully");
            }else{
                System.out.println("can not cancel the event");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void rescheduleEvent(){
        while (true){
        System.out.println("What do you want to update");
        System.out.println("1. Update eventName :");
        System.out.println("2. update eventDate :");
        System.out.println("3. Update eventLocation :");
        System.out.println("4. update Organizer name :");
        System.out.println("5. Exit");
        int input = scanner.nextInt();
        switch (input) {
            case 1://Update event Name
                System.out.print("Please Enter the event Name: ");
                String name = scanner.next();
                String updateName = "UPDATE event SET eventName = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateName);
                    preparedStatement.setString(1, name);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Name updated successfully");
                    } else {
                        System.out.println("Something went wrong !");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2://Update event Date
                System.out.print("Please Enter the event Date: ");
                String date = scanner.next();
                String updateDate = "UPDATE event SET eventDate = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateDate);
                    preparedStatement.setString(1, date);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Date updated successfully");
                    } else {
                        System.out.println("Something went wrong !");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3://Update event Location
                System.out.print("Please Enter the event Location: ");
                String location = scanner.next();
                String updateLocation = "UPDATE event SET eventLocation = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateLocation);
                    preparedStatement.setString(1, location);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Location updated successfully");
                    } else {
                        System.out.println("Something went wrong !");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4://Update organizer name
                System.out.print("Please Enter the event Organizer name: ");
                String OrganizerName = scanner.next();
                String updateOrganizer = "UPDATE event SET eventOrganizerName = ?";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(updateOrganizer);
                    preparedStatement.setString(1, updateOrganizer);
                    int rowAffected = preparedStatement.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("Organizer name updated successfully");
                    } else {
                        System.out.println("Something went wrong !");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 5: // Exit
                return;
            default:
                System.out.println("Invalid entry");
        }
        }

    }

}
