package c.gla.admin;

class Donation {

    String dId;
    String dname;
    String demail;
    String dphone;
    String doccupation;
    String dmoney;
    String dfood;
    String dcloth;
    String dstationary;

    public Donation(String dId, String dname, String demail, String dphone, String doccupation,
                    String dmoney, String dfood, String dcloth, String dstationary) {
        this.dId = dId;
        this.dname = dname;
        this.demail = demail;
        this.dphone = dphone;
        this.doccupation = doccupation;
        this.dmoney = dmoney;
        this.dfood = dfood;
        this.dcloth = dcloth;
        this.dstationary = dstationary;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDemail() {
        return demail;
    }

    public void setDemail(String demail) {
        this.demail = demail;
    }

    public String getDphone() {
        return dphone;
    }

    public void setDphone(String dphone) {
        this.dphone = dphone;
    }

    public String getDoccupation() {
        return doccupation;
    }

    public void setDoccupation(String doccupation) {
        this.doccupation = doccupation;
    }

    public String getDmoney() {
        return dmoney;
    }

    public void setDmoney(String dmoney) {
        this.dmoney = dmoney;
    }

    public String getDfood() {
        return dfood;
    }

    public void setDfood(String dfood) {
        this.dfood = dfood;
    }

    public String getDcloth() {
        return dcloth;
    }

    public void setDcloth(String dcloth) {
        this.dcloth = dcloth;
    }

    public String getDstationary() {
        return dstationary;
    }

    public void setDstationary(String dstationary) {
        this.dstationary = dstationary;
    }


    public Donation(){

    }
}
