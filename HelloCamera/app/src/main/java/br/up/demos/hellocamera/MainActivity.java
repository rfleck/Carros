package br.up.demos.hellocamera;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.app.AlertDialog;


import java.io.File;

public class MainActivity extends AppCompatActivity {
    // Caminho para salvar o arquivo
    private File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView b = (ImageView) findViewById(R.id.btAbrirCamera);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria o caminho do arquivo no SDCard
                file = SDCardUtils.getSdCardFile("up", "foto.jpg");

                // Chama a intent informando o arquivo para salvar a foto
                Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(i, 0);
            }
        });


        // Solicita as permissÃµes
        String[] permissoes = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };
        PermissionUtils.validate(this, 0, permissoes);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissÃ£o foi negada, agora Ã© com vocÃª :-)
                alertAndFinish();
                return;
            }
        }

        // Se chegou aqui estÃ¡ OK :-)
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, vocÃª precisa aceitar as permissÃµes.");
            // Add the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap bitmap = ImageUtils.getResizedImage(Uri.fromFile(file), 480, 360);

            Toast.makeText(this, "w/h:" + bitmap.getWidth() + "/" + bitmap.getHeight(), Toast.LENGTH_SHORT).show();
            // Atualiza a imagem na tela
            ImageView img = (ImageView) findViewById(R.id.imagem);
            img.setImageBitmap(bitmap);
        }

/*        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                // Recupera o Bitmap retornado pela câmera
                Bitmap bitmap = (Bitmap) bundle.get("data");
                // Atualiza a imagem na tela
                ImageView img = (ImageView) findViewById(R.id.imagem);
                img.setImageBitmap(bitmap);
            }
        }*/
    }
}
