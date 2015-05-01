package mx.itesm.edu.tidprueba;



import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.media.tv.TvContentRating;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


public class AgregarEvento extends Fragment implements View.OnClickListener {
    EditText evento;
    EditText fecha;
    private static String url = "";
    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        // Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = android.os.Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_agregar_evento, container, false);

       ImageButton b = (ImageButton) v.findViewById(R.id.buttonGuardar);
        b.setOnClickListener(this);
        evento= (EditText) v.findViewById(R.id.editText);
        fecha = (EditText) v.findViewById(R.id.editText2);
        url=getString(R.string.URL);

        String id=getUniquePsuedoID();
        id= id.replace("-","");
        url+=id;
        Log.w("LOG", "URL: " + url);
        return v;
    }

    String json;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGuardar:

                json="{\"evento\":\""+evento.getText().toString()+"\",\"fecha\":\""+fecha.getText().toString()+"\"}";
                Log.w("JSON",json);
                (new AddToDatabase()).execute();
                break;
        }
    }

    private class AddToDatabase extends AsyncTask<String, String, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage(getString(R.string.actualizar));
            dialog.show();
        }
        @Override
        protected Boolean doInBackground(String... args) {


           //"{\"Evento\":\"sacaron diente\",\"Fecha\":\"25/08/45\"}"
            // Getting JSON from URL

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            StringEntity se=null;
            try {
                se = new StringEntity(json);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
            se.setContentType("application/json");

            httpPost.setEntity(se);
            try {
                HttpResponse httpResponse = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        @Override
        protected void onPostExecute(Boolean json) {
            super.onPostExecute(json);
            dialog.dismiss();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new HistorialDentalFragment()).commit();
        }

    }

}
