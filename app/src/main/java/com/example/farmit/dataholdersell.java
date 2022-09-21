package com.example.farmit;

public class dataholdersell {
    String addr,contact,cost,location,oname,pdesc,pname,purl;

    public dataholdersell(String addr, String contact, String cost, String location, String oname, String pdesc, String pname, String purl) {
        this.addr = addr;
        this.contact = contact;
        this.cost = cost;
        this.location = location;
        this.oname = oname;
        this.pdesc = pdesc;
        this.pname = pname;
        this.purl = purl;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOname() {
        return oname;
    }

    public void setOname(String oname) {
        this.oname = oname;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
