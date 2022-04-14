package com.ueba.performance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FormatData {
    public static Map formatCpu(double data){
        Map<Long,Double> hmCpu=new HashMap<Long,Double>();
        hmCpu.put(System.currentTimeMillis(),data);
        for (Map.Entry<Long,Double> me: hmCpu.entrySet()){
            System.out.println(me.getKey() + ":" + me.getValue());
        }
        return hmCpu;
    }

    public static Map formatMemory(long data){
        Map<Long,Long> hmMemory=new HashMap<Long,Long>();
        hmMemory.put(System.currentTimeMillis(),data);
        for (Map.Entry<Long,Long> me: hmMemory.entrySet()){
            System.out.println(me.getKey() + ":" + me.getValue());
        }
        return hmMemory;
    }
}
