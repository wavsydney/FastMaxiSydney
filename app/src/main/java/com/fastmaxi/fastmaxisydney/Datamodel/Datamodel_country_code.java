package com.fastmaxi.fastmaxisydney.Datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datamodel_country_code {


    @SerializedName("dial_code")
    @Expose
    String dial_Code;
    @SerializedName("code")
    @Expose
    String code;

    public Datamodel_country_code(String dial_Code, String code) {
        this.dial_Code = dial_Code;
        this.code = code;
    }

    public String getDial_Code() {
        return dial_Code;
    }

    public void setDial_Code(String dial_Code) {
        this.dial_Code = dial_Code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code + " " + dial_Code;
    }
}
