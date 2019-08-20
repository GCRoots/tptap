package tapDame.pojo;

public class Data {
//    硬件控制
    private String upTimer;

    private String humidity;

    private String infrared;

    private String image;

    private String sound;

    private String judge; //  T/F

    private String water;

    private String sensor1;
    private String sensor2;
    private String sensor3;
    private String sensor4;
    private String sensor5;

    private String tapId;

    private String tmp;

    private String need;

//    登录注册
    private String phone;

    private String password;

    private String idCode;

    private String success; //  T/F

//    软件——家用
    private String microphone; //  T/F

    private String camera; //  T/F

    private String ifUsed; //  T/F

    private String todayUsed;

    private String purpose;

    private String idDate;

    private String weather;

//    软件——农用
    private String hostName;

    private String count;

    private String cameras; //  {"Cameras":["Host1":"T/F","Host2":"T/F"],......}

    private String fault; //  {"Fault":["Sensor1":"T/F","Sensor2":"T/F"],......}

    private String sensor; //  T/F

    private String lastTime;

//  Humidity


    public String getUpTimer() {
        return upTimer;
    }

    public void setUpTimer(String upTimer) {
        this.upTimer = upTimer;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getInfrared() {
        return infrared;
    }

    public void setInfrared(String infrared) {
        this.infrared = infrared;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getJudge() {
        return judge;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water;
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

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMicrophone() {
        return microphone;
    }

    public void setMicrophone(String microphone) {
        this.microphone = microphone;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getIfUsed() {
        return ifUsed;
    }

    public void setIfUsed(String ifUsed) {
        this.ifUsed = ifUsed;
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCameras() {
        return cameras;
    }

    public void setCameras(String cameras) {
        this.cameras = cameras;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getSensor() {
        return sensor;
    }

    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getTapId() {
        return tapId;
    }

    public void setTapId(String tapId) {
        this.tapId = tapId;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getIdDate() {
        return idDate;
    }

    public void setIdDate(String idDate) {
        this.idDate = idDate;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "Data{" +
                "upTimer='" + upTimer + '\'' +
                ", humidity='" + humidity + '\'' +
                ", infrared='" + infrared + '\'' +
                ", image='" + image + '\'' +
                ", sound='" + sound + '\'' +
                ", judge='" + judge + '\'' +
                ", water='" + water + '\'' +
                ", sensor1='" + sensor1 + '\'' +
                ", sensor2='" + sensor2 + '\'' +
                ", sensor3='" + sensor3 + '\'' +
                ", sensor4='" + sensor4 + '\'' +
                ", sensor5='" + sensor5 + '\'' +
                ", tapId='" + tapId + '\'' +
                ", tmp='" + tmp + '\'' +
                ", need='" + need + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", idCode='" + idCode + '\'' +
                ", success='" + success + '\'' +
                ", microphone='" + microphone + '\'' +
                ", camera='" + camera + '\'' +
                ", ifUsed='" + ifUsed + '\'' +
                ", todayUsed='" + todayUsed + '\'' +
                ", purpose='" + purpose + '\'' +
                ", idDate='" + idDate + '\'' +
                ", weather='" + weather + '\'' +
                ", hostName='" + hostName + '\'' +
                ", count='" + count + '\'' +
                ", cameras='" + cameras + '\'' +
                ", fault='" + fault + '\'' +
                ", sensor='" + sensor + '\'' +
                ", lastTime='" + lastTime + '\'' +
                '}';
    }
}

