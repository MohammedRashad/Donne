package ieee.donn.Main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ieee.donn.R;
import ieee.donn.Services.MessagingService;

public class ConnectUsers extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvData, tvPatientName, tvPatientBlood;
    Button call, message, facebookThem;
    String name,blood,email,phone,facebook, country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_users);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        Log.d("ConnectUsers","Created");
        if (getIntent()!=null){
            Log.d("ConnectUsers","There's an action");
            if (getIntent().getAction().equals(MessagingService.ACTION_CONNECT_USERS)){
                Log.d("ConnectUsers","Right intent");

            }
        }

        if (getIntent().getExtras() != null) {
            Bundle data = getIntent().getBundleExtra("data");
            name = data.getString("name");
            blood = data.getString("blood");
            email = data.getString("email");
            phone = data.getString("phone");
            facebook = data.getString("facebook");
            country = data.getString("country");
        }

        tvData = (TextView) findViewById(R.id.data);
        tvPatientName = (TextView) findViewById(R.id.tv_patient_name);
        tvPatientBlood = findViewById(R.id.tv_patient_blood);
        call = (Button) findViewById(R.id.call);
        facebookThem = (Button) findViewById(R.id.facebook);
        message = (Button) findViewById(R.id.message);


        tvData.setText("Phone :  " + phone + "\nEmail:  " + email + "\nFacebook :  " + facebook + "\n");
        tvPatientName.setText("Name :  " + name);
        tvPatientBlood.setText("Blood Type :  " + blood);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                callIntent.setData(Uri.parse("tel:" + phone));    //this is the phone number calling
                //check permission
                //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
                //the system asks the user to grant approval.
                if (ActivityCompat.checkSelfPermission(ConnectUsers.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(ConnectUsers.this,
                            new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(callIntent);  //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri sms_uri = Uri.parse("smsto:" + phone);
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
                sms_intent.putExtra("sms_body", "Hello, I have requested blood type.");
                startActivity(sms_intent);

            }
        });

        facebookThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent
                        .putExtra(Intent.EXTRA_TEXT,
                                "Hello, I have requested blood type.");
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.facebook.orca");
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ConnectUsers.this, "Please Install Facebook Messenger", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}