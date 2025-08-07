package cn.escort.web.business.canvas.service;


import cn.escort.web.business.fingerprint.domain.openFingerprint.ColoredPoint;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CanvasServiceImpl implements CanvasService {


    public List<ColoredPoint> getRandCanvas(){
        ArrayList<ColoredPoint> coloredPoints = new ArrayList<>();
        List<Integer> number = Arrays.asList(2, 3, 3, 3, 3, 4, 4, 4, 4, 5);
        Random random = new Random();
        int i = random.nextInt(number.size());
        Integer integer = number.get(i);
        for (int j = 0; j < integer; j++) {
            ColoredPoint coloredPoint = new ColoredPoint();
            coloredPoint.setRow(random.nextInt(99)+1);
            coloredPoint.setColumn(random.nextInt(99)+1);
            coloredPoint.setRed(random.nextInt(40)-20);
            coloredPoint.setBlue(random.nextInt(40)-20);
            coloredPoint.setGreen(random.nextInt(40)-20);
            coloredPoint.setAlpha(random.nextInt(40)-20);
            boolean contains = coloredPoints.stream().map(ColoredPoint::getRow).collect(Collectors.toList()).contains(coloredPoint.getRow());
            if(!contains){
                coloredPoints.add(coloredPoint);
            }else{
                j--;
            }
        }
        return coloredPoints.stream().sorted(Comparator.comparingInt(ColoredPoint::getRow)).collect(Collectors.toList());
    }

}
