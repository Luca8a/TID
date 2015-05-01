package mx.itesm.edu.tidprueba;



import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;





/**
 * A simple {@link Fragment} subclass.
 */
public class HistorialDentalFragment extends Fragment {

    ArrayList<EventoDental> eventos;

    public HistorialDentalFragment() {
        // Required empty public constructor
    }
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
    private static String url = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public EventosAdapter historial;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        eventos = new ArrayList<EventoDental>();

         historial=new EventosAdapter(getActivity(),
                R.layout.hist_dental_renglon,
                eventos
        );
        View view= inflater.inflate(R.layout.fragment_historial_dental, container, false);
        ListView lista= (ListView) view.findViewById(R.id.list);
        lista.setAdapter(historial);
        getData();


        return view;
    }

    private void getData(){
        url=getString(R.string.URL);

        String id=getUniquePsuedoID();
        id= id.replace("-","");
        url+=id;
        Log.w("LOG","URL: "+url);
        (new JSONParse()).execute();
    }
JSONArray jsonArrayEventos=null;
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage(getString(R.string.descarga));
            dialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            try {
                // Getting JSON Array
                jsonArrayEventos = json.getJSONArray("eventos");
                for(int i=0;i<jsonArrayEventos.length();i++){
                    JSONObject c = jsonArrayEventos.getJSONObject(i);
                    eventos.add(EventoDental.creaEventoFechaEventoId(c.getString("fecha"),c.getString("evento"),c.getString("_id")));
                }
                dialog.dismiss();
                historial.setItemList(eventos);
                historial.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    public class EventosAdapter extends ArrayAdapter<EventoDental>{
        Context context;
        int layoutResourceId;
        ArrayList<EventoDental> data = null;


        public EventosAdapter(Context context, int resource, ArrayList<EventoDental> items){
            super(context,resource,items);
            this.layoutResourceId = resource;
            this.context = context;
            this.data = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View vi=convertView;
            if(vi==null){
                LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi=inflater.inflate(R.layout.hist_dental_renglon,parent,false);
            }
            EventoDental ed=data.get(position);
            if(ed!=null) {
                TextView evento = (TextView) vi.findViewById(R.id.rowEvent);
                TextView fecha = (TextView) vi.findViewById(R.id.rowFecha);
                evento.setText(ed.getEvento());
                fecha.setText(ed.getFecha());
            }
            return vi;
        }
        public void setItemList(ArrayList<EventoDental> list){
            data=list;
        }
    }
}
