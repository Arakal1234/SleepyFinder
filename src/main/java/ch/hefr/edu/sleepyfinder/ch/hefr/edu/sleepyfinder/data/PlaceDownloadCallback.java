package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by perro_000 on 28.08.2016.
 */
public interface PlaceDownloadCallback {

    public void doCallback(List<Place> list);

}
