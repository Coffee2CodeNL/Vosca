package nl.deadpixel.vosca.scanner;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ScannerActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "nl.deadpixel.vosca.scanner.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
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

    public void openActivity(View view)
    {
        IntentIntegrator integrator = new IntentIntegrator(ScannerActivity.this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        try {
            if (scanResult != null) {
                Intent intent2 = new Intent(this, ProductDetailActivity.class);
                intent2.putExtra(EXTRA_MESSAGE, scanResult.getContents());
                startActivity(intent2);
            }
        } catch (Exception ex) {
            Log.e("Scanner", ex.getMessage());
        }
    }
}
