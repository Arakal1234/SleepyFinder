package ch.hefr.edu.sleepyfinder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import ch.hefr.edu.sleepyfinder.ch.hefr.edu.sleepyfinder.data.PlaceInfo;

/**
 * Created by johnnyhaymoz on 28.08.16.
 */
public class PlaceInfoDialogFragment extends DialogFragment {

    private PlaceInfo place;
    private View view;
    public void setPlace(PlaceInfo place) {
        this.place = place;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.place_info_dialog, null);
        ((TextView)view.findViewById(R.id.dialog_place_name)).setText(place.getPlace().getName());
        ((ImageView)view.findViewById(R.id.dialog_place_image)).setImageBitmap(place.getBitmap());
        String placeinformation =BuildPlaceInfoString();
        ((TextView)view.findViewById(R.id.dialog_place_Info)).setText(placeinformation);
        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PlaceInfoDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    private String BuildPlaceInfoString() {
        Place currentplace = place.getPlace();
        String adresse =  currentplace.getAddress()==null?"no adress found":(String) currentplace.getAddress();
        String phone =  currentplace.getPhoneNumber()==null?"no phone number found":(String) currentplace.getPhoneNumber();
        String url =currentplace.getWebsiteUri()==null?"no website found":currentplace.getWebsiteUri().toString();
        int priceLevel =currentplace.getPriceLevel();
        String price="";
        if(priceLevel<0){
            price="unknown";
        }
        else{
            switch (priceLevel){
                case 0:
                    price ="Free";
                    break;
                case 1:
                    price ="Inexpensive";
                    break;
                case 2:
                    price ="Moderate";
                    break;
                case 3:
                    price ="Expensive";
                    break;
                case 4:
                    price ="Very Expensive";
                    break;
            }
        }
        String rating = currentplace.getRating()<0?"no rating aviable":String.format("%s/5.0",String.valueOf(currentplace.getRating())) ;
        return String.format("%s\nphone: %s\n%s\nprice: %s\nrating: %s",adresse,phone,url,price,rating);
    }
}
