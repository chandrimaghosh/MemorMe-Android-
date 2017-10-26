package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

/**
 * Created by chandrimaghosh on 4/22/17.
 */


public class UserClass {

    int id;
    String name;
    String officeLocation;
    String homeLocation;
    int start;
    int end;


    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public UserClass()
    {

    }
    public UserClass(String name,String officeLocation,String homeLocation,int start,int end)
    {

        this.name=name;
        this.officeLocation=officeLocation;
        this.homeLocation=homeLocation;
        this.start=start;
        this.end=end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(String homeLocation) {
        this.homeLocation = homeLocation;
    }




}
