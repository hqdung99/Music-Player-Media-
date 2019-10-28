package com.example.zingmp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<DataMusic> dataMusics;
    ListView listView;
    private static CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        listView = findViewById(R.id.listSong);
        dataMusics = createListMusic();


        int heightListView = 140 * 4 / 3 * dataMusics.size() + 1;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)listView.getLayoutParams();
        lp.height = heightListView;
        listView.setLayoutParams(lp);

        adapter = new CustomAdapter(dataMusics, getApplicationContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DataMusic dataMusic = dataMusics.get(position);

                Intent intent = new Intent(MainActivity.this, PlayMusicActivity.class);

                intent.putExtra("order", dataMusic.numerical_order);
                intent.putExtra("name_song", dataMusic.name_song);
                intent.putExtra("name_singer", dataMusic.name_singer);

                MainActivity.this.startActivity(intent);

//                Snackbar.make(view, dataMusic.getNumerical_order()+"\n"+dataMusic.getName_song()
//                        +" API: "+ dataMusic.getName_song(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
            }
        });

    }

    private ArrayList<DataMusic> createListMusic() {
        dataMusics = new ArrayList<>();
        dataMusics.add(new DataMusic("1", "Đôi Bờ", "Lê Cát Trọng Lý"));
        dataMusics.add(new DataMusic("2", "Đó Chỉ Là Thành Phố Của Anh", "Lux"));
        dataMusics.add(new DataMusic("3", "Phố Không Mùa", "Dương Trường Giang, Bùi Anh Tuấn"));
        dataMusics.add(new DataMusic("4", "Chiều Nay Không Có Mưa Bay", "Trung Quân Idol"));
        dataMusics.add(new DataMusic("5", "Có Chàng Trai Viết Lên Cây", "Phan Mạnh Quỳnh"));
        dataMusics.add(new DataMusic("6", "Sóng Gió", "Jack, K-ICM"));

        return dataMusics;
    }

    public static ArrayList<DataMusic> getDataMusics() {
        return dataMusics;
    }


}
