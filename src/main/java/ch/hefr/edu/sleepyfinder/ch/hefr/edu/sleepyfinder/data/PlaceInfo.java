package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.graphics.Bitmap;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.BitmapDescriptor;

/**
 * Created by perro_000 on 28.08.2016.
 */
public class PlaceInfo {

    private Place place;
    private Bitmap bitmap;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
