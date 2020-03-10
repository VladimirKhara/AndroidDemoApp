package com.example.easyclusterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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
        Toast.makeText(this,"Welcome!",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.mm_kmeans:
                Toast.makeText(getApplicationContext(), "K-means", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mm_cmeans:
                Toast.makeText(getApplicationContext(), "C-means", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mm_hierarchic:
                Toast.makeText(getApplicationContext(), "Hierarchic", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
        int x=0;

        ListView lv = findViewById(R.id.am_lv0);
        String[] clustresults = new String[Integer.parseInt(et1.getText().toString())*Integer.parseInt(et0.getText().toString())];
        for(int assignment : assignments) {
            clustresults[x] = "idx: " + x + " data :" + dataset.get(x) +  " centroid: " + instances.get(assignment);
            x++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clustresults);
        lv.setAdapter(adapter);
    }

    private static Instances getRandomDataSet(int fcount, int clustcount, int objcount) {
        ArrayList<Attribute> attrList = new ArrayList<Attribute>();
        String fname;
        for (int i=0; i<fcount; i++) {
            fname = "attr" + String.valueOf(i);
            Attribute attr = new Attribute(fname);
            attrList.add(attr);
        }
        Instances dataset = new Instances("test", attrList, 0);
        double[] proto = new double[fcount];
        DenseInstance di = new DenseInstance(1.0, proto);
        for (int i=0; i<clustcount; i++) {
            for (int j=0; j<objcount; j++) {
                for (int f=0; f<fcount; f++) {
                    proto[f] = randD(i);
                    di.setValue(f, proto[f]);
                }
                dataset.add(di);
            }
        }
        return dataset;
    }

    private static double randD(int delta) {
        return r.nextDouble() * 10 + delta*15;
    }
}
