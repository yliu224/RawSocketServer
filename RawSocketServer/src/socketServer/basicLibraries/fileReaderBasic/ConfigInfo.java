package  socketServer.basicLibraries.fileReaderBasic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by yliu224 on 11/12/16.
 */
public class ConfigInfo {
    private static ConfigInfo ci=new ConfigInfo();
    private static JSONObject jsonObject;

    /**
     * get specific value
     * @param name
     * @return
     */
    public static String getConfig(String name){
        Object obj=jsonObject.get(name);
        if(obj==null) return null;
        else return (String)obj;
    }

    /**
     * load config file
     */
    private ConfigInfo() {
        JSONParser parser = new JSONParser();
        try {
             jsonObject = (JSONObject)parser.parse(new FileReader("SocketServerConfig.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
