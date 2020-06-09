package es.usal.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "es.usal.myapplication.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void testClick(View view) {
        Intent intent = new Intent(this, DisplayMSG.class);
        EditText txt_test = (EditText) findViewById(R.id.txt_test);
        String message = txt_test.getText().toString();
        if(message.equalsIgnoreCase(""))
            message = "HOLA";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
