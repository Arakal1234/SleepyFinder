package ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data;

import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceImpl;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by perro_000 on 27.08.2016.
 */
public class MyPlace implements Place {

    private String id;
    private List<Integer> placeTypes = new ArrayList<Integer>();
    private CharSequence address;
    private Locale locale;
    private CharSequence name;
    private LatLng latLng;
    private LatLngBounds latLngBounds;
    private Uri website;
    private CharSequence phoneNumber;
    private float rating;
    private int priceLevel;
    private CharSequence attributions;
    private boolean valid = false;

    protected MyPlace(){
        valid = true;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setPlaceTypes(List<Integer> placeTypes) {
        this.placeTypes = placeTypes;
    }

    public void setAddress(CharSequence address) {
        this.address = address;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setLatLngBounds(LatLngBounds latLngBounds) {
        this.latLngBounds = latLngBounds;
    }

    public void setWebsite(Uri website) {
        this.website = website;
    }

    public void setPhoneNumber(CharSequence phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public void setAttributions(CharSequence attributions) {
        this.attributions = attributions;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Integer> getPlaceTypes() {
        return placeTypes;
    }

    @Override
    public CharSequence getAddress() {
        return address;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public LatLngBounds getViewport() {
        return null;
    }

    @Override
    public Uri getWebsiteUri() {
        return website;
    }

    @Override
    public CharSequence getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public float getRating() {
        return rating;
    }

    @Override
    public int getPriceLevel() {
        return priceLevel;
    }

    @Override
    public CharSequence getAttributions() {
        return attributions;
    }

    @Override
    public Place freeze() {
        return this;
    }

    @Override
    public boolean isDataValid() {
        return valid;
    }
}
