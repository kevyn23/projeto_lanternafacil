package br.com.maracujasoftware.lanternafacil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btAtivar;
    Camera camera;
    private boolean isLigtOn = false;

    @Override
    protected void onStart(){
        super.onStart();
        camera = Camera.open();
        camera.release();

        //final Camera.Parameters p = camera.getParameters();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Toast toast = Toast.makeText(MainActivity.this, "oi", Toast.LENGTH_LONG);
                toast.show();
            }
        });
       /* if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                48);*/
        Context context = this;
        PackageManager pm = context.getPackageManager();

        btAtivar = (Button) findViewById(R.id.button_ativar);
        btAtivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Camera cam= Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                 camera= Camera.open();
                 final Camera.Parameters p = camera.getParameters();

                turnOnFlash(p);
                Toast.makeText(MainActivity.this, "Lights On!", Toast.LENGTH_SHORT).show();

                /*cam.setPreviewCallback(null);
                cam.stopPreview();
                cam.release();
                Camera.Parameters p = cam.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();*/
            }
        });

    }
    private void turnOnFlash(Camera.Parameters p) {
        p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(p);
        camera.startPreview();
        isLigtOn = true;
    }
    private void turnOffFlash(Camera.Parameters p){
        p.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(p);
        camera.stopPreview();
        isLigtOn = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop(){
        super.onStop();

        if(camera!=null){
            camera.release();
        }
    }
}
