import java.util.ArrayList;
import java.util.Collections;


//Should be point in N dimensions
class Point {
    private double x[];

    public Point(double[] x) {
        this.x = x;
    }

    public double getCoordinate(int n) {
        return x[n];
    }

    public double scalarProduct(double [] weights) {
        //Assume length are same
        double  result = 0;
        for (int i = 0;i<weights.length;++i) {
            result += x[i] * weights[i];
        }

        return result;
    }

    public void updateVectorNegatively(double [] weights) {

        for (int i = 0;i<weights.length;++i) {
            weights[i] = weights[i] - x[i];
        }
    }

    public void updateVectorPositively(double [] weights) {

        for (int i = 0;i<weights.length;++i) {
            weights[i] = weights[i] + x[i];
        }
    }
}

public class Perceptron {


     private static final int MAX_COUNT = 1000;
     double [] trainingAlgo(ArrayList<Point> points,ArrayList<Boolean> results,double [] weightVector,double threshold) {
         ArrayList<Integer> arr = new ArrayList<>();
         for (int i = 0;i<points.size();++i) {
             arr.add(i+1);
         }

         for (int i = 0;i<MAX_COUNT;++i) {
             int j = 0;
             int anomalyEncountered = 0;

             Collections.shuffle(arr);
             for (int k = 0;k<points.size();++k) {
                 Point point = points.get(arr.get(k)-1);
                 boolean givenResult = results.get(arr.get(k)-1);
                 double sampleWeight = point.scalarProduct(weightVector);
                 double diffValue = sampleWeight - threshold;
                 boolean sampleResult = new Double(diffValue).compareTo(0.0) >=0 ? true:false;
                 if (sampleResult != givenResult) {
                    if (sampleResult == true) {
                        //Means expectation was false
                        point.updateVectorNegatively(weightVector);
                    }else {
                        point.updateVectorPositively(weightVector);
                    }

                     //System.out.println("Anomaly weight encounterd for index + " +j);
                     ++anomalyEncountered;
                     break;
                 }else {
                     //System.out.println("Anomaly not encountered");
                 }


                 ++j;
             }

             if (anomalyEncountered == 0) {
                 System.out.println("Anomlay not encountered ");
             }
         }

         return weightVector;
     }

}
