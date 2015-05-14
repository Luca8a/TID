package mx.itesm.edu.tidprueba;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.metaio.sdk.ARViewActivity;
import com.metaio.sdk.MetaioDebug;
import com.metaio.sdk.jni.IGeometry;
import com.metaio.sdk.jni.IMetaioSDKCallback;
import com.metaio.sdk.jni.Vector3d;
import com.metaio.tools.io.AssetsManager;

import java.io.IOException;


public class MetaioActivity extends ARViewActivity {

    private String marcador;
    private IGeometry BTL;
    private AssetsExtracter assets;



    private class AssetsExtracter extends AsyncTask<Integer, Integer, Boolean>
    {
        @Override
        protected Boolean doInBackground(Integer... params)
        {
            try
            {
                // Extract all assets and overwrite existing files if debug build
                AssetsManager.extractAllAssets(getApplicationContext(), BuildConfig.DEBUG);
            }
            catch (IOException e)
            {
                MetaioDebug.printStackTrace(Log.ERROR, e);
                return false;
            }

            return true;
        }

    }

    @Override
    protected int getGUILayout() {
        return R.layout.activity_main;
    }

    @Override
    protected IMetaioSDKCallback getMetaioSDKCallbackHandler() {
        return null;
    }

    @Override
    protected void loadContents() {
        marcador = AssetsManager.getAssetPath(getApplicationContext(),"TrackingData_MarkerlessFast.xml");
        metaioSDK.setTrackingConfiguration(marcador);

        String modelo = AssetsManager.getAssetPath(getApplicationContext(), "DSL2.png");
        if(modelo != null){
            BTL = metaioSDK.createGeometryFromImage(modelo);
            if(BTL != null){
                BTL.setScale(new Vector3d(3.0f,3.0f,3.0f));
                BTL.setCoordinateSystemID(1);
                BTL.setVisible(true);
            }

        }

    }
    private boolean enlarged=false;
    @Override
    protected void onGeometryTouched(IGeometry geometry) {
        if(!enlarged){
            geometry.setScale(new Vector3d(10.0f,10.0f,10.0f));
            enlarged=true;
        }else{
            geometry.setScale(new Vector3d(6.0f,6.0f,6.0f));
            enlarged=false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assets = new AssetsExtracter();
        assets.execute(0);


    }


}
