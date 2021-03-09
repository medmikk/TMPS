package f1;

import java.util.ArrayList;

public class MainTest {

    public static void main(String[] args) {
        ArrayList<Double[]> data = new ArrayList<>();
        data.add(new Double[]{10.0, 25.0, 120.0, 53.0, 2.0});
        data.add(new Double[]{15.0, 16.0, 152.0, 53.0, 3.0});
        data.add(new Double[]{13.0, 21.0, 174.0, 52.0, 1.0});
        data.add(new Double[]{16.0, 21.0, 102.0, 53.0, 5.0});
        data.add(new Double[]{10.0, 17.0, 92.0,  51.0, 3.0});
        data.add(new Double[]{18.0, 30.0, 132.0, 50.0, 4.0});

        ArrayList<Double> weight = new ArrayList<>();
        weight.add(0.4);
        weight.add(0.3);
        weight.add(0.1);
        weight.add(0.1);
        weight.add(0.1);

        ArrayList<Boolean> mains = new ArrayList<>();
        mains.add(true);
        mains.add(true);
        mains.add(false);
        mains.add(false);
        mains.add(false);

        Compar compar = new Compar(data, weight, mains);
        System.out.println("Best is object №" + (compar.compareWithTOPSIS() + 1));
    }
}
