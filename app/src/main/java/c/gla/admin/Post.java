package c.gla.admin;

public class Post {
    private String postid;

    private String postdesp,postTitle;
    private String uri;

    public Post(){}



    public Post(String postid, String postdesp, String uri,String postTitle) {
        this.postid = postid;
        this.postTitle = postTitle;
        this.postdesp = postdesp;

        this.uri=uri;

    }



    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public void setPostdesp(String postdesp) {
        this.postdesp = postdesp;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostdesp() {
        return postdesp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
