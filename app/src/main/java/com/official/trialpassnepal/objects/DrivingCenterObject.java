package com.official.trialpassnepal.objects;

/**
 * Created by SlowhandJr. on 4/4/16.
 */
public class DrivingCenterObject {

    int dcid;
    String dcName, dcProprietor, dcAddres, dcConactNo;
    double lat, lng;

    public int getDcid() {
        return dcid;
    }

    public void setDcid(int dcid) {
        this.dcid = dcid;
    }

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public String getDcProprietor() {
        return dcProprietor;
    }

    public void setDcProprietor(String dcProprietor) {
        this.dcProprietor = dcProprietor;
    }

    public String getDcAddres() {
        return dcAddres;
    }

    public void setDcAddres(String dcAddres) {
        this.dcAddres = dcAddres;
    }

    public String getDcConactNo() {
        return dcConactNo;
    }

    public void setDcConactNo(String dcConactNo) {
        this.dcConactNo = dcConactNo;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
