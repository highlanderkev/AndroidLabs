package testing.mbtesting.modalpopupexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.text.Layout;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll = new LinearLayout(this);

        Button myButton = new Button(this);
        myButton.setText("Modal Popup");
        myButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showInfoDialog();
            }
        });

        TextView text = new TextView(this);
        text.setText("Modal Test");

        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(myButton);
        ll.addView(text);
        setContentView(ll);


        //setContentView(R.layout.activity_maps);
        //setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //setUpMapIfNeeded();
    }


    public void showInfoDialog() {
        DialogFragment dialog = new InfoDialogFragment();
        dialog.show(getFragmentManager(), "Info");
    }

    public void showReportDialog(){
        DialogFragment dialog = new ReportDialogFragment();
        dialog.show(getFragmentManager(), "Report");
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    public static class ReportDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_report, container, false);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = super.onCreateDialog(savedInstanceState);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            return dialog;
        }
    }

    public static class InfoDialogFragment extends DialogFragment implements View.OnClickListener {

        private Button reportButton;

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Light_Panel);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.dialog_moreinfo, container, false);
            reportButton = (Button)view.findViewById(R.id.report_button);
            reportButton.setOnClickListener(this);
            setDialogPosition();
            return view;
        }

        private void setDialogPosition(){
            Window window = getDialog().getWindow();
            window.setGravity(Gravity.BOTTOM);
        }

        @Override
        public void onClick(View v){
            int id = v.getId();
            if(id == R.id.report_button){
                InfoDialogFragment.this.getDialog().cancel();
                DialogFragment dialog = new ReportDialogFragment();
                dialog.show(getFragmentManager(), "Report");
            }else{
                InfoDialogFragment.this.getDialog().cancel();
            }
        }

    }
}
