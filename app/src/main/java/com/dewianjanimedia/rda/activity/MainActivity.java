package com.dewianjanimedia.rda.activity;

import java.util.Locale;

import android.media.AudioTrack;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.dewianjanimedia.rda.R;
import com.dewianjanimedia.rda.listener.StreamingControlListener;
import com.dewianjanimedia.rda.listener.StreamingListener;
import com.dewianjanimedia.rda.fragment.HomeFragment;
import com.dewianjanimedia.rda.fragment.JadwalFragment;
import com.dewianjanimedia.rda.fragment.ProfilFragment;
import com.spoledge.aacdecoder.AACPlayer;
import com.spoledge.aacdecoder.PlayerCallback;


public class MainActivity extends AppCompatActivity implements ActionBar.TabListener, StreamingControlListener, PlayerCallback {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private StreamingListener streamingListener;

    /**
     * flag untuk menandakan user pernah menekan tombol pause
     * (streaming pernah berjalan kemudian di pause)
     * flag ini dibutuhkan untuk mencegah player autoplay
     * apabila user melakukan navigasi ke fragment lain selain home
     * kemudian kembali ke home.
     * karena setiap kali menuju home, maka UI akan di build ulang dan
     * secara default, sistem akan mencoba melakukan autoplay
     * (HomeFragment, line 63-65)
     *
     * apabila pernah di pause (true), maka ketika proses re-draw UI
     * sistem tidak akan melakukan autoplay
     */
    private boolean hasBeenPaused = false;

    // flag untuk menandakan state dari streaming
    // -1 = stoped, 1 = played, 2= buffering
    private int playStatus = -1;

    private int bufferSize = 2000;

    private AACPlayer mediaPlayer;
    private static final String RADIO_CHANNEL = "http://103.237.33.44:8000/user300";

//    mp.setDataSource("http://usa8-vn.mixstream.net:8138");
//    mp.setDataSource("http://103.237.33.44:8000/user300");
//    mp.setDataSource("http://173.224.124.217:8160");
//    mp.setDataSource("http://ina.vinhostmedia.com:8000/user300");

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    /**
     * Method ini digunakan oleh fragment untuk mengecek apakah streaming
     * sudah berjalan atau belum
     * @return boolean
     */
    public boolean isPlayed(){
        return playStatus == 1;
    }

    public boolean isHasBeenPaused(){
        return hasBeenPaused;
    }

    @Override
    public void startStream() {

        if(streamingListener == null){
            streamingListener = (StreamingListener) mSectionsPagerAdapter.getFragment(0);
        }
        if(mediaPlayer == null){
            mediaPlayer = new AACPlayer(this);
            mediaPlayer.setAudioBufferCapacityMs(3000);
            mediaPlayer.setDecodeBufferCapacityMs(1500);
            mediaPlayer.setResponseCodeCheckEnabled(false);
        }

        mediaPlayer.playAsync(RADIO_CHANNEL,32);
    }

    @Override
    public void pauseStream(){
        hasBeenPaused = true;
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        pauseStream();
        mediaPlayer = null;
    }

    @Override
    public void playerStarted() {
        playStatus = 1;

        // oleh karena implementasi dari onStreamingStarted() melakukan
        // manipulasi terhadap UI (di dalam fragment Home)
        // maka callee nya juga harus dilakukan di dalam thread UI
        // agar proses manipulasi UI dapat dilakukan
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                streamingListener.onStreamingStarted();
            }
        });
    }

    @Override
    public void playerStopped(int i) {
        playStatus = -1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                streamingListener.onStreamingStopped();
            }
        });
    }

    @Override
    public void playerException(final Throwable throwable) {
        this.playStatus = -1;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                streamingListener.onStreamingError(throwable.getMessage());
            }
        });
    }

    @Override
    public void playerPCMFeedBuffer(boolean b, final int i, int i1) {}

    @Override
    public void playerMetadata(String s, String s1) {}

    @Override
    public void playerAudioTrackCreated(AudioTrack audioTrack) {}

    /**
     * Adapter untuk membangun tab fragment
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SparseArray<Fragment> fragments = new SparseArray<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return HomeFragment.newInstance();
                case 1:
                    return JadwalFragment.newInstance();
                case 2:
                    return ProfilFragment.newInstance();
            }
            return null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fragments.put(position, fragment);
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.home).toUpperCase(l);
                case 1:
                    return getString(R.string.jadwal_acara).toUpperCase(l);
                case 2:
                    return getString(R.string.profil).toUpperCase(l);
            }
            return null;
        }

        public Fragment getFragment(int position){
            return fragments.get(position);
        }
    }

}
