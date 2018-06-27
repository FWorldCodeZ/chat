package forever.zybelieve.com.weixinc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import Bean.StorageApplication;

public class AddFreindsActivity extends AppCompatActivity {
    StorageApplication storageApplication;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_freinds);
        storageApplication = (StorageApplication) this.getApplicationContext();
        storageApplication.getUserCode();
        editText=(EditText)findViewById(R.id.search_friend);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }
}
