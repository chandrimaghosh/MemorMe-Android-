package syedamanalam.madcourse.neu.edu.numad17s_syedamanalam.finalProject;

/**
 * Created by chandrimaghosh on 4/20/17.
 */

public class ActivityStamp {
    int id;
    String locationCloseTo;
    int activityConfidence;
    String activityDetail;
    String activityTimeStamp;
    String activityLocation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActivityConfidence() {
        return activityConfidence;
    }

    public void setActivityConfidence(int activityConfidence) {
        this.activityConfidence = activityConfidence;
    }

    public ActivityStamp()
    {

    }
    public ActivityStamp(String activityDetail,String activityTimeStamp,String locationCloseTo,int activityConfidence
    )

    {
        this.activityDetail=activityDetail;
        this.activityTimeStamp=activityTimeStamp;
        this.locationCloseTo=locationCloseTo;
        this.activityConfidence=activityConfidence;
    }
    public String getActivityDetail() {
        return activityDetail;
    }

    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    public String getActivityTimeStamp() {
        return activityTimeStamp;
    }

    public void setActivityTimeStamp(String activityTimeStamp) {
        this.activityTimeStamp = activityTimeStamp;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }
    public String getLocationCloseTo() {
        return locationCloseTo;
    }

    public void setLocationCloseTo(String locationCloseTo) {
        this.locationCloseTo = locationCloseTo;
    }

}
