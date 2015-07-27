package com.dewianjanimedia.rda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dewianjanimedia.rda.R;
import com.dewianjanimedia.rda.adapter.JadwalAdapter;
import com.dewianjanimedia.rda.helper.JadwalDB;
import com.dewianjanimedia.rda.helper.JadwalQuery;
import com.dewianjanimedia.rda.model.JadwalAcara;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JadwalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JadwalFragment extends ListFragment {

    JadwalDB dbHelper;
    JadwalQuery query;


    public static JadwalFragment newInstance() {
        return new JadwalFragment();
    }

    public JadwalFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new JadwalDB(getActivity());
        query = new JadwalQuery(dbHelper);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_jadwal, container, false);

        JadwalAdapter adapter = new JadwalAdapter(getActivity(),generateJadwalDB());
        setListAdapter(adapter);

        return rootView;
    }

    private List<JadwalAcara> generateJadwalDB(){

        String[] nah = new String[]{
                "SIROH (Siraman Rohani)",
                "Silaturrahmi Pagi",
                "Acara Khusus Pendidikan",
                "Salam Dangdut",
                "Ngaji, Adzan, Lagu Islami/Perjuangan",
                "Silaturrahmi Siang",
                "INSPIRASI (Informasi, Spirit dan Kreasi)",
                "Ngaji, Adzan, Lagu Islami",
                "Pilihan Pendengar",
                "DA'I (Dakwah Agama Islam)",
                "Ngaji, Adzan, Dakwah",
                "Silaturrahmi Malam",
                "SEJATI (Setia Menjaga Hati)"};
        String[] jah = new String[]{
                "05.30 – 06.15 WITA",
                "06.30 – 07.30 WITA",
                "07.30 – 10.00 WITA",
                "10.00 – 11.30 WITA",
                "11.30 – 12.30 WITA",
                "12.30 – 13.30 WITA",
                "13.30 – 15.00 WITA",
                "15.00 – 16.00 WITA",
                "16.00 – 17.30 WITA",
                "17.30 – 18.00 WITA",
                "18.00 - 20.00 WITA",
                "20.00 – 22.00 WITA",
                "22.00 – 23.00 WITA"
        };

        String[] nam = new String[]{
               "Tanya Jawab Agama Islam",
                "Universitaria",
                "Hikayat",
                "Hizib Nahdlatul Wathan",
                "Al-Barzanji",
                "Panggung Anak Soleh"
        };

        String[] jam = new String[]{
                "Selasa Jam 20.00 - 22.00 WITA",
                "Sabtu Jam 16.00 - 17.00 WITA",
                "Kamis Jam 22.00 - 23.00 WITA",
                "Ahad Jam 19.00 WITA - Selesai",
                "Kamis Jam 19.00 WITA - Selesai",
                "Ahad Jam 10.00 - 12.00 WITA"
        };

        List<JadwalAcara> acara = new ArrayList<>();

        for(int i = 0; i < nah.length; i++){
            acara.add(new JadwalAcara(jah[i],nah[i],JadwalAcara.REGULER,null));
        }

        for(int i = 0; i < nam.length; i++){
            acara.add(new JadwalAcara(jam[i],nam[i],JadwalAcara.MINGGUAN,null));
        }

        return acara;
    }
}
