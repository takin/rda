package com.dewianjanimedia.rda.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dewianjanimedia.rda.activity.MainActivity;
import com.dewianjanimedia.rda.R;
import com.dewianjanimedia.rda.listener.StreamingControlListener;
import com.dewianjanimedia.rda.listener.StreamingListener;


public class HomeFragment extends Fragment implements View.OnClickListener, StreamingListener {

    private ImageButton streamingButton;
    private TextView streamingStatus;
    private TextView streamingHint;
    private StreamingControlListener mActionListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
            mActionListener = (StreamingControlListener) activity;
            throw new Exception("activity must implement Listener");
        } catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        streamingButton = (ImageButton) rootView.findViewById(R.id.streamingButton);
        streamingStatus = (TextView) rootView.findViewById(R.id.streamingStatus);
        streamingHint = (TextView) rootView.findViewById(R.id.streamingHint);

        streamingButton.setOnClickListener(this);

        // Disable tombol saat awal UI di create untuk mencegah user meng-click tombol
        // sehigga proses buffering tidak terhenti
        streamingButton.setClickable(false);

        // sebelum melakukan trigger streaming setiap kali fragment UI di create
        // cek terlebih dahulu apakah streaming sudah berjalan atau belum.
        // jika sudah, maka tidak perlu di re-trigger sehingga streaming tidak
        // terputus ketika aplikasi masuk dalam mode pause
        if(!((MainActivity)getActivity()).isPlayed() && !((MainActivity)getActivity()).isHasBeenPaused()) {
            mActionListener.startStream();
        }

        // apabila sebelumnya user pernah menekan tombol pause
        // maka ketika melakukan re-instasiate fragment panggil onStreamingStopped()
        // untuk men-set ulang text sesuai dengan state UI saat ini (streaming paused)
        // karena default text adalah 'Menghubungi server...'
        if(((MainActivity)getActivity()).isHasBeenPaused()){
            onStreamingStopped();
        }

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        // oleh karena setiap terjadi perpindahan frgament
        // ataupun fragment masuk dalam state pause, fragment otomatis
        // di re-instansiate. Meskipun streaming sedang berjalan, tapi karena
        // setting default dari fragment home dianggap streaming dalam keadaan off
        // maka text dan tombol otomatis akan masuk dalam state off.

        // Untuk itu, maka perlu dilakukan re-trigger untuk menyesuaikan state
        // dari streaming saat ini.
        if(((MainActivity)getActivity()).isPlayed()){
            onStreamingStarted();
        }
    }

    @Override
    public void onClick(View view) {
        if(((MainActivity)getActivity()).isPlayed()){
            mActionListener.pauseStream();
        } else {
            mActionListener.startStream();
        }
    }


    @Override
    public void onStreamingStarted() {
        if(isAdded()) {
            streamingButton.setClickable(true);
            streamingButton.setImageResource(R.drawable.player_play);
            streamingStatus.setText(R.string.streaming_status_on);
            streamingStatus.setTextColor(getResources().getColor(R.color.base_color));
            streamingHint.setText(R.string.streaming_hint_on);
        }
    }

    @Override
    public void onStreamingStopped() {
        if(isAdded()) {
            streamingButton.setClickable(true);
            streamingButton.setImageResource(R.drawable.player_stop);
            streamingStatus.setText(R.string.streaming_status_off);
            streamingStatus.setTextColor(getResources().getColor(R.color.alert_color));
            streamingHint.setText(R.string.streaming_hint_off);
        }
    }

    @Override
    public void onStreamingError(String cause) {
        // isAdded() perlu dilakuka untuk memastikan Fragment ini
        // sedang aktif sebelum melakukan operasi UI
        // untuk mencegah force close
        if(isAdded()) {
            streamingButton.setClickable(true);
            streamingButton.setImageResource(R.drawable.player_stop);
            streamingStatus.setText(R.string.streaming_status_off);
            streamingStatus.setTextColor(getResources().getColor(R.color.alert_color));
            streamingHint.setText(R.string.streaming_hint_offline);
        }
    }
}
