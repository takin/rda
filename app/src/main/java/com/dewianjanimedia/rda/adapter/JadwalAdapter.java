package com.dewianjanimedia.rda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dewianjanimedia.rda.R;
import com.dewianjanimedia.rda.model.JadwalAcara;

import java.util.List;

/**
 * Created by syamsul on 7/27/15.
 */
public class JadwalAdapter extends BaseAdapter {

    private List<JadwalAcara> dataJadwal;
    private Context mContext;

    public JadwalAdapter(Context context, List<JadwalAcara> jadwal){
        mContext = context;
        dataJadwal = jadwal;
    }


    @Override
    public int getCount() {
        return dataJadwal.size();
    }

    @Override
    public Object getItem(int i) {
        return dataJadwal.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.jadwal_acara_card,viewGroup,false);
        }

        ViewHolder vh = new ViewHolder();
        vh.jam_acara = (TextView) view.findViewById(R.id.jam_acara);
        vh.nama_acara = (TextView) view.findViewById(R.id.nama_acara);
        vh.tipe_acara = (TextView) view.findViewById(R.id.tipe_acara);

        JadwalAcara item = (JadwalAcara) getItem(i);

        vh.jam_acara.setText(item.getTime());
        vh.tipe_acara.setText(item.getProgramType());
        vh.nama_acara.setText(item.getProgramName());

        view.setTag(vh);

        return view;
    }

    private class ViewHolder {
        public TextView nama_acara,tipe_acara,jam_acara;
    }
}