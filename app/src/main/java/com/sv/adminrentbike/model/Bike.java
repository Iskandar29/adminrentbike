package com.sv.adminrentbike.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Bike implements Parcelable {
    private String id, kode, merk, jenis, warna, hargasewa;

    public Bike() {

    }

    protected Bike(Parcel in) {
        id = in.readString();
        kode = in.readString();
        merk = in.readString();
        jenis = in.readString();
        warna = in.readString();
        hargasewa = in.readString();
    }

    public static final Creator<Bike> CREATOR = new Creator<Bike>() {
        @Override
        public Bike createFromParcel(Parcel in) {
            return new Bike(in);
        }

        @Override
        public Bike[] newArray(int size) {
            return new Bike[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public String getHargasewa() {
        return hargasewa;
    }

    public void setHargasewa(String hargasewa) {
        this.hargasewa = hargasewa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(kode);
        parcel.writeString(merk);
        parcel.writeString(jenis);
        parcel.writeString(warna);
        parcel.writeString(hargasewa);
    }
}
