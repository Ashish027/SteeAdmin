package c.gla.admin;

class News {
    private String nId,Headline,Description, Image;

    public News() {
    }

    public News(String nId, String headline, String description, String image) {
        this.nId = nId;
        Headline = headline;
        Description = description;
        Image = image;
    }

    public String getnId() {
        return nId;
    }

    public void setnId(String nId) {
        this.nId = nId;
    }

    public String getHeadline() {
        return Headline;
    }

    public void setHeadline(String headline) {
        Headline = headline;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
}
