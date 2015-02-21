import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PerceptronTest {

    Point generateRandomPoint(Random random) {
        double x[] = new double[2];
        x[0] = random.nextDouble();
        x[1] = random.nextDouble();

        return  new Point(x);
    }
    double randomInRange(double min,double max,Random random) {
        return min + (max-min)*random.nextDouble();
    }
    void generateRandomPoints(ArrayList<Point> points,ArrayList<Boolean> result) {
        Random random = new Random();
        Vector2D p1 = new Vector2D(randomInRange(-1.0,+1.0,random),randomInRange(-1.0, +1.0, random));
        Vector2D p2 = new Vector2D(randomInRange(-1.0, +1.0, random),randomInRange(-1.0, +1.0, random));

        System.out.println(p1.getX() + " " + p1.getY());
        System.out.println(p2.getX() + " " + p2.getY());
        Line line = new Line(p1,p2);
        random = new Random();
        for (int i = 0;i<10000;++i) {
            Point point = generateRandomPoint(random);
            points.add(point);
            Vector2D point2d = new Vector2D(point.getCoordinate(0),point.getCoordinate(1));
            double distance = line.getOffset(point2d);
            if (distance >= 0.0) {
                result.add(true);
                //System.out.println("On one side of line");
            }else {
                result.add(false);
                ///System.out.println("On anotjer side of line");
            }


        }



    }

    void testBasedonLinearlySeprableDate() {
        ArrayList<Point> points = new ArrayList<Point>();
        ArrayList<Boolean> results = new ArrayList<Boolean>();
        generateRandomPoints(points,results);
        double weight[] = new double[2];
        weight[0] = 0.0;
        weight[1] = 0.0;
        Perceptron perceptron = new Perceptron();
        perceptron.trainingAlgo(points,results,weight,0.0);
        System.out.println(weight[0]);
        System.out.println(weight[1]);
        int count = 0;
        for (int i = 0;i<points.size();++i) {
            double val = points.get(i).scalarProduct(weight);
            if (val >= 0.0 && results.get(i)) {
                ++count;
            }

            if (val < 0.0 && !results.get(i)) {
                ++count;
            }
        }

        System.out.println(count);

    }
    void readFile(String fileName) {
        //generateRandomPoints(null,null);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/rasrivastava/LEARNINGFROMDATA/src/test/java/formattedTrainingData"));
            ArrayList<Point> points = new ArrayList<Point>();
            ArrayList<Boolean> result = new ArrayList<Boolean>();
            boolean [] fieldRequiredToBeChosen = new boolean[]{false,true,true,false,false,false,false,true,false,false,true,false,false,true,true};
            while ((line = bufferedReader.readLine()) != null) {
                String [] data = line.split(",");
                boolean isNA = false;
                for (String x : data) {
                    if (x.equals("?")) {
                        //ignore this line
                        isNA = true;
                        break;
                    }

                }

                if (!isNA) {
                    double x[] = new double[16];
                    for (int i = 0,j = 0;i<15;++i) {
                        if (fieldRequiredToBeChosen[i]) {
                            x[j++] = Double.parseDouble(data[i]);
                        }
                    }

                    points.add(new Point(x));
                    if (data[15].equals("+")) {
                        result.add(true);
                    }else {
                        result.add(false);
                    }
                }
            }

            System.out.println("Result vector " + Arrays.asList(result));
            System.out.println(points.size());
            Perceptron perceptron = new Perceptron();
            int weightVectorSize = 0;
            for (int i = 0;i<fieldRequiredToBeChosen.length;++i) {
                if (fieldRequiredToBeChosen[i]) {
                    ++weightVectorSize;
                }
            }


            double [] weights = new double[weightVectorSize];
            //Random random = new Random();
            for (int i = 0;i<weights.length;++i) {
                weights[i] = 0.0;
            }
            perceptron.trainingAlgo(points,result,weights,1.0);
            for (int i = 0;i<weights.length;++i) {
                System.out.println(weights[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testTrainingSet() throws Exception {
        readFile("/Users/rasrivastava/LEARNINGFROMDATA/src/test/java/trainingdata");
        //testBasedonLinearlySeprableDate();

    }
}