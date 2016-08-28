package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by perro_000 on 28.08.2016.
 */
public class WebPlaceUtilities {
    private static String RESULT = "results";
    private static String URL = "http://proxy2.fnfever.ch/proxy_googleapi.php"; // Special thanks to Michael Berchier for the hosting
    public static void request(Activity a, Map<String, String> params, PlaceDownloadCallback callback) throws Exception {
        HttpUtilities.callbackRequest(a,URL,params,callback);
    }


    /*
    Status Codes

    The "status" field within the place response object contains the status of the request, and may contain debugging information to help you track down why the place request failed. The "status" field may contain the following values:

    OK indicates that no errors occurred; the place was successfully detected and at least one result was returned.
    UNKNOWN_ERROR indicates a server-side error; trying again may be successful.
    ZERO_RESULTS indicates that the reference was valid but no longer refers to a valid result. This may occur if the establishment is no longer in business.
    OVER_QUERY_LIMIT indicates that you are over your quota.
    REQUEST_DENIED indicates that your request was denied, generally because of lack of an invalid key parameter.
    INVALID_REQUEST generally indicates that the query (reference) is missing.
    NOT_FOUND indicates that the referenced location was not found in the Places database.

     */


    public static List<Place> parsePlace(String s) throws JSONException {
        List<Place> list = new ArrayList<Place>();
        JSONObject root = new JSONObject(s);
        switch(root.getString("status")){
            case "OK": break;
            case "ZERO_RESULTS":
            default: return list;
        }
        JSONArray result = root.getJSONArray(RESULT);
        for(int i = 0;i<result.length();i++){
            list.add(parseSinglePlace(result.getJSONObject(i)));
        }
        return list;
    }

    private static Place parseSinglePlace(JSONObject jsonObject) throws JSONException {
        JSONArray names = jsonObject.names();
        MyPlace p = new MyPlace();
        String current;
        for(int i = 0;i<names.length();i++){
            current = names.getString(i);
            switch(current){
                case "geometry":
                    parseSinglePlaceGeometry(jsonObject.getJSONObject(current),p);
                    break;
                //case "id":
                case "place_id":
                    p.setId(jsonObject.getString(current));
                    break;
                case "name":
                    p.setName(jsonObject.getString(current));
                    break;
                case "rating":
                    p.setRating(Float.parseFloat(jsonObject.getString(current)));
                    break;
                case "vicinity"://not very accurate
                    p.setAddress(jsonObject.getString(current));
                // seem's to be unused in the Place interface
                case "reference":
                case "scope":
                    break;
                default:
                    Log.i("JSONParser",current + " field not defined");
                    break;
            }

        }
        return p;
    }

    private static void parseSinglePlaceGeometry(JSONObject jsonObject, MyPlace p) throws JSONException {

        JSONArray names = jsonObject.names();
        String current;
        for(int i = 0;i<names.length();i++) {
            current = names.getString(i);
            switch (current) {
                //"location" : { "lat" : 46.80645879999999, "lng" : 7.1550555 }
                case "location":
                    JSONObject val = jsonObject.getJSONObject(current);
                    p.setLatLng(new LatLng(val.getDouble("lat"), val.getDouble("lng")));
                    break;
                // "viewport" : { "northeast" : { "lat" : 46.80538309999999, "lng" : 7.157428049999998 }, "southwest" : { "lat" : 46.80519829999999, "lng" : 7.15733665 } }
                case "viewport":
                    //ignore this we won't use it
                    break;
            }
        }
    }
}
