package eyes.video.utils;


import org.xutils.DbManager;
import org.xutils.x;

import eyes.video.app.App;


public class DBHelper {
    public static DbManager db = x.getDb(App.getInstance().getDaoConfig());
}
