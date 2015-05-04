package mx.itesm.edu.tidprueba;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Luck on 5/3/2015.
 */
public class Pagos extends Fragment {

    ArrayList<Pago> pagosList;
    public final String db = "pagos";
    private static String url = "";
    public PagosAdapter historial;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pagosList = new ArrayList<Pago>();

        historial=new PagosAdapter(getActivity(),
                R.layout.pago_dental_renglon,
                pagosList
        );
        View view= inflater.inflate(R.layout.fragment_pagos_rec, container, false);
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
        url+="/Pagos";
        Log.w("LOG", "URL: " + url);
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
            Log.w("LOG", "json: " + json);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            super.onPostExecute(json);
            try {
                Log.w("LOG", "json: " + json);
                // Getting JSON Array
                jsonArrayEventos = json.getJSONArray(db);
                Log.w("LOG", "tamano: " + jsonArrayEventos.length());
                for(int i=0;i<jsonArrayEventos.length();i++){
                    Log.w("LOG", "ciclo: " + i);
                    JSONObject c = jsonArrayEventos.getJSONObject(i);
                    Log.w("LOG", "ciclo: " + i);
                    String fecha=c.getString("fecha");
                    Log.w("LOG", "fecha: " + fecha);
                    String dec=c.getString("desc");
                    Log.w("LOG", "desc: " + dec);
                    String pago=c.getString("pago");
                    Log.w("LOG", "pago: " + pago);
                    String id=c.getString("_id");
                    Log.w("LOG", "id: " + id);
                    pagosList.add(Pago.creaPagoFechaDescCantId(fecha,dec , pago,id ));
                    Log.w("LOG", "ciclo: " + i);
                }

                dialog.dismiss();
                Log.w("LOG", "dismiss");
                historial.setItemList(pagosList);
                historial.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

    }

    public class PagosAdapter extends ArrayAdapter<Pago> {
        Context context;
        int layoutResourceId;
        ArrayList<Pago> data = null;


        public PagosAdapter(Context context, int resource, ArrayList<Pago> items){
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
                vi=inflater.inflate(R.layout.pago_dental_renglon,parent,false);
            }
            Pago ed=data.get(position);
            if(ed!=null) {
                TextView cant = (TextView) vi.findViewById(R.id.rowCantdad);
                TextView desc = (TextView) vi.findViewById(R.id.rowDesc);
                TextView fecha = (TextView) vi.findViewById(R.id.rowFecha);
                cant.setText(ed.getCantidad());
                desc.setText(ed.getDesc());
                fecha.setText(ed.getFecha());
            }
            return vi;
        }
        public void setItemList(ArrayList<Pago> list){
            data=list;
        }
    }

}
