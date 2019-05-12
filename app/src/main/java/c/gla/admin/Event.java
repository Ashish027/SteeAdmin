package c.gla.admin;

public class Event {
    private String eventid;
    private String eventname;
    private String eventdate;
    private String eventtime;
    private String eventdescription;


    public Event(){

    }

    public Event(String eventid, String eventname, String eventdate, String eventtime, String eventdescription) {
        this.eventid = eventid;
        this.eventname = eventname;
        this.eventdate = eventdate;
        this.eventtime = eventtime;
        this.eventdescription = eventdescription;
    }

    public String getEventid() {
        return eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public String getEventdate() {
        return eventdate;
    }

    public String getEventtime() {
        return eventtime;
    }

    public String getEventdescription() {
        return eventdescription;
    }
}
