package testing.com;

import java.util.ArrayList;
import java.util.Collections;

public class ScaleGenerator {

    private ArrayList<String> scalesArray = new ArrayList<String>();

    public void Generator(){
        Collections.shuffle(scalesArray);
    }

    public void setScalesArray(String scale){
        scalesArray.add(scale);
    }

    public String getScaleAtIndex(int index) {
        try {
            return scalesArray.get(index);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(scalesArray.size());
            return "Finished";

        }
    }
    public int getLength(){
        return scalesArray.size();
    }

}
