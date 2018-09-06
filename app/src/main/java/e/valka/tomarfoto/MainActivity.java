package e.valka.tomarfoto;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.boton) Button boton;
    @BindView(R.id.imagen) ImageView imagen;
    private final int PERMISO_CAMARA = 1001;
    private final int RESULTADO_CAMARA = 1002;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        boton.setOnClickListener((button)->{
            tomarFoto();
        });
    }
    private void tomarFoto(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.CAMERA},PERMISO_CAMARA);
            return;
        }
        Intent intentoCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentoCamara,RESULTADO_CAMARA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISO_CAMARA:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    tomarFoto();
                }
            break;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode){
            case RESULTADO_CAMARA:
                if(resultCode == Activity.RESULT_OK && data != null){
                    imagen.setImageBitmap((Bitmap) data.getExtras().get("data"));
                }
                break;
        }

    }
}
