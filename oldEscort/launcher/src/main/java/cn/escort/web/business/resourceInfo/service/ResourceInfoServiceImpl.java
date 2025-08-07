package cn.escort.web.business.resourceInfo.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class ResourceInfoServiceImpl implements ResourceInfoService {



    private ArrayList<Integer> randomList = new ArrayList<>();

    @PostConstruct
    public void init(){
        Map<Integer,Integer> cpuMap=new HashMap();

        cpuMap.put(2,20);
        cpuMap.put(4,50);
        cpuMap.put(6,60);
        cpuMap.put(8,110);
        cpuMap.put(10,40);

        cpuMap.put(12,80);
        cpuMap.put(16,360);
        cpuMap.put(18,40);
        cpuMap.put(20,80);
        cpuMap.put(24,160);
        int sum = cpuMap.entrySet().stream().mapToInt(Map.Entry::getValue).sum();

        cpuMap.forEach((key,value)->{
            Double dCount = value * 1D / sum * 1000;
            for (int i = 0; i < dCount.intValue(); i++) {
                randomList.add(key);
            }
        });

    }

    @Override
    public Integer getCpuCore(){
        int rand = new Random().nextInt(randomList.size());
        return randomList.get(rand);
    }

    @Override
    public  Integer getRandMemory(Boolean bit64){
        if(bit64){
            return 8;
        }else{
            return 4;
        }
    }

}
