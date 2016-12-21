package cs601.socketServer.databaseHandlers;

import cs601.socketServer.basicLibraries.databaseBasic.SQLBaseHandler;
import cs601.socketServer.basicLibraries.databaseBasic.Status;
import cs601.socketServer.basicLibraries.fileReaderBasic.ConfigInfo;
import cs601.socketServer.models.AddressModel;
import cs601.socketServer.models.HotelModel;
import cs601.socketServer.models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by Yiding Liu on 11/15/2016.
 */
public class HotelHandler extends SQLBaseHandler{
    private static final String ADD_HOTEL_SQL = "INSERT INTO hotel_info (hotelid, hotelname, street, state, city, longitude, latitude, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String HOTEL_LIST_SQL = "SELECT *,AVG(overallrating) as rating FROM hotel_info LEFT OUTER JOIN reviews ON hotel_info.hotelid=reviews.hotelid GROUP BY hotelname LIMIT ?,?";
    //private static final String SEARCH_HOTEL_SQL = "SELECT * FROM hotel_info WHERE hotelid = ?";//deprecated
    private static final String SEARCH_HOTEL_SQL="SELECT *,AVG(overallrating) as rating FROM hotel_info LEFT OUTER JOIN reviews ON reviews.hotelid=hotel_info.hotelid WHERE hotel_info.hotelid=? GROUP BY hotelname";//deprecated
    private static final String GET_NUMBER_OF_HOTELS="SELECT COUNT(*) as number FROM hotel_info";//deprecated
    private static final String GET_HOTEL_LIST_BY_NAME="SELECT *,AVG(overallrating) as rating FROM hotel_info LEFT OUTER JOIN reviews ON hotel_info.hotelid=reviews.hotelid WHERE hotelname like ? GROUP BY hotel_info.hotelid LIMIT ?,?";
    private static final String GET_NUMBER_OF_HOTELS_BY_NAME="SELECT COUNT(*) as number FROM hotel_info WHERE hotelname like ?";
    /**
     * add one hotel
     * @param hotel
     * @return
     */
    public HotelModel addHotel(HotelModel hotel) {
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(ADD_HOTEL_SQL);) {
                setHotelStatement(hotel,statement);
                int rows=statement.executeUpdate();

                if (rows>0) {
                    return hotel;
                } else {
                    return null;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }

    /**
     * add multiple hotel
     */
    public int addMultipleHotel(List<HotelModel> hotels){
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(ADD_HOTEL_SQL);) {
                for(HotelModel hotel:hotels){
                    setHotelStatement(hotel,statement);
                    statement.addBatch();
                }
                int[] ruslts=statement.executeBatch();

                int cnt=0;
                for(int i:ruslts){
                    if(i>0) cnt++;
                }

                if (cnt>0) {
                    return cnt;
                } else {
                    return 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**
     * Get hotel list and the range starts from 'start' to 'end'
     * @param start start index
     * @param end end index
     * @return
     */
    public List<HotelModel> loadHotelList(int start,int end){
        List<HotelModel> list=new ArrayList<>();
        try (Connection connection = db.getConnection();) {
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(HOTEL_LIST_SQL);) {
                statement.setInt(1,start);
                statement.setInt(2,end);

                ResultSet resultSet=statement.executeQuery();

                while(resultSet.next()){
                    list.add(hotelFactory(resultSet));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        finally {
            return list;
        }
    }

    /**
     * Load hotel list by searching hotel name
     * @param name
     * @param start
     * @param end
     * @return
     */
    public List<HotelModel> loadHotelListByName(String name,int start,int end){
        List<HotelModel> list=new ArrayList<>();
        try (Connection connection = db.getConnection();) {
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(GET_HOTEL_LIST_BY_NAME);) {
                statement.setString(1,"%"+name+"%");
                statement.setInt(2,start);
                statement.setInt(3,end);

                ResultSet resultSet=statement.executeQuery();

                while(resultSet.next()){
                    list.add(hotelFactory(resultSet));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        finally {
            return list;
        }
    }

    /**
     * Get number of hotels which hase been filtered by the name
     * @param name
     * @return
     */
    public int getNumberOfHotelByName(String name){
        try (Connection connection = db.getConnection();) {
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_HOTELS_BY_NAME);) {
                statement.setString(1, "%"+name+"%");

                ResultSet resultSet=statement.executeQuery();

                if(resultSet.next()){
                    return resultSet.getInt("number");
                }
                else{
                    return 0;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**
     * search hotel by hotel id
     * @param id
     * @return
     */
    public HotelModel loadHotel(String id){
        try (Connection connection = db.getConnection();) {
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(SEARCH_HOTEL_SQL);) {
                statement.setString(1,id);

                ResultSet resultSet=statement.executeQuery();

                if(resultSet.next()){
                    return hotelFactory(resultSet);
                }
                else{
                    return null;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }

    /**
     * Get total number of
     * @return
     */
    public int getNumberOfHotel(){
        try (Connection connection = db.getConnection();) {
            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(GET_NUMBER_OF_HOTELS);) {

                ResultSet resultSet=statement.executeQuery();

                if(resultSet.next()){
                    return resultSet.getInt("number");
                }
                else{
                    return 0;
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return 0;
    }

    /**********************************private functions begin**********************************/
    private HotelModel hotelFactory(ResultSet resultSet) throws SQLException {
        String hotelId=resultSet.getString("hotelid");
        String hotelName=resultSet.getString("hotelname");
        String street=resultSet.getString("street");
        String state=resultSet.getString("state");
        String city=resultSet.getString("city");
        Double longitude=resultSet.getDouble("longitude");
        Double latitude=resultSet.getDouble("latitude");
        String country=resultSet.getString("country");
        Double rating=resultSet.getDouble("rating");
        String imageUrl=resultSet.getString("imageurl");
        if(imageUrl==null){
           imageUrl= ConfigInfo.getConfig("defaultImg")+"/hotel.jpg";
        }

        return new HotelModel(hotelId,hotelName,imageUrl,rating==null?0:rating,new AddressModel(street,state,city,longitude,latitude,country));
    }
    private void setHotelStatement(HotelModel hotel,PreparedStatement statement) throws SQLException {
        AddressModel add=hotel.getAddressInfo();
        statement.setString(1, hotel.getHotelId());
        statement.setString(2, hotel.getHotelName());
        statement.setString(3, add.getStreetAddress());
        statement.setString(4, add.getState());
        statement.setString(5, add.getCity());
        statement.setDouble(6, add.getLongitude());
        statement.setDouble(7, add.getLatitude());
        statement.setString(8, add.getCountry());
    }
    /**********************************private functions end**********************************/

}
