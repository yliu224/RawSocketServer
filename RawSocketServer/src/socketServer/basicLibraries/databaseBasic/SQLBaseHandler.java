package  socketServer.basicLibraries.databaseBasic;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Base class of SQLhandler.Every other  SQLhandler should extend this class
 */
public class SQLBaseHandler {
    protected DatabaseConnector db;
    public SQLBaseHandler(){
        Status status = Status.OK;
        try {
            db = new DatabaseConnector();
            status = db.testConnection() ? Status.OK : Status.CONNECTION_FAILED;
        } catch (FileNotFoundException e) {
            status = Status.MISSING_CONFIG;
        } catch (IOException e) {
            status = Status.MISSING_VALUES;
        }

        if (status != Status.OK) {
            System.out.println("Error while obtaining a connection to the database: " + status);
        }
    }
}
