
package com.official.trialpassnepal.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrivingCenter {

@SerializedName("dcid")
@Expose
private String dcid;
@SerializedName("dcName")
@Expose
private String dcName;
@SerializedName("dcProprietor")
@Expose
private String dcProprietor;
@SerializedName("dcContactno")
@Expose
private String dcContactno;
@SerializedName("latitude")
@Expose
private String latitude;
@SerializedName("longitude")
@Expose
private String longitude;
@SerializedName("published")
@Expose
private String published;

/**
* 
* @return
* The dcid
*/
public String getDcid() {
return dcid;
}

/**
* 
* @param dcid
* The dcid
*/
public void setDcid(String dcid) {
this.dcid = dcid;
}

/**
* 
* @return
* The dcName
*/
public String getDcName() {
return dcName;
}

/**
* 
* @param dcName
* The dcName
*/
public void setDcName(String dcName) {
this.dcName = dcName;
}

/**
* 
* @return
* The dcProprietor
*/
public String getDcProprietor() {
return dcProprietor;
}

/**
* 
* @param dcProprietor
* The dcProprietor
*/
public void setDcProprietor(String dcProprietor) {
this.dcProprietor = dcProprietor;
}

/**
* 
* @return
* The dcContactno
*/
public String getDcContactno() {
return dcContactno;
}

/**
* 
* @param dcContactno
* The dcContactno
*/
public void setDcContactno(String dcContactno) {
this.dcContactno = dcContactno;
}

/**
* 
* @return
* The latitude
*/
public String getLatitude() {
return latitude;
}

/**
* 
* @param latitude
* The latitude
*/
public void setLatitude(String latitude) {
this.latitude = latitude;
}

/**
* 
* @return
* The longitude
*/
public String getLongitude() {
return longitude;
}

/**
* 
* @param longitude
* The longitude
*/
public void setLongitude(String longitude) {
this.longitude = longitude;
}

/**
* 
* @return
* The published
*/
public String getPublished() {
return published;
}

/**
* 
* @param published
* The published
*/
public void setPublished(String published) {
this.published = published;
}

}