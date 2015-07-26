package com.dewianjanimedia.rda.model;

/**
 * Created by syamsul on 7/26/15.
 */
public class JadwalAcara {

    private String time;
    private String programName;
    private String day;
    private String announcerName;

    public JadwalAcara(){

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

    public String getAnnouncerName(){
        return this.announcerName;
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

    public void setAnnouncerName(String name){
        this.announcerName = name;
    }
}
