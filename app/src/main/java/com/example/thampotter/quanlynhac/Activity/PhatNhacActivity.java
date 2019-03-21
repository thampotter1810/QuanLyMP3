package com.example.thampotter.quanlynhac.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thampotter.quanlynhac.Adapter.ViewpagerPlayNhac;
import com.example.thampotter.quanlynhac.Fragment.Fragment_DanhSachPhat;
import com.example.thampotter.quanlynhac.Fragment.Fragment_PlayNhac;
import com.example.thampotter.quanlynhac.Model.Baihat;
import com.example.thampotter.quanlynhac.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class PhatNhacActivity extends AppCompatActivity {

    Toolbar toolbarphatnhac;
    TextView tvthoigianchay, tvtongthoigian;
    SeekBar seekBarplay;
    ImageButton imgbtnrandom, imgprev, imgplay, imgnext, imgrepeat;
    ViewPager viewPagerPhatNhac;
    public static ArrayList<Baihat> listbaiHat;
    public static ViewpagerPlayNhac viewpagerPlayNhacAdapter;

    Fragment_DanhSachPhat fragmentdanhSachPhat;
    Fragment_PlayNhac fragmentplayNhac;

    MediaPlayer mediaPlayer;
    int vitri = 0;
    boolean replay = false;
    boolean random = false;
    boolean next = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phat_nhac);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        anhXa();
        loadDuLieu();

        onClick();
    }

    private void onClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewpagerPlayNhacAdapter.getItem(1) != null) {
                    if (listbaiHat.size() > 0) {

                        fragmentplayNhac.Playnhac(listbaiHat.get(0).getHinhbaihat());

                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);


        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                } else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.iconpause);
                }
            }
        });

        imgrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (replay == false) {
                    if (random == true) {
                        Log.e("REPEAT", random + "");
                        random = false;
                        imgrepeat.setImageResource(R.drawable.iconsyned);
                        imgbtnrandom.setImageResource(R.drawable.iconsuffle);
                    }
                    imgrepeat.setImageResource(R.drawable.iconsyned);
                    replay = true;
                } else {
                    imgrepeat.setImageResource(R.drawable.iconrepeat);
                    replay = false;
                }
            }
        });

        imgbtnrandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (random == false) {
                    if (replay == true) {
                        replay = false;
                        imgbtnrandom.setImageResource(R.drawable.iconshuffled);
                        imgrepeat.setImageResource(R.drawable.iconrepeat);
                    }
                    imgbtnrandom.setImageResource(R.drawable.iconshuffled);
                    random = true;
                } else {
                    imgbtnrandom.setImageResource(R.drawable.iconsuffle);
                    random = false;
                }
            }
        });

        seekBarplay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listbaiHat.size() > 0) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (vitri < listbaiHat.size()) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        vitri++;
                        if (replay == true) {
                            if (vitri == 0) {
                                vitri = listbaiHat.size();

                            }
                            vitri = vitri - 1;
                        }
                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(listbaiHat.size());
                            if (index == vitri) {
                                vitri = index - 1;
                            }
                            vitri = index;
                        }
                        if (vitri > listbaiHat.size() - 1) {
                            vitri = 0;
                        }
                        new PhatNhac().execute(listbaiHat.get(vitri).getLinkbaihat());
                        fragmentplayNhac.Playnhac(listbaiHat.get(vitri).getHinhbaihat());
                        getSupportActionBar().setTitle(listbaiHat.get(vitri).getTenbaihat());
                        capNhatThoiGian();
                    }
                }
                imgprev.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgprev.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 5000);
            }
        });

        imgprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listbaiHat.size() > 0) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                    if (vitri < listbaiHat.size()) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        vitri--;
                        if (vitri < 0) {
                            vitri = listbaiHat.size() - 1;
                        }
                        if (replay == true) {

                            vitri = vitri + 1;
                        }
                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(listbaiHat.size());
                            if (index == vitri) {
                                vitri = index - 1;
                            }
                            vitri = index;
                        }

                        new PhatNhac().execute(listbaiHat.get(vitri).getLinkbaihat());
                        fragmentplayNhac.Playnhac(listbaiHat.get(vitri).getHinhbaihat());
                        getSupportActionBar().setTitle(listbaiHat.get(vitri).getTenbaihat());
                        capNhatThoiGian();
                    }
                }
                imgprev.setClickable(false);
                imgnext.setClickable(false);
                Handler handler1 = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgprev.setClickable(true);
                        imgnext.setClickable(true);
                    }
                }, 5000);
            }
        });
    }

    private void loadDuLieu() {
        Intent intent = getIntent();
        if (listbaiHat.size() > 0) {
            listbaiHat.clear();
        }

        if (intent != null) {
            if (intent.hasExtra("baihat")) {
                Baihat baihat = intent.getParcelableExtra("baihat");
                listbaiHat.add(baihat);

                Toast.makeText(this, "" + baihat.getLinkbaihat(), Toast.LENGTH_SHORT).show();
                Log.e("LINKDANHA",baihat.getLinkbaihat());
            }
            if (intent.hasExtra("danhsachbaihat")) {
                ArrayList<Baihat> list = intent.getParcelableArrayListExtra("danhsachbaihat");
                listbaiHat = list;

                for (int i = 0; i < list.size(); i++) {
                    Log.e("TTTTTTT", list.get(i).getTenbaihat());
                }
            }
        }

        fragmentplayNhac = (Fragment_PlayNhac) viewpagerPlayNhacAdapter.getItem(1);

        Log.e("LISSS", listbaiHat.size() + "");
        if (listbaiHat.size() > 0) {
            getSupportActionBar().setTitle(listbaiHat.get(0).getTenbaihat());
            new PhatNhac().execute(listbaiHat.get(0).getLinkbaihat());
            imgplay.setImageResource(R.drawable.iconpause);
            Log.e("LINKBAIHATNHAN",listbaiHat.get(0).getLinkbaihat());
        }
    }

    private void anhXa() {
        toolbarphatnhac = findViewById(R.id.toolbarphatnhac);
        tvthoigianchay = findViewById(R.id.tvthoigianchay);
        tvtongthoigian = findViewById(R.id.tvtongthoigian);
        seekBarplay = findViewById(R.id.seekbarbaihat);
        imgbtnrandom = findViewById(R.id.imagebuttonrandom);
        imgprev = findViewById(R.id.imagebuttonprev);
        imgplay = findViewById(R.id.imagebuttonplay);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        viewPagerPhatNhac = findViewById(R.id.viewpagerphatnhac);
        listbaiHat = new ArrayList<>();
        setSupportActionBar(toolbarphatnhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarphatnhac.setTitleTextColor(Color.WHITE);
        toolbarphatnhac.setTitle("Trình phát nhạc");
        toolbarphatnhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                mediaPlayer.stop();
                listbaiHat.clear();
            }
        });

        fragmentdanhSachPhat = new Fragment_DanhSachPhat();
        fragmentplayNhac = new Fragment_PlayNhac();
        viewpagerPlayNhacAdapter = new ViewpagerPlayNhac(getSupportFragmentManager());
        viewpagerPlayNhacAdapter.addfragment(fragmentdanhSachPhat);
        viewpagerPlayNhacAdapter.addfragment(fragmentplayNhac);
        viewPagerPhatNhac.setAdapter(viewpagerPlayNhacAdapter);

    }

    class PhatNhac extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();

                    }
                });

                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            thoiGianChay();
            capNhatThoiGian();
        }
    }

    private void thoiGianChay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        tvtongthoigian.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBarplay.setMax(mediaPlayer.getDuration());

    }

    private void capNhatThoiGian() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBarplay.setProgress(mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tvthoigianchay.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            next = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }, 1000);
        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (next == true) {
                    if (vitri < listbaiHat.size()) {
                        imgplay.setImageResource(R.drawable.iconpause);
                        vitri++;
                        if (replay == true) {
                            if (vitri == 0) {
                                vitri = listbaiHat.size();

                            }
                            vitri = vitri - 1;
                        }
                        if (random == true) {
                            Random random = new Random();
                            int index = random.nextInt(listbaiHat.size());
                            if (index == vitri) {
                                vitri = index - 1;
                            }
                            vitri = index;
                        }
                        if (vitri > listbaiHat.size() - 1) {
                            vitri = 0;
                        }
                        new PhatNhac().execute(listbaiHat.get(vitri).getLinkbaihat());
                        fragmentplayNhac.Playnhac(listbaiHat.get(vitri).getHinhbaihat());
                        getSupportActionBar().setTitle(listbaiHat.get(vitri).getTenbaihat());
                    }

                    imgprev.setClickable(false);
                    imgnext.setClickable(false);
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imgprev.setClickable(true);
                            imgnext.setClickable(true);
                        }
                    }, 5000);
                    next = false;
                    handler1.removeCallbacks(this);
                } else {
                    handler1.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}
