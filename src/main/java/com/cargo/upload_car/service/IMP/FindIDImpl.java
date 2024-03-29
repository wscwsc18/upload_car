package com.cargo.upload_car.service.IMP;

import com.alibaba.fastjson.JSON;
import com.cargo.upload_car.entity.Car;
import com.cargo.upload_car.entity.CarConfig;
import com.cargo.upload_car.mapper.FindCar;
import com.cargo.upload_car.service.IfiandID;
import com.cargo.upload_car.util.Send;
import com.cargo.upload_car.util.SendCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FindIDImpl implements IfiandID {

    @Autowired
    public FindCar findCar;

    @Override
    public List<Car> setConfig() {
        List<CarConfig> carConfigs = findCar.findAll();
        List<Car> list = new ArrayList<>();
        Iterator<CarConfig> it = carConfigs.iterator();
        Car car;
        while (it.hasNext()) {
            car = new Car();
            CarConfig carConfig = it.next();
            car.setCid(carConfig.getId());
            car.setStart_address(carConfig.getSetOutCity());
            car.setEnd_address(carConfig.getDestinationCity());
            //car.setRemark(carConfig.getInfo());
            car.setRemark("很好的物流u1");
            car.setStart_id("371311");
            car.setEnd_id("370102");
            car.setStart_lng("116.141");
            car.setStart_lat("39.961");
            car.setEnd_lng("115.214");
            car.setEnd_lat("40.822");

            car.setIs_use_enum("0");
            car.setVip_phone ( carConfig.getPhoneNumber() );
            car.setVip_username(carConfig.getUsername());

            car.setCar_model(carConfig.getTType());//车场长
            car.setCar_length(carConfig.getTLength());
            car.setCar_type("1");
            car.setWeight_min("0");
            car.setWeight_max("100");
            car.setVolume_min("0");
            car.setVolume_max("100");
            car.setGoods_name("货物名称1");
            car.setStarttime(String.valueOf(new Date().getTime()));
            car.setLoad_of("装货方式1");//装货方式
            car.setTask_price("2000");
            car.setIs_visible("1");//同城是否可见
            list.add(car);
        }
        return list;
    }

    @Override
    public void sendCars(List<Car> cars) {
        Iterator<Car> it = cars.iterator();
        SendCar sendCar = new SendCar();
        sendCar.setRequest_id("11111");
        sendCar.setTimestamp(String.valueOf(new Date().getTime()));
        sendCar.setSign();
        Map<String, String> map = new LinkedHashMap<String, String>();
        //设置request_id
        map.put("request_id", Integer.toString(Integer.valueOf(sendCar.getRequest_id())));
        //设置timestamp
        map.put("timestamp", sendCar.getTimestamp());
        //设置appid
        map.put("appid", sendCar.getAppid());
        //设置签名
        map.put("sign", sendCar.getSign());
        //设置预发送json
        map.put("data", JSON.toJSONString(cars));
        System.out.println(JSON.toJSONString(cars));
        System.err.println();
        String result = Send.sendPost("http://daguolian.qdunzi.com/api/thirdparty/thirdPartySource", map);
        System.err.println(result);
    }

    @Override
    public List<CarConfig> findTop200() {
        return  findCar.findTop200();
    }

    @Override
    public void deleteCarByCid(Integer id) {
        Integer rows = findCar.deletById(id);
    }

}
