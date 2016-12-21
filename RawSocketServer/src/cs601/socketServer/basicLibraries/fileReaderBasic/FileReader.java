package cs601.socketServer.basicLibraries.fileReaderBasic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by yliu224 on 11/12/16.
 */
public class FileReader {

    /**
     * can only get html file,the file path doesn't need the extension name
     * @param filePath
     * @return
     */
    public static String getHtml(String filePath){
        try {
            //file redirect.....It should be url redirect!!!!!!!!!!!!
            if(filePath.equals("/")) filePath="/Index";


            Path p=Paths.get(ConfigInfo.getConfig("viewsPath")+filePath+".html");
            if(Files.exists(p)){
                return new String(Files.readAllBytes(p));
            }
            else{
                Path h=Paths.get(ConfigInfo.getConfig("viewsPath")+"/404.html");
                //System.out.println(h.toAbsolutePath());
                return new String(Files.readAllBytes(h));
            }


        } catch (IOException e) {
            e.printStackTrace();
            return "FILE EXCEPTION";
        }
    }

    /**
     * this method can get any file from the server,but the filepath should specify the file
     * extension name
     * @param filePath
     * @return
     */
    public static String getFile(String filePath){
        try {
            Path p=Paths.get(ConfigInfo.getConfig("viewsPath")+filePath);
            if(Files.exists(p)){
                return new String(Files.readAllBytes(p));
            }
            else{
                Path h=Paths.get(ConfigInfo.getConfig("viewsPath")+"/404.html");
                //System.out.println(h.toAbsolutePath());
                return new String(Files.readAllBytes(h));
            }


        } catch (IOException e) {
            e.printStackTrace();
            return "FILE EXCEPTION";
        }
    }

    /**
     * get image file
     * @param filePath
     * @return
     */
    public static byte[] getImage(String filePath){
        try {
            Path p=Paths.get(ConfigInfo.getConfig("viewsPath")+filePath);
            return Files.readAllBytes(p);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
