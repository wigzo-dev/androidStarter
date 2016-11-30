package wigzo.com.wigzopushsampleappwithfcm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(MainActivity.this, MyFirebaseInstanceIDService.class));

        Button logTokenButton = (Button) findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.e("getId________", FirebaseInstanceId.getInstance().getId());
                Log.e("getId________", FirebaseInstanceId.getInstance().getToken());
                Log.e("getId________", FirebaseInstanceId.getInstance().getToken());

                // Log and toast
                Log.d("Token: ", "MainActivity: " + FirebaseInstanceId.getInstance().getToken());
                Toast.makeText(MainActivity.this, FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
