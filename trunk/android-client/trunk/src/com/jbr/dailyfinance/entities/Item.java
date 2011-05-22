/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.entities;

/**
 *
 * @author jbr
 */
public class Item {
    private int _id;
    private String ean;
    private String name;
    private String manufacturer;

    public Item() {
    }

    public Item(int _id, String ean, String name, String manufacturer) {
        this._id = _id;
        this.ean = ean;
        this.name = name;
        this.manufacturer = manufacturer;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
