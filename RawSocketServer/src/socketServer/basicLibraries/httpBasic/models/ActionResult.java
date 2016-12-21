package  socketServer.basicLibraries.httpBasic.models;

import org.json.simple.JSONObject;

/**
 * Created by yliu224 on 11/27/16.
 */
public class ActionResult {
    public static final int REDIRECT=1;
    public static final int JSON=2;
    public static final int HTML=3;
    public static final int BYTES=4;
    public static final int LOG_IN_SUCCESS =5;
    public static final int LOG_IN_FAILED=6;
    public static final int LOG_OUT=7;

    private int operationType;
    private String stringContent;
    private Object obj;
    private BinaryFile file;

    public ActionResult(int operationType, String stringContent) {
        this.operationType = operationType;
        this.stringContent = stringContent;
    }

    public ActionResult(int operationType, Object obj) {
        this.operationType = operationType;
        this.obj = obj;
    }

    public ActionResult(int operationType,String fileType, byte[] bytes) {
        this.operationType = operationType;
        this.file = new BinaryFile(fileType,bytes);
    }

    public int getOperationType() {
        return operationType;
    }

    public String getStringContent() {
        return stringContent;
    }

    public Object getObject() {
        return obj;
    }

    public BinaryFile getFile() {
        return file;
    }

}
