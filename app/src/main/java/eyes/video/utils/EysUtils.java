package eyes.video.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import eyes.video.app.App;

public class EysUtils {
    /**
     * 是否连接网络
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }
    private static Toast toast = null;

    /**
     * 吐丝方法
     *
     * @param message 吐丝内容
     */
    public static void showToast(final String message) {
        ThreadUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                } else {
                    toast = Toast.makeText(App.mContext, message, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

}
