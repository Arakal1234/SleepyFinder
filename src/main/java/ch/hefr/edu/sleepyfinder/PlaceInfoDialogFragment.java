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
        ((TextView)view.findViewById(R.id.dialog_place_Info)).setText(place.getPlace().getAddress());
        builder.setView(view).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PlaceInfoDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
