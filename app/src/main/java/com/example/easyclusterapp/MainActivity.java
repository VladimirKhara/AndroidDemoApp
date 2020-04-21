package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    SeekBar sb;
    private static final Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.am_tv3);
        sb = findViewById(R.id.am_sb0);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        registerForContextMenu(tv);
        Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        Intent myInt;
        switch (itemId) {
            case R.id.mm_kmeans:
                Toast.makeText(getApplicationContext(), "K-means", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mm_cmeans:
                Toast.makeText(getApplicationContext(), "C-means", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mm_hierarchic:
                Toast.makeText(getApplicationContext(), "Hierarchic", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mm_settings:
                myInt = new Intent(this,ClusterSettingsActivity.class);
                startActivity(myInt);
                break;
            case R.id.mm_users:
                myInt = new Intent(this,UsersActivity.class);
                startActivity(myInt);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_main, menu);
    }

    public void Analyze(View view) throws Exception {

        EditText et0 = findViewById(R.id.am_et0);
        EditText et1 = findViewById(R.id.am_et1);
        TextView tv3 = findViewById(R.id.am_tv3);
        Instances dataset = getRandomDataSet(Integer.parseInt(tv3.getText().toString()), Integer.parseInt(et0.getText().toString()), Integer.parseInt(et1.getText().toString()));

        SimpleKMeans kmeans = new SimpleKMeans();
        kmeans.setPreserveInstancesOrder(true);
        kmeans.setDontReplaceMissingValues(true);

        kmeans.setNumClusters(Integer.parseInt(et0.getText().toString()));
        kmeans.setMaxIterations(100);
        kmeans.buildClusterer(dataset);
        Instances instances = kmeans.getClusterCentroids();
        int[] assignments = kmeans.getAssignments();
        int x = 0;

        ListView lv = findViewById(R.id.am_lv0);

        final List<ClusterResult> clusterResults = new ArrayList();
        int imgResource;
        int imgLike = R.drawable.ic_before_like;
        int imgDislike = R.drawable.ic_before_dislike;
        for (int assignment : assignments) {
            switch (assignments[x]) {
                case 1:
                    imgResource = R.drawable.spidermanmorales;
                    break;
                case 2:
                    imgResource = R.drawable.batman;
                    break;
                case 3:
                    imgResource = R.drawable.logan;
                    break;
                default:
                    imgResource = R.drawable.question;
                    break;
            }
            clusterResults.add(new ClusterResult("idx: " + x, "data: " + dataset.get(x), "centroid: " + instances.get(assignment), imgResource, imgLike, imgDislike));
            x++;
        }
        ClusterResultsAdapter clusterResultsAdapter = new ClusterResultsAdapter(this, R.layout.my_list_item_1, clusterResults);
        lv.setAdapter(clusterResultsAdapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_SHORT).show();
                ClusterResult clusterResult = clusterResults.get(i);
                switch (clusterResult.getImgRes()) {
                    case R.drawable.spidermanmorales:
                        Toast.makeText(getApplicationContext(),"It's Spider-Man",Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.batman:
                        Toast.makeText(getApplicationContext(),"It's Batman",Toast.LENGTH_SHORT).show();
                        break;
                    case R.drawable.logan:
                        Toast.makeText(getApplicationContext(),"It's Logan",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"It's me",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        registerForContextMenu(lv);
    }

    private static Instances getRandomDataSet(int fcount, int clustcount, int objcount) {
        ArrayList<Attribute> attrList = new ArrayList<Attribute>();
        String fname;
        for (int i = 0; i < fcount; i++) {
            fname = "attr" + String.valueOf(i);
            Attribute attr = new Attribute(fname);
            attrList.add(attr);
        }
        Instances dataset = new Instances("test", attrList, 0);
        double[] proto = new double[fcount];
        DenseInstance di = new DenseInstance(1.0, proto);
        for (int i = 0; i < clustcount; i++) {
            for (int j = 0; j < objcount; j++) {
                for (int f = 0; f < fcount; f++) {
                    proto[f] = randD(i);
                    di.setValue(f, proto[f]);
                }
                dataset.add(di);
            }
        }
        return dataset;
    }

    private static double randD(int delta) {
        return r.nextDouble() * 10 + delta * 15;
    }
}
