package wigzo.com.wigzopushsamplenofcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.wigzo.sdk.AbstractWigzoFcmListenerService;
import com.wigzo.sdk.WigzoBaseActiviy;
import com.wigzo.sdk.WigzoInstanceIDService;
import com.wigzo.sdk.WigzoSDK;
import com.wigzo.sdk.helpers.Configuration;

public class MainActivity extends WigzoBaseActiviy {


    private String ORG_TOKEN = "";
    Button showToken, showNotification;
    TextView showDemandedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showToken = (Button) findViewById(R.id.showToken);
        showNotification = (Button) findViewById(R.id.showNotification);
        showDemandedText = (TextView) findViewById(R.id.showDemandedText);

        ORG_TOKEN = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE).getString("ORG_TOKEN", "09d8699d-aead-4bf9-a6b2-c606181956de");
        WigzoSDK.getInstance().initializeWigzoData(MainActivity.this, ORG_TOKEN);

        showToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDemandedText.setText(WigzoInstanceIDService.refreshedToken);
                showDemandedText.setText(FirebaseInstanceId.getInstance().getToken());
                Log.e("new Token", FirebaseInstanceId.getInstance().getToken());
            }
        });

        showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDemandedText.setText(AbstractWigzoFcmListenerService.msg.getData().toString());
            }
        });
    }
}
