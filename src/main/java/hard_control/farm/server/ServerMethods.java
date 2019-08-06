package hard_control.farm.server;

import pojo.Data;

public class ServerMethods {

    public Data update(String jsonString){

        System.out.println("updata");
        System.out.println(jsonString);

        return null;

    }

    public Data select(Data data){
        Data reData=new Data();

        String image=data.getImage();

        String sound=data.getSound();


        return reData;
    }


}
