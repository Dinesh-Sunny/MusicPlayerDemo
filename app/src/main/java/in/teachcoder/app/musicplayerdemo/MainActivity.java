package in.teachcoder.app.musicplayerdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;

    File[] rootContents;
    ArrayList<File> songsPlayList;

    String[] nameofSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.Get the file Path
        File pathToStorage = Environment.getExternalStorageDirectory();
        File internalPath = getFilesDir();
        ArrayList<File> allSongs = searchDirectory(pathToStorage);

        nameofSongs = new String[allSongs.size()];

        for(int x=0; x<allSongs.size();x++){
            nameofSongs[x] = allSongs.get(x).getName();
        }


        lv = (ListView) findViewById(R.id.list);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameofSongs);

        lv.setAdapter(adapter);


        if (allSongs.size()>0){
            Toast.makeText(this,"Songs added",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Songs not found",Toast.LENGTH_SHORT).show();
        }


    }

    private ArrayList<File> searchDirectory(File path) {
        ArrayList<File> songsPlayList = new ArrayList<>();
        //2.Collect and refer file objects present in the path
        rootContents = path.listFiles();

        for(File song : rootContents){

            if(song.isDirectory()){
                songsPlayList.addAll(searchDirectory(song));
            }else{
                String songName = song.getName();
                if(songName.endsWith(".mp3") || songName.endsWith(".wav") ){
                    songsPlayList.add(song);
                }

            }

        }



        return songsPlayList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
