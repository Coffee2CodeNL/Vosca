package nl.deadpixel.vosca.scanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.util.ArrayList;


public class ProductDetailActivity extends ActionBarActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        String barcode = intent.getStringExtra(ScannerActivity.EXTRA_MESSAGE);
        String url = "http://api1.nl/vosca/getdetails.php?bc=" + barcode;
        lv = (ListView) findViewById(R.id.lvIngredients);
        new GetProductData().execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
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

    private class GetProductData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    HttpResponse execute = client.execute(httpGet);
                    InputStream content = execute.getEntity().getContent();

                    BufferedReader buffer = new BufferedReader(
                            new InputStreamReader(content));
                    String s;
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            ArrayList<String> items = new ArrayList<String>();
            String ProductName = null;
            StringBuilder sb = new StringBuilder();
            JSONArray stores;
            JSONArray ingredients;
            try {
                JSONObject json = (JSONObject) new JSONTokener(result).nextValue();
                ProductName = (String) json.get("name");
                stores = json.getJSONArray("stores");
                ingredients = json.getJSONArray("ingredients");
                int storecount = stores.length();
                for (int i = 0; i < storecount; i++) {
                    try {
                        JSONObject jo = stores.getJSONObject(i);
                        String sname = (String) jo.get("name");
                        sb.append(sname);
                        sb.append(", ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                int ingredientcount = ingredients.length();
                for (int i = 0; i < ingredientcount; i++) {
                    try {
                        JSONObject jo = ingredients.getJSONObject(i);
                        String iname = (String) jo.get("name");
                        items.add(iname);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                Log.e("Scanner_ex", e.toString());
            }

            TextView tv_pn = (TextView) findViewById(R.id.tvProductName);
            tv_pn.setText(ProductName);

            TextView tv_fsa = (TextView) findViewById(R.id.tvStores);
            tv_fsa.setText(sb.substring(0, sb.length() - 2));

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, items);
            lv.setAdapter(adapter);
            //Log.e("Scanner", result);
        }
    }
}

