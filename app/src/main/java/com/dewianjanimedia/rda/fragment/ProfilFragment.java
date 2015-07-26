package com.dewianjanimedia.rda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dewianjanimedia.rda.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {

    public static ProfilFragment newInstance(){
        return new ProfilFragment();
    }

    public ProfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_profil,container,false);

        Bundle arg = getArguments();

        return rootView;
    }


}