package c.gla.admin;

class Volunteer {
    private String volid,volname,volemail,voladdress, voluri;

    public Volunteer(String volid, String volname, String volemail, String voladdress, String voluri) {
        this.volid = volid;
        this.volname = volname;
        this.volemail = volemail;
        this.voladdress = voladdress;
        this.voluri = voluri;
    }

    public String getVolid() {
        return volid;
    }

    public void setVolid(String volid) {
        this.volid = volid;
    }

    public String getVolname() {
        return volname;
    }

    public void setVolname(String volname) {
        this.volname = volname;
    }

    public String getVolemail() {
        return volemail;
    }

    public void setVolemail(String volemail) {
        this.volemail = volemail;
    }

    public String getVoladdress() {
        return voladdress;
    }

    public void setVoladdress(String voladdress) {
        this.voladdress = voladdress;
    }

    public String getVoluri() {
        return voluri;
    }

    public void setVoluri(String voluri) {
        this.voluri = voluri;
    }

    public Volunteer(){

    }
}
