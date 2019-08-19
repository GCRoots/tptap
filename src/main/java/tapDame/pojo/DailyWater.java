package tapDame.pojo;

/**
 * @author shipengfei
 * @data 19-8-17
 */
public class DailyWater {
    private String idDate;
    private String todayUsed;
    private String purpose;

    public String getIdDate() {
        return idDate;
    }

    public void setIdDate(String idDate) {
        this.idDate = idDate;
    }

    public String getTodayUsed() {
        return todayUsed;
    }

    public void setTodayUsed(String todayUsed) {
        this.todayUsed = todayUsed;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
