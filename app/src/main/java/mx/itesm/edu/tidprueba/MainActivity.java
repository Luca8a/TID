package mx.itesm.edu.tidprueba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.app.Fragment;




public class MainActivity extends ActionBarActivity {

    private String[] itemsTitulos;
    private DrawerLayout drawerLayout;
    private ListView listViewDrawer;
    private ActionBarDrawerToggle toggle;
    private CharSequence tituloDrawer, titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = tituloDrawer = "TiD";
        itemsTitulos = getResources().getStringArray(R.array.opciones);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listViewDrawer = (ListView)findViewById(R.id.left_drawer);

        Objeto[] opciones = new Objeto[8];
        opciones[0] = new Objeto(R.drawable.ic_launcher, itemsTitulos[0]);
        opciones[1] = new Objeto(R.drawable.ic_action_hd_web,itemsTitulos[1]);
        opciones[2] = new Objeto(R.drawable.ic_action_rec_web, itemsTitulos[2]);
        opciones[3] = new Objeto(R.drawable.ic_action_dentista_web, itemsTitulos[3]);
        opciones[4] = new Objeto(R.drawable.ic_action_juego, itemsTitulos[4]);
        opciones[5] = new Objeto(R.drawable.ic_action_marc, itemsTitulos[5]);
        opciones[6] = new Objeto(R.drawable.ic_action_mident, itemsTitulos[6]);
        opciones[7] = new Objeto(R.drawable.ic_action_aboutus, itemsTitulos[7]);

        DrawerAdaptor adaptador = new DrawerAdaptor(this, R.layout.renglon, opciones);
        listViewDrawer.setAdapter(adaptador);

        listViewDrawer.setOnItemClickListener(new DrawerListListener());

        if (savedInstanceState == null){
            seleccionaAccion(0);
        }

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.abrir,
                R.string.cerrar
        ){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(tituloDrawer);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(titulo);
            }
        };

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);




    }

    private class DrawerListListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            seleccionaAccion(position);
        }

    }

    public void seleccionaAccion(int p){
        Fragment fragment = null;
        switch (p){
            case 0:
                fragment= new InicioFragment();
                break;
            case 1:
                fragment= new HistorialDentalFragment();
                break;
            case 2:
                fragment= new Recordatorios();
                break;
            case 3:
                //TODO: PAGOS
                fragment = new Pagos();
                break;
            case 4:
                //TODO: JUEGO TID
                fragment = new Metaio();
                break;
            case 5:
                //TODO: Marcas Reconocidas
                fragment= new MarcasRecomendadas();
                break;
            case 6:
                fragment= new Dentista();
                break;
            case 7:
                fragment= new About();
                break;
        }
        if(  fragment != null  ){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            listViewDrawer.setItemChecked(p, true);
            listViewDrawer.setSelection(p);
            setTitle(itemsTitulos[p]);
            drawerLayout.closeDrawer(listViewDrawer);
        }else{
            listViewDrawer.setItemChecked(p,true);
            listViewDrawer.setSelection(p);
            setTitle(itemsTitulos[p]);
            drawerLayout.closeDrawer(listViewDrawer);
        }

    }

    public void nuevoRegistro(View v){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new AgregarEvento()).commit();
    }
	    public void nuevoPago(View v){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new AgregarPago()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        if (toggle. onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

