package wigzo.com.wigzopushsamplenofcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wigzo.sdk.AbstractWigzoFcmListenerService;
import com.wigzo.sdk.WigzoInstanceIDService;
import com.wigzo.sdk.WigzoSDK;

public class MainActivity extends AppCompatActivity {


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

        ORG_TOKEN = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE).getString("ORG_TOKEN", "56065c5b-db30-4b89-bd76-0a9c2938c90b");
        WigzoSDK.getInstance().initializeWigzoData(getApplicationContext(), ORG_TOKEN);

        showToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDemandedText.setText(WigzoInstanceIDService.refreshedToken);
            }
        });

        showNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDemandedText.setText(AbstractWigzoFcmListenerService.msg.getNotification().getBody());
            }
        });
    }
}
