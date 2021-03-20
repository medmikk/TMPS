package f1;

import java.util.ArrayList;

public class Compar {
    private ArrayList<Double[]> data;
    private ArrayList<Double> weight;
    private ArrayList<Double> sign;
    private int cols;
    private int rows;
    private double[][] normalizedData;
    private double[][] weightedData;
    private double[] neg;
    private double[] pos;
    private double[] sip;
    private double[] sin;
    private double[] pi;
    private ArrayList<Double> pi_Moora = new ArrayList<Double>();
    private double sum;

    public Compar(ArrayList<Double[]> data, ArrayList<Double> weight, ArrayList<Double> sign){
        try{
            int size = data.get(0).length;
            for(Double[] d : data){
                int size1 = d.length;
                if (size !=  size1){
                    throw new IllegalArgumentException(
                            "not correct input table");
                }
            }
            if(size != weight.size()){
                throw new IllegalArgumentException(
                        "data columns must be equalent weight columns");
            }
            this.data = data;
            this.weight = weight;
            this.sign = sign;
            this.cols = weight.size();
            this.rows = data.size();
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private double sumSqrOfCol(int j){
        double res = 0.0;
        for (Double[] datum : data) {
            res += Math.pow(datum[j], 2);
        }
        return res;
    }

    public void print(double[][] matrix){
        for (double[] doubles : matrix) {
            for (int j = 0; j < matrix[0].length; j++)
                System.out.print("|  " + doubles[j]  + "\t");
            System.out.print("\n");
        }
    }

    private void setNormalizedData(){
        normalizedData = new double[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                normalizedData[i][j] =
                        data.get(i)[j]/Math.sqrt(sumSqrOfCol(j));
            }
        }
    }

    private void setWeightedData(){
        weightedData = new double[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                weightedData[i][j] =
                        normalizedData[i][j] * weight.get(j);
            }
        }
    }

    private void countPositiveAndNegative(){
        double[] pos = new double[cols];
        double[] neg = new double[cols];
        double min = Double.MAX_VALUE;
        double max = -1.0;
        for (int j = 0; j < cols; j++) {
            min = Double.MAX_VALUE;
            max = -1.0;
            for (int i = 0; i < rows; i++) {
                if(min > weightedData[i][j])
                    min = weightedData[i][j];
                if (max < weightedData[i][j])
                    max = weightedData[i][j];
            }
            pos[j] = max;
            neg[j] = min;

        }
        this.pos = pos;
        this.neg = neg;
//        System.out.print("negative:\n");
//        for(int i = 0; i < cols; i++)
//            System.out.print(neg[i] + "\t");
//        System.out.print("\npositive:\n");
//        for(int i = 0; i < cols; i++)
//            System.out.print(pos[i] + "\t");
    }

    private void countMOORA(){

        for (int i = 0; i < weightedData.length; i++){
            for (int j = 0; j < weightedData[0].length; j++){
                if (sign.get(j) == 1.0){
                    sum += weightedData[i][j];
                }
                if (sign.get(j) == 0.0){
                    sum -= weightedData[i][j];
                }
            }
            this.pi_Moora.add(sum);
            sum = 0;
        }
        System.out.println(pi_Moora);
    }

    private double countSumOfRowP(int i){
        double res = 0.0;
        for (int j = 0; j < cols; j ++)
            res += Math.pow(weightedData[i][j] - pos[j], 2);
        return Math.sqrt(res);
    }

    private double countSumOfRowN(int i){
        double res = 0.0;
        for (int j = 0; j < cols; j ++)
            res += Math.pow(weightedData[i][j] - neg[j], 2);
        return Math.sqrt(res);
    }

    private void countSi(){
        sin = new double[rows];
        sip = new double[rows];
        for (int i = 0; i < rows; i++) {
            sip[i] = countSumOfRowP(i);
            //System.out.println(sip[i]);
        }
        //System.out.println("\nSi NEG:");
        for (int i = 0; i < rows; i++) {
            sin[i] = countSumOfRowN(i);
            //System.out.println(sin[i]);
        }
    }

    private void countPi(){
        pi = new double[rows];
        for (int i = 0; i < rows; i++){
            pi[i] = sin[i]/(sin[i] + sip[i]);
        }
        for (int i = 0; i < rows; i++)
            System.out.println(pi[i]);
    }

    private int resultOfTOPSIS(){
        int res = -1;
        double max = -1;
        for(int i = 0; i < rows; i++)
            if (max < pi[i]){
                max = pi[i];
                res = i;
            }
        return res;
    }



    public void compareWithMOORA(){
        setNormalizedData();
        setWeightedData();
        countMOORA();
    }

    public int compareWithTOPSIS(){
        setNormalizedData();
        //print(normalizedData);
        setWeightedData();;
        //print(weightedData);
        double[] neg = {};
        double[] pos = {};
        countPositiveAndNegative();
        countSi();
        countPi();
        return resultOfTOPSIS();
    }
}
