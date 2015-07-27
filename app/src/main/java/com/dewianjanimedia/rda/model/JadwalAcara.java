package com.dewianjanimedia.rda.model;

/**
 * Created by syamsul on 7/26/15.
 */
public class JadwalAcara {

    public static final String ID = "ID";
    public static final String NAMA = "NAMA";
    public static final String JAM = "JAM";
    public static final String TIPE = "TIPE";
    public static final String HARI = "HARI";
    public static final String REGULER = "Acara Harian";
    public static final String MINGGUAN = "Acara Mingguan";

    private String time;
    private String programName;
    private String day;
    private String programType;
    private int id;

    public JadwalAcara(String jam, String nama, String tipe, String hari){
        if(nama != null) {
            setProgramName(nama);
        }
        if(jam != null){
            setTime(jam);
        }
        if(hari != null){
            setDay(hari);
        }
        if (tipe != null) {
            setProgramType(tipe);
        }
    }


    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTime(){
        return this.time;
    }

    public String getProgramName(){
        return this.programName;
    }

    public String getDay(){
        return this.day;
    }

    public String getProgramType(){
        return this.programType;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setProgramName(String programName){
        this.programName = programName;
    }

    public void setDay(String day){
        this.day = day;
    }

    public void setProgramType(String type){
        this.programType = type;
    }
}
