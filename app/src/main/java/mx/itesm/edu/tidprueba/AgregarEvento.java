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
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.UUID;


public class AgregarEvento extends Fragment implements View.OnClickListener {
    EditText evento;
    DatePicker pickerDate;
    private static String url = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_agregar_evento, container, false);
        pickerDate = (DatePicker)v.findViewById(R.id.datePicker);
        Calendar now = Calendar.getInstance();
        pickerDate.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

       ImageButton b = (ImageButton) v.findViewById(R.id.buttonGuardar);
        b.setOnClickListener(this);
        evento= (EditText) v.findViewById(R.id.editText);

        url=getString(R.string.URL);

        String id=DevicePsuedoID.getUniquePsuedoID();
        id= id.replace("-","");
        url+=id;
        url+="/Eventos";
        Log.w("LOG", "URL: " + url);
        return v;
    }

    String json;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonGuardar:

                json="{\"evento\":\""+evento.getText().toString()+"\",\"fecha\":\""
                        +pickerDate.getYear()+"/"+pickerDate.getMonth()+"/"+pickerDate.getDayOfMonth()+"\"}";
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
