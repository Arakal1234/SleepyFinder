package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Created by perro_000 on 27.08.2016.
 */
public class HttpUtilities {

    public static void callbackRequest(Activity a, String url, Map<String,String> params, final PlaceDownloadCallback callback) throws Exception {
        ConnectivityManager connMgr = (ConnectivityManager)
                 a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            DownloadTask t = new DownloadTask(callback);
            t.execute(generateURL(url,params));
            // fetch data
        } else {
            throw new Exception("network not accessible");
        }

    }

    private static URL generateURL(String url, Map<String, String> params) throws MalformedURLException {
        if(params.isEmpty())return new URL(url);
        Set<Entry<String, String>> set = params.entrySet();
        Iterator<Entry<String, String>> i = set.iterator();
        Entry<String, String> e = i.next();
        url += "?"+e.getKey()+"="+e.getValue();
        while(i.hasNext()){
            e = i.next();
            url += "&"+e.getKey()+"="+e.getValue();
        }
        return new URL(url);
    }
}
