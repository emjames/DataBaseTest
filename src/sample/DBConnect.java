package sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A on 2015/1/17.
 */
public class DBConnect {
    private static final String dir = System.getProperty("user.home" + "/taggR/");
    private static final String dbURL = "jdbc:derby:" + dir + "sample";
    private static final String dbDRiver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String user = "app";
    private static final String password = "app";

    private  static final List<File> dummyFileList = new ArrayList<File>();
    static {

    }

    private void setDBSystemDir(){
        System.setProperty("derby.system.home", "/Users/myuser/taggR");
    }

}
