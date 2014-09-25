package com.itechart.courses.service.order;

import com.itechart.courses.enums.OrderStatusEnum;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by Alex on 20.09.14.
 */
@Service
public class SequenceOrderStatusImpl implements SequenceOrderStatus {
    private Map<OrderStatusEnum, List<OrderStatusEnum>> map;

    public SequenceOrderStatusImpl() throws IOException{
        map = new HashMap<OrderStatusEnum, List<OrderStatusEnum>>(8);
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/sequence_order_status.properties"));

        for (String key : properties.stringPropertyNames()){
            String[] values = properties.getProperty(key).split(";");
            List<OrderStatusEnum> statusEnums = null;
            if (!values[0].isEmpty()){
                statusEnums = new ArrayList<OrderStatusEnum>(values.length);
                for (int i = 0; i < values.length; i++){
                    statusEnums.add(OrderStatusEnum.valueOf(values[i]));
                }
            }
            map.put(OrderStatusEnum.valueOf(key), statusEnums);
        }
    }

    public List<OrderStatusEnum> getValues(OrderStatusEnum key){
        return map.get(key);
    }
}


