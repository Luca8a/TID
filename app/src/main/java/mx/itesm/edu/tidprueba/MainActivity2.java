package mx.itesm.edu.tidprueba;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity2 extends ActionBarActivity {

    private String[] itemsTitulos;
    private DrawerLayout drawerLayout;
    private ListView listViewDrawer;
    private ActionBarDrawerToggle toggle;
    private CharSequence tituloDrawer, titulo;

    private MapaFragment mapaFragment;


    private Menu menuControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        itemsTitulos = getResources().getStringArray(R.array.opciones);
        titulo = tituloDrawer = getTitle();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listViewDrawer = (ListView ) findViewById(R.id.left_drawer);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>( this,
                android.R.layout.simple_list_item_1, itemsTitulos );
        listViewDrawer.setAdapter(adapter);

        listViewDrawer.setOnItemClickListener(new DrawerListListener());

        if(savedInstanceState == null){
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

    }

    public void seleccionaAccion(int p){

        switch (p){
            case  0:
                if(mapaFragment == null){
                    mapaFragment = new MapaFragment();
                    FragmentManager manager = getFragmentManager();
                    if(manager.findFragmentByTag("mapaFragment") == null){
                        manager.beginTransaction().replace(R.id.content_frame , mapaFragment, "mapaFragment").commit();
                    }
                }
                break;
        }


        listViewDrawer.setItemChecked(p,true);
        listViewDrawer.setSelection(p);
        setTitle(itemsTitulos[p]);
        titulo = itemsTitulos[p];
        drawerLayout.closeDrawer(listViewDrawer);

    }

    private class DrawerListListener implements ListView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            seleccionaAccion(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuControl = menu;
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (   toggle.onOptionsItemSelected(item )  ) {
            return true;
        }

        switch (id){
            case R.id.mapa:
                Toast.makeText(this, getString(R.string.mexico_titulo), Toast.LENGTH_LONG).show();
                muestraDialogoOpciones();
                break;
            case R.id.cambia_mapa:
                mapaFragment.cambiaMapaSatellite();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
*/

}
