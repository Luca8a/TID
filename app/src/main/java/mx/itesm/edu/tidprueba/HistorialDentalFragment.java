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
    public final String db = "eventos";

    public HistorialDentalFragment() {
        // Required empty public constructor
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

        String id=DevicePsuedoID.getUniquePsuedoID();
        id= id.replace("-","");
        url+=id;
        url+="/Eventos";
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
            JSONObject json = jParser.getJSONFromUrl(url,db);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            try {
                // Getting JSON Array
                jsonArrayEventos = json.getJSONArray(db);
                Log.w("LOG", "json: " + json);
                for(int i=0;i<jsonArrayEventos.length();i++){
                    JSONObject c = jsonArrayEventos.getJSONObject(i);
                    eventos.add(EventoDental.creaEventoFechaEventoId(c.getString("fecha"),c.getString("evento"),c.getString("_id")));
                }
                dialog.dismiss();
                historial.setItemList(eventos);
                historial.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
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
