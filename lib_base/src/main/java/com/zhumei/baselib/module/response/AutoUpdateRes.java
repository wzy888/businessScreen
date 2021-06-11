package com.zhumei.baselib.module.response;

import android.os.Parcel;
import android.os.Parcelable;

public class AutoUpdateRes implements Parcelable {


    /**
     * address :
     * package_name :
     * version_code :
     * version_name :
     */

    private String address;
    private String package_name;
    private String version_code;
    private String version_name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    @Override
    public String toString() {
        return "AutoUpdateRes{" +
                "address='" + address + '\'' +
                ", package_name='" + package_name + '\'' +
                ", version_code='" + version_code + '\'' +
                ", version_name='" + version_name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.package_name);
        dest.writeString(this.version_code);
        dest.writeString(this.version_name);
    }

    public AutoUpdateRes() {
    }

    protected AutoUpdateRes(Parcel in) {
        this.address = in.readString();
        this.package_name = in.readString();
        this.version_code = in.readString();
        this.version_name = in.readString();
    }

    public static final Parcelable.Creator<AutoUpdateRes> CREATOR = new Parcelable.Creator<AutoUpdateRes>() {
        @Override
        public AutoUpdateRes createFromParcel(Parcel source) {
            return new AutoUpdateRes(source);
        }

        @Override
        public AutoUpdateRes[] newArray(int size) {
            return new AutoUpdateRes[size];
        }
    };
}
