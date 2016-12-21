package cs601.socketServer.databaseHandlers;

import cs601.hotelapp.FormatString;
import cs601.socketServer.basicLibraries.databaseBasic.SQLBaseHandler;
import cs601.socketServer.basicLibraries.databaseBasic.Status;
import cs601.socketServer.models.UserModel;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Random;

/**
 * Created by yliu224 on 11/13/16.
 */
public class UserHandler extends SQLBaseHandler {
    private static final String REGISTER_SQL = "INSERT INTO login_users (username, password, usersalt) " + "VALUES (?, ?, ?);";
    private static final String USER_SQL = "SELECT * FROM login_users WHERE username = ?";
    private static final String SALT_SQL = "SELECT usersalt FROM login_users WHERE username = ?";
    private static final String AUTH_SQL = "SELECT * FROM login_users WHERE username = ? AND password = ?";
    private static final String SEARCH_USER_SQL = "SELECT * FROM login_users WHERE userid = ?";
    private static final String UPDATE_LAST_LOGIN_TIME ="UPDATE login_users SET last_login_time= ? ,current_login_time = ? WHERE userid= ?";
    private static final String GET_LAST_LOGIN_TIME="SELECT current_login_time FROM login_users WHERE userid= ?";
    private Random random;

    public UserHandler(){
        super();
        random=new Random();
    }

    /*****************************SQL actions begin*****************************/
    /**
     * Registers a new user, placing the username, password hash, and salt into
     * the database if the username does not already exist.
     *
     * @param newuser
     *            - username of new user
     * @param newpass
     *            - password of new user
     * @return Status.OK if registration successful
     */
    public UserModel registerUser(String newuser, String newpass) {
        Status status = Status.ERROR;

        // make sure we have non-null and non-emtpy values for login
        if (FormatString.isBlank(newuser) || FormatString.isBlank(newpass)) {
            status = Status.INVALID_LOGIN;
            //System.out.println("Invalid regiser info");
            return null;
        }

        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {
            status = duplicateUser(connection, newuser);

            // if okay so far, try to insert new user
            if (status == Status.OK) {
                // generate salt
                byte[] saltBytes = new byte[16];
                random.nextBytes(saltBytes);

                String usersalt = encodeHex(saltBytes, 32); // hash salt
                String passhash = getHash(newpass, usersalt); // combine
                // password and
                // salt and hash
                // again

                // add user info to the database table
                try (PreparedStatement statement = connection.prepareStatement(REGISTER_SQL);) {
                    statement.setString(1, newuser);
                    statement.setString(2, passhash);
                    statement.setString(3, usersalt);
                    statement.executeUpdate();

                    if(status == Status.OK){
                        try(PreparedStatement statementGetUser = connection.prepareStatement(USER_SQL);){
                            statementGetUser.setString(1,newuser);
                            ResultSet resultSet=statementGetUser.executeQuery();

                            if(resultSet.next()){
                                return userFactory(resultSet);
                            }
                        }
                    }
                    else{
                        return null;
                    }
                }
            }
        } catch (SQLException ex) {
            status = Status.CONNECTION_FAILED;
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;

    }

    /**
     * check user by username and password
     * @param username
     * @param pass
     * @return
     */
    public UserModel loginUser(String username, String pass){
        Status status = Status.ERROR;

        // make sure we have non-null and non-emtpy values for login
        if (FormatString.isBlank(username) || FormatString.isBlank(username)) {
            status = Status.INVALID_LOGIN;
            //System.out.println("Invalid regiser info");
            return null;
        }

        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            String usersalt = getSalt(connection,username); // get salt
            String passhash = getHash(pass, usersalt); // combine

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(AUTH_SQL);) {
                statement.setString(1, username);
                statement.setString(2, passhash);
                ResultSet result=statement.executeQuery();

                //if it found user in the database
                if(result.next()){
                    return userFactory(result);
                }
                else{
                    return null;
                }
            }

        } catch (SQLException ex) {
            status = Status.CONNECTION_FAILED;
            ex.printStackTrace();
            System.out.println("Error while connecting to the database: " + ex);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the last login time
     * @param userId
     * @return
     */
    public long updateLoginTime(int userId){
        try (Connection connection = db.getConnection();) {

            Timestamp lastLoginTime=getLastLoginTime(connection,userId);
            // update last login time
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_LAST_LOGIN_TIME);) {
                statement.setTimestamp(1,lastLoginTime);
                statement.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                statement.setInt(3, userId);
                long line=statement.executeLargeUpdate();
                return line;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error while connecting to the database: " + ex);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public UserModel getUser(String userId){
        // try to connect to database and test for duplicate user
        try (Connection connection = db.getConnection();) {

            // add user info to the database table
            try (PreparedStatement statement = connection.prepareStatement(SEARCH_USER_SQL);) {
                statement.setString(1, userId);

                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return userFactory(resultSet);
                }
                else return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error while connecting to the database: " + ex);
        }
        return null;
    }
    /*****************************SQL actions end*****************************/

    /*****************************private helper begin*****************************/
    /**
     * Get last login time
     * @param connection
     * @param userId
     * @return
     */
    private Timestamp getLastLoginTime(Connection connection,int userId){
        try(PreparedStatement statement=connection.prepareStatement(GET_LAST_LOGIN_TIME)){
            statement.setInt(1,userId);
            ResultSet resultSet=statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getTimestamp("current_login_time");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Tests if a user already exists in the database. Requires an active
     * database connection.
     *
     * @param connection
     *            - active database connection
     * @param user
     *            - username to check
     * @return Status.OK if user does not exist in database
     * @throws SQLException
     */
    private Status duplicateUser(Connection connection, String user) {

        assert connection != null;
        assert user != null;

        Status status = Status.ERROR;

        try (PreparedStatement statement = connection.prepareStatement(USER_SQL);) {
            statement.setString(1, user);

            ResultSet results = statement.executeQuery();
            status = results.next() ? Status.DUPLICATE_USER : Status.OK;
        } catch (SQLException e) {
            status = Status.SQL_EXCEPTION;
            //System.out.println("Exception occured while processing SQL statement:" + e);
        }

        return status;
    }

    /**
     * Returns the hex encoding of a byte array.
     *
     * @param bytes
     *            - byte array to encode
     * @param length
     *            - desired length of encoding
     * @return hex encoded byte array
     */
    private String encodeHex(byte[] bytes, int length) {
        BigInteger bigint = new BigInteger(1, bytes);
        String hex = String.format("%0" + length + "X", bigint);

        assert hex.length() == length;
        return hex;
    }

    /**
     * Calculates the hash of a password and salt using SHA-256.
     *
     * @param password
     *            - password to hash
     * @param salt
     *            - salt associated with user
     * @return hashed password
     */
    private String getHash(String password, String salt) {
        String salted = salt + password;
        String hashed = salted;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salted.getBytes());
            hashed = encodeHex(md.digest(), 64);
        } catch (Exception ex) {
            //System.out.println("Unable to properly hash password." + ex);
        }

        return hashed;
    }

    /**
     * Gets the salt for a specific user.
     *
     * @param connection
     *            - active database connection
     * @param user
     *            - which user to retrieve salt for
     * @return salt for the specified user or null if user does not exist
     * @throws SQLException
     *             if any issues with database connection
     */
    private String getSalt(Connection connection, String user) throws SQLException {
        assert connection != null;
        assert user != null;

        String salt = null;

        try (PreparedStatement statement = connection.prepareStatement(SALT_SQL);) {
            statement.setString(1, user);

            ResultSet results = statement.executeQuery();

            if (results.next()) {
                salt = results.getString("usersalt");
            }
        }

        return salt;
    }

    /**
     * create user model
     * @param resultSet
     * @return
     */
    private UserModel userFactory(ResultSet resultSet) throws SQLException {
        String userName=resultSet.getString("username");
        int userId=resultSet.getInt("userid");
        Timestamp timestamp=resultSet.getTimestamp("last_login_time");

        Date lastLoginTime=null;
        if(timestamp!=null){
            lastLoginTime=new Date(timestamp.getTime());
        }

        return new UserModel(userName,userId,lastLoginTime);
    }
    /*****************************private helper end*****************************/
}
