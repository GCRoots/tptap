package tapDame.pojo;

public class FarmStatus {

    private String tapId;
    private String camera;
    private String sensor1;
    private String sensor2;
    private String sensor3;
    private String sensor4;
    private String sensor5;
    private String lastTime;
    private String humidity;

    public String getTapId() {
        return tapId;
    }

    public void setTapId(String tapId) {
        this.tapId = tapId;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getSensor1() {
        return sensor1;
    }

    public void setSensor1(String sensor1) {
        this.sensor1 = sensor1;
    }

    public String getSensor2() {
        return sensor2;
    }

    public void setSensor2(String sensor2) {
        this.sensor2 = sensor2;
    }

    public String getSensor3() {
        return sensor3;
    }

    public void setSensor3(String sensor3) {
        this.sensor3 = sensor3;
    }

    public String getSensor4() {
        return sensor4;
    }

    public void setSensor4(String sensor4) {
        this.sensor4 = sensor4;
    }

    public String getSensor5() {
        return sensor5;
    }

    public void setSensor5(String sensor5) {
        this.sensor5 = sensor5;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "FarmStatus{" +
                "tapId='" + tapId + '\'' +
                ", camera='" + camera + '\'' +
                ", sensor1='" + sensor1 + '\'' +
                ", sensor2='" + sensor2 + '\'' +
                ", sensor3='" + sensor3 + '\'' +
                ", sensor4='" + sensor4 + '\'' +
                ", sensor5='" + sensor5 + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
