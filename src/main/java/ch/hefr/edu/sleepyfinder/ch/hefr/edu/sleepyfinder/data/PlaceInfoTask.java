package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;

import java.util.concurrent.Semaphore;

/**
 * Created by perro_000 on 28.08.2016.
 */
public class PlaceInfoTask extends AsyncTask<String,Void,PlaceInfo> {

    GoogleApiClient client;

    public PlaceInfoTask(GoogleApiClient client){
        this.client = client;
    }

    @Override
    protected PlaceInfo doInBackground(final String... params) {
        final PlaceInfo info = new PlaceInfo();
        /*final Semaphore sem = new Semaphore(0);
        Thread downloadPlace = new Thread(new Runnable() {
            @Override
            public void run() {
                sem.release();
            }
        });
        downloadPlace.start();*/

        PendingResult<PlaceBuffer> pending = Places.GeoDataApi.getPlaceById(client,params[0]);
        info.setPlace(pending.await().get(0));

        PendingResult<PlacePhotoMetadataResult> pendingImg = Places.GeoDataApi.getPlacePhotos(client,params[0]);
        PlacePhotoMetadataBuffer meta = pendingImg.await().getPhotoMetadata();
        if(meta.getCount() > 0){
            PendingResult<PlacePhotoResult> t = meta.get(0).getPhoto(client);
            info.setBitmap(t.await().getBitmap());
        }
        /*try {
            sem.acquire();
        } catch (InterruptedException e) {
        }*/
        return info;
    }
}
