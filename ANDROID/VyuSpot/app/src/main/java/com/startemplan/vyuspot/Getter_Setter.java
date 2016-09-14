package com.startemplan.vyuspot;

/**
 * Created by Salman on 2/24/2016.
 */
public class Getter_Setter {




    String _NAME;
    String _LOCATION;
    String _NUMBER;
    String _LANDMARK;
    String _SPOTID;
    String _NEED;
    String _CAREDBY;
    String _CARE1;


    public Getter_Setter(){}


    public Getter_Setter(String name,String mail,String num,String service,String spot,String detail,String cared,String care1){

        this._NAME = name;
        this._LOCATION = mail;
        this._NUMBER = num;
        this._LANDMARK=service;
        this._SPOTID=spot;
        this._NEED = detail;
        this._CAREDBY = cared;
        this._CARE1 = care1;

    }


    public String get_NAME() {
        return _NAME;
    }

    public String get_LOCATION() {
        return _LOCATION;
    }

    public String get_NUMBER() {
        return _NUMBER;
    }

    public String get_LANDMARK() {
        return _LANDMARK;
    }

    public String get_SPOTID() {
        return _SPOTID;
    }

    public String get_NEED() {
        return _NEED;
    }

    public String get_CAREDBY() {
        return _CAREDBY;
    }

    public String get_CARE1() {
        return _CARE1;
    }

    public void set_NAME(String _NAME) {
        this._NAME = _NAME;
    }

    public void set_LOCATION(String _LOCATION) {
        this._LOCATION = _LOCATION;
    }

    public void set_NUMBER(String _NUMBER) {
        this._NUMBER = _NUMBER;
    }

    public void set_LANDMARK(String _LANDMARK) {
        this._LANDMARK = _LANDMARK;
    }

    public void set_SPOTID(String _BRAND) {
        this._SPOTID = _BRAND;
    }

    public void set_NEED(String _NEED) {
        this._NEED = _NEED;
    }

    public void set_CAREDBY(String _CAREDBY) {
        this._CAREDBY = _CAREDBY;
    }

    public void set_CARE1(String _CARE1) {
        this._CARE1 = _CARE1;
    }
}
