package mobile.proj.spoilers.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mobile.proj.spoilers.R;
import mobile.proj.spoilers.adapter.SpoilAdapter;
import mobile.proj.spoilers.model.Spoil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static mobile.proj.spoilers.activity.LoginActivity.idUser;

public class SpoilActivity extends AppCompatActivity {
    private static final String TAG = "SpoilActivity";
    ProgressDialog progressDialog;
    private View showDialogView;
    private TextView outputTextView;

    private ArrayList<Spoil> spoilList = new ArrayList<Spoil>();
    private ListView list;
    SpoilAdapter spoilAdapter;
    //private AnnoncesAdapter annocesAdapter;
    EditText spoilTxt;
    Button spoilAdd;
    TextView nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spoil);
        final SharedPreferences sharedPreferences = SpoilActivity.this.getSharedPreferences("USER", 0);
       // progressDialog = new ProgressDialog(this);
nom = (TextView)findViewById(R.id.nom);
//nom.setText(idUser.toString());
        Bundle extras = getIntent().getExtras();
        final String imdbId,idUsr;
        imdbId = extras.getString("imdbId");
        idUsr = extras.getString("id");
    final String id =sharedPreferences.getString("id",null);



       // System.out.println(firstname);
        Log.d("tt",id);
        //Log.d("ddd", idUser);
        // volleyJsonArrayRequest("http://10.0.2.2/mobile/selectSpoil.php?idUser=6&idMovie=346364");
        RequestQueue queue = Volley.newRequestQueue(SpoilActivity.this);
        String url = "http://41.226.11.243:10080/spoilers/mobile/selectSpoil.php?idUser="+id+"&idMovie=" + imdbId;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("rs", response);

                        JSONArray spoilsArray = null;
                        Spoil spoil = null;
                        try {

                            spoilsArray = new JSONArray(response);
                            spoilList = new ArrayList<>();

                            for (int i = 0; i < spoilsArray.length(); i++) {

                                JSONObject obj = spoilsArray.getJSONObject(i);
                                spoil = new Spoil();

                                spoil.setIdSpoil(obj.getString("idSpoil"));
                                spoil.setSpoilContent(obj.getString("spoilContent"));

                                Log.d("spoil", spoil.toString());
                                spoilList.add(spoil);

                            }
////////////////////////////////////////////////////
                            list = (ListView) findViewById(R.id.listSpoil);
                            spoilAdapter = new SpoilAdapter(getApplicationContext(), spoilList);
                            list.setAdapter(spoilAdapter);
                        } catch (JSONException ex) {
                            ex.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

        // parseJSON();

        spoilAdd = (Button) findViewById(R.id.spoilAdd);
        spoilAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spoilTxt = (EditText) findViewById(R.id.spoilTxt);
                String spoilCont = spoilTxt.getText().toString();

                //System.out.println(idUsr);
                //Log.d("aaaaa",idUsr);
// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(SpoilActivity.this);
            String url = "http://41.226.11.243:10080/spoilers/mobile/addSpoil.php?idUser="+id+"&idMovie=" + imdbId + "&spoilContent=" + spoilCont;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            spoilTxt.setText("");
                            recreate();                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.getMessage());
                }
            });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
        }
        });
    }

 }
