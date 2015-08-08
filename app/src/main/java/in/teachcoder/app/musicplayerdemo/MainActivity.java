package in.teachcoder.app.musicplayerdemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;

    File[] rootContents;
    ArrayList<File> songsPlayList;

    String[] nameofSongs;
    static MediaPlayer iPod = new MediaPlayer();
    static MediaPlayer iPod2 = new MediaPlayer();
    ArrayList<File> allSongs;
    private Button play;
    private Button back;
    private Button forward;
    LinearLayout buttonLayoutRef;

    private static int currentSong = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.Get the file Path
        File pathToStorage = Environment.getExternalStorageDirectory();
        File internalPath = getFilesDir();
         allSongs = searchDirectory(pathToStorage);

        nameofSongs = new String[allSongs.size()];

        for(int x=0; x<allSongs.size();x++){
            nameofSongs[x] = allSongs.get(x).getName();
        }


        lv = (ListView) findViewById(R.id.list);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameofSongs);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                iPod.stop();
                iPod.release();
                Uri u = Uri.fromFile(allSongs.get(i));

                Toast.makeText(MainActivity.this, "Songs CLicked " + u.getLastPathSegment(), Toast.LENGTH_SHORT).show();

                iPod = MediaPlayer.create(MainActivity.this, u);
                iPod.start();
                MainActivity.currentSong = i;

                if(iPod.isPlaying()){

                    play.setText("||");
                }
                else{

                    play.setText(">");

                }


            }
        });

        play = (Button) findViewById(R.id.pp);
        back = (Button) findViewById(R.id.back);
        forward = (Button) findViewById(R.id.forward);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iPod.isPlaying()){
                    iPod.pause();
                    play.setText("||");
                }
                else{
//                    Uri x = Uri.fromFile(allSongs.get(0));
//                    iPod = MediaPlayer.create(MainActivity.this, x);
//                    iPod.start();
                    play.setText(">");
                    iPod.start();
                }
//                else{
//
//                    play.setText(">");
//                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iPod.stop();
                iPod.release();

                currentSong = currentSong-1;

                if (currentSong == -1){
                    currentSong = allSongs.size()-1;
                }

                Uri uri = Uri.fromFile(allSongs.get(currentSong));
                iPod = MediaPlayer.create(MainActivity.this,uri);
                iPod.start();

                Toast.makeText(MainActivity.this, "Previous song "+ uri.getLastPathSegment(), Toast.LENGTH_SHORT).show();

                if(!iPod.isPlaying()){

                    play.setText("||");
                }
                else{

                    play.setText(">");

                }

            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 currentSong = currentSong + 1;
                if(allSongs.size() == currentSong){
                    currentSong = 0;
                }

                Uri uri = Uri.fromFile(allSongs.get(currentSong));

                iPod.stop();
                iPod.release();

                iPod = MediaPlayer.create(MainActivity.this, uri);
                iPod.start();
                Toast.makeText(MainActivity.this,"Next Song"+uri.getLastPathSegment(),Toast.LENGTH_SHORT).show();


            }
        });



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
