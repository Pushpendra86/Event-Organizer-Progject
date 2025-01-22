package Even_Organizer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Hotel {
    private Connection connection;
    //Constructor
    public Hotel(Connection connection) {
        this.connection = connection;
    }
    //Method
    public void viewHotelDetails(){
        String query = "Select * from hotel";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query); //takes query as an argument
            ResultSet resultSet = preparedStatement.executeQuery(); // for read operation and holding the table
            System.out.println("Hotel details");
            System.out.println("+------------+------------------+------------------------+");
            System.out.println("| Hotel ID |     Hotel name     |     Hotel Location     |");
            System.out.println("+------------+------------------+------------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String Hotel_Name = resultSet.getString("HotelName");
                String location = resultSet.getString("HotelLocation");
                System.out.printf("|%-10s|%-20s%-24s|\n",id,Hotel_Name, location);
                System.out.println("+------------+------------------+------------------------+");
            }
        }
        catch (SQLException e){
            e.getMessage();
        }
    }
    //Method
    public boolean getHotelbyID(int id){
        String query = "SELECT * FROM hotel WHERE id = ?";
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
}