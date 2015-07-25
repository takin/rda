package com.dewianjanimedia.rda;

import android.media.AudioTrack;

import com.spoledge.aacdecoder.PlayerCallback;

/**
 * Created by syamsul on 7/25/15.
 */
public interface StreamingListener {

    void onStreamingStarted();
    void onStreamingStopped();
    void onStreamingError(String cause);
}
