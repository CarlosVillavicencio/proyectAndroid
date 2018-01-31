package com.example.usuario.seccion_01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamera;
    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imgBtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        //Boton para la llamada
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    // comprobar version actual de android que estamos corriendo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Comprobar si ha aceptado, no ha aceptado, o nunca se le ha preguntado
                        if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                            //ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                                return;
                            startActivity(i);
                        } else {
                            //ha denegado, o es la primera vez que se le pregunta
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                //no se le ha preguntado aun
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            } else {
                                //ha denegado
                                Toast.makeText(ThirdActivity.this, "Please, enable the request permission", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                    } else {
                        OlderVersions(phoneNumber);
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "Insert a phone number", Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the access!!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Boton para la direccion web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                String email = "carlos.villavicencio.g7@gmail.com";
                email = "ddddddvillavicencio.g7@gmail.com";
                if (url != null && !url.isEmpty()) {
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + url));

                    //Contactos
                    Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                    //Email rapido
                    Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                    //Email completo

                    //este primer ejemplo de email completo no funciona para android 7.0 por temas de seguridad de google
//                    Intent intentMail = new Intent(Intent.ACTION_VIEW, Uri.parse(email));
//                    intentMail.setClassName("com.google.android.gm","com.google.andro‌​id.gm.ComposeActivit‌​yGmail");
//                    intentMail.setType("plain/text");
//                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mail's title");
//                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hi there, I love MyForm app, but...");
//                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"carl_13_95@hotmail.com"});

//                    Intent intentMail = new Intent(Intent.ACTION_VIEW, Uri.parse(email));
//                    intentMail.setType("message/rfc822");
//                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mail's title");
//                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hi there, I love MyForm app, but...");
//                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"carl_13_95@hotmail.com", "carl_13_95sss@hotmail.com"});
//                    startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));
                    Intent emailsss = new Intent(Intent.ACTION_SEND);
                    emailsss.putExtra(Intent.EXTRA_EMAIL, new String[]{"abc@gmail.com"});
                    emailsss.putExtra(Intent.EXTRA_SUBJECT, "Sunject Text Here..");
                    emailsss.putExtra(Intent.EXTRA_TEXT, "");
                    emailsss.setType("message/rfc822");
                    startActivity(Intent.createChooser(emailsss, "Send Mail Using :"));
//                    startActivity(intentMail);
                    //ejemplo para android 7.0 sacado de
                    //https://medium.com/@cketti/android-sending-email-using-intents-3da63662c58f
                    String subject = "Mail's title";
                    String bodyText = "Hi there, I love MyForm app, but...";
                    String mailto = "mailto:bob@example.org" +
                            "?cc=" + "alice@example.com" +
                            "&subject=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(bodyText);

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));
//                    startActivity(emailIntent);


                    //telefono 2, sin permisos requeridos
                    Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:982152323"));
//                    startActivity(intentWeb);
//                    startActivity(intentContacts);
//                    startActivity(intentMailTo);
//                    startActivity(intentMail);
//                    startActivity(intentPhone);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Estamos en el caso del telefono
        switch (requestCode) {
            case PHONE_CALL_CODE:
                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comptrobar si ha sido aceptado o denegado la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // Condedio su permiso
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);
                    } else {
                        // No condedio su permiso
                        Toast.makeText(ThirdActivity.this, "You declined the access u.u", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private boolean CheckPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
