package consts;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Constants {
    static Config consts = ConfigFactory.load("app.conf");

    // verifies if the desired design pattern is implemented or not
    // in the config file
    public static boolean verifyImplementation(String desPat){
        if(consts.getBoolean(desPat)){
            return true;
        }
        return false;
    }
}
