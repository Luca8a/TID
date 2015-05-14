package mx.itesm.edu.tidprueba;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Metaio extends Fragment {


    public Metaio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = new Intent(getActivity(), MetaioActivity.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_metaio, container, false);
    }


}