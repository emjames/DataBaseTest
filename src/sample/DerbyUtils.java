package sample;

import java.sql.SQLException;

/**
 * Created by A on 2015/1/23.
 */
public class DerbyUtils {
    public  DerbyUtils(){
        //empty, helper class
    }

    public static boolean tableAlreadyExists(SQLException e ){
        boolean exists;
        if(e.getSQLState().equals("X0Y32")){
            exists = true;
        }else{
            exists = false;
        }
        return exists;
    }
}
