package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by perro_000 on 28.08.2016.
 */
public class DownloadTask extends AsyncTask<URL,Void,List<Place>> {

    private PlaceDownloadCallback callback;

    public DownloadTask(PlaceDownloadCallback callback){
        this.callback = callback;
    }

    @Override
    protected List<Place> doInBackground(URL... url) {
        assert url.length == 1;
        BufferedReader in = null;
        List<Place> list = new ArrayList<Place>();
        try {
            Log.i("DownloadTask",url.toString());
            URLConnection yc = url[0].openConnection();
            in = new BufferedReader(
                    new InputStreamReader(
                            yc.getInputStream()));
            String inputLine;
            StringBuilder b = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine);
            try {
                list.addAll(WebPlaceUtilities.parsePlace(b.toString()));
            } catch (JSONException e) {
                Log.d("JSONParser",e.getMessage());
            }
        } catch (IOException e){
            e.printStackTrace();
        }finally {
            if(in != null)try {in.close();} catch (IOException e) {e.printStackTrace();}
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<Place> places) {
        callback.doCallback(places);
    }
}
