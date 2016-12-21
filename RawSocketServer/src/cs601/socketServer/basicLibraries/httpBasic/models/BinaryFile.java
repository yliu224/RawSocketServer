package cs601.socketServer.basicLibraries.httpBasic.models;

/**
 * Created by yliu224 on 11/27/16.
 */
public class BinaryFile{
    private String fileType;
    private byte[] bytes;

    public BinaryFile(String fileType, byte[] bytes) {
        this.fileType = fileType;
        this.bytes = bytes;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }
}
