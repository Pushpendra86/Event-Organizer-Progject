package Even_Organizer;
import java.sql.*;
import java.util.Scanner;
public class Main {
    //creating variables
    private static final String url = "jdbc:mysql://127.0.0.1:3306/eventOrganizer";
    private static final String userName = "root";
    private static final String password = "Pushpendra@9634";
    public static void main(String[] args) {
       // Loading the drivers
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
           e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        //Creating connection
        try{
            Connection connection = DriverManager.getConnection(url,userName,password); //connection establish
            Event event = new Event(connection,scanner);
            Hotel hotel = new Hotel(connection); //admin will store the details
            while (true){
                System.out.println();
                System.out.println("EVENT ORGANIZER");
                System.out.println("1. Registration for an Event");
                System.out.println("2. Cancel Event");
                System.out.println("3. View Event details");
                System.out.println("4. Reschedule the Event");
                System.out.println("5. View Hotel list");
                System.out.println("6. Book an Event");
                System.out.println("7. Exit");
                System.out.println();
                System.out.println("Enter your choice");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1://Book an event
                        event.createEvent();
                        break;
                    case 2:// cancel an event
                        event.cancelEvent();
                        break;
                    case 3:// view event details
                        event.viewEventDetails();
                        break;
                    case 4:// Reschedule the event
                        event.rescheduleEvent();
                        break;
                    case 5: //View hotel list
                        hotel.viewHotelDetails();
                        break;
                    case 6:// book an event
                        bookEvent(event,hotel,connection,scanner);
                        break;
                    case 7: // exit
                        System.out.println("Thank you for using event Organizer");
                        return;
                    default:
                        System.out.println("Invalid choice !!!");
                        break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Error :" + e.getMessage());
        }
    }
    public static void bookEvent(Event event, Hotel hotel, Connection connection, Scanner scanner){
        //Booking
        System.out.print("Enter event ID :");
        int event_id = scanner.nextInt();
        System.out.print("Enter hotel Location :");
        int hotel_location = scanner.nextInt();
        System.out.print("enter Booking date (YYYY-MM-DD :");
        String booking_date = scanner.next();

        if (event.getEventbyID(event_id) && hotel.getHotelbyID(hotel_location)){
            if (checkHotelAvailability(hotel_location, booking_date, connection)){
                String bookingQuery = "INSERT INTO appointments (event_ID, hotel_ID, booking_date) VALUES(?, ?, ?)";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(bookingQuery);
                    preparedStatement.setInt(1,event_id);
                    preparedStatement.setInt(2, hotel_location);
                    preparedStatement.setString(3, booking_date);
                    int rowsAffected = preparedStatement.executeUpdate(); //Insertion
                    if (rowsAffected > 0){
                        System.out.println("Event Booked");
                    }else{
                        System.out.println("failed to book an Event");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                System.out.println("hotel is not empty on this date");
            }
        }else {
            System.out.println("hotel is not empty");
        }
    }
    public static boolean checkHotelAvailability(int hotel_ID, String booking_date, Connection connection) {
        String query = "SELECT COUNT (*) FROM appointments WHERE hotel_ID = ? AND booking_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, hotel_ID);
            preparedStatement.setString(2, booking_date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count == 0) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
