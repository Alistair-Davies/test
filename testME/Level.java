package ass2.ass2;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Level extends JPanel {
    public int levelNumber;
    public ArrayList<Integer> currentLevelInfo = new ArrayList<Integer>();
    public ArrayList<Integer> currentTop;
    public ArrayList<Integer> currentBottom;
    public ArrayList<Integer> currentLeft;
    public ArrayList<Integer> currentRight;
    public ArrayList<Integer> collectables;
    public ArrayList<Integer> defaultBorders;
    public int pacInitialX;
    public int pacInitialY;
    public int jellRestartX;
    public int jellRestartY;

    private static Map<String, ArrayList<Integer>> levelBorders1 = new HashMap();


    public Level(int levelID) {
        this.levelNumber = levelID;
        initialise();
        if (levelNumber==1) {
            setLevelInfo(levelBorders1);
            this.pacInitialX = 50;
            this.pacInitialY = 125;
            this.jellRestartX = 200;
            this.jellRestartY =150;
        }
        setBackground(Color.black);
        setLayout(new GridLayout(11, 30));

        for (int i=0; i <330; i++) {
            add(new shapeComponent(i));
        }

    }

    public void setLevelInfo(Map<String, ArrayList<Integer>> mapInfo) {
        Iterator itr =  mapInfo.entrySet().iterator();
        ArrayList<Integer> noCollect = new ArrayList();
        currentLevelInfo.addAll(defaultBorders);
        while (itr.hasNext()) {
            Map.Entry pair = (Map.Entry) itr.next();
            if (!pair.getKey().equals("collectables")){
                currentLevelInfo.addAll((ArrayList<Integer>)pair.getValue());
            }
            if (pair.getKey().equals("top")) {
                currentTop = (ArrayList<Integer>)pair.getValue();
            }
            else if (pair.getKey().equals("bottom")) {
                currentBottom = (ArrayList<Integer>)pair.getValue();
            }
            else if (pair.getKey().equals("left")) {
                currentLeft = (ArrayList<Integer>)pair.getValue();
            }
            else if (pair.getKey().equals("right")) {
                currentRight = (ArrayList<Integer>)pair.getValue();
            }
            else if (pair.getKey().equals("collectables")) {
               noCollect = (ArrayList<Integer>)pair.getValue();
            }
        }
        ArrayList<Integer> collect = new ArrayList<>();

        for (int c=0; c<330; c++) {
            if (!currentLevelInfo.contains(c) && !noCollect.contains(c)) {
                collect.add(c);
            }
        }
        collectables = collect;
    }

    public void initialise() {
        // generic outline borders for all levels
        defaultBorders = new ArrayList<Integer>();
        for (int b=0; b<30; b++) {
            if(!(b==14)) {
                defaultBorders.add(b);
                defaultBorders.add(b + 300);
            }
        }
        for (int b=0; b<300; b+=30) {
            if (b==120 || b==180) {
                defaultBorders.add(b);
                defaultBorders.add(b+29);
                defaultBorders.add(b+1);
            }
            else if (!(b==150)) {
                defaultBorders.add(b);
                defaultBorders.add(b+29);
            }
        }

        //level 1 init
        levelBorders1.put("top", new ArrayList<Integer>(Arrays.asList(62,63,242,244,245,65,66,67,155,156,69,70,71,250,73,74,75,164,165,254,77,78,79,167,168,227,258,81,82,83,172,173,262, 85,86,87 ,175,176,237,266)));
        levelBorders1.put("bottom", new ArrayList<Integer>(Arrays.asList(62,242,243,244,245,66,95,156,185,247,70,249,250,251,74,105,164,253,254,255,78,107,167,168,257,258,259,82,113,172,261,262,263, 86,115,176,177,265,266,267)));
        levelBorders1.put("left", new ArrayList<Integer>(Arrays.asList(62,93,123,153,183,213,242,65,95,155,185,97,127,187,217,247,69,99,129,159,189,219,249,101,131,161,191,221,73,103,133,163,193,223,253,105,195,225,77,107,167,227,257,199,229,109,139, 81,111,141,171,201,231,261,203,233,113,85,115,175,205,235,265,237,117,147)));
        levelBorders1.put("right", new ArrayList<Integer>(Arrays.asList(63,93,123,153,183,213,245,67,97,127,157,187,217,247,185,95,99,129,159,189,219,71,101,131,161,191,221,251,103,133,193,223,195,165,225,105,255,75,107,227,79,109,139,169,199,229,259,83,113,173,203,233,263,111,141,201,231,115,205,235,237,87,117,147,177,237,267)));
        levelBorders1.put("collectables",new ArrayList<>(Arrays.asList(150,14,314,179,128,158,188,100,130,160,190,220,194,224,202,232)));

        //other levels

    }
}
