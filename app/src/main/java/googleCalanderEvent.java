import com.google.firebase.Timestamp;

public class googleCalanderEvent {

    private int id;
    private String name;
    private String place;
    private String city;
    private String address;
    private String postCode;
    private String date;
    private String time;
    private String description;
    private Timestamp timestamp;
    private boolean done;




    public googleCalanderEvent(int id,String name,String place,String city,String address,String postCode,String date,String time,String description){
        this.id=id;
        this.name=name;
        this.place=place;
        this.city=city;
        this.address=address;
        this.postCode=postCode;
        this.date=date;
        this.time=time;
        this.description=description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public boolean isDone() {
        return done;
    }

    public String getTime() {
        return time;
    }
    public Timestamp getTimestamp(){
        return timestamp;
    }
}
