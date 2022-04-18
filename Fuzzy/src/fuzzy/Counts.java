
package fuzzy;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 *
 * @author Jihan Lailatul A
 */
public class Counts {
        DecimalFormat df = new DecimalFormat("###.##");
        String CSVfile = "Influencers.csv";
        ArrayList<Integer> dataId = new ArrayList<>();
        ArrayList<Double> dataFolls = new ArrayList<>();
        ArrayList<Double> dataER = new ArrayList<>();
        
        int fhigh = 50000; //Batas nilai minimum high followers
        int favg2 = 40000; // Batas nilai maksimum avg followers
        int favg1 = 20000; // Batas nilai minimum avg followers
        int flow = 10000; // Batas nilai maksimum low followers
        
        int fuzzy_fhigh = 40000;
        int fuzzy_favg2 = 48000;
        int fuzzy_favg1 = 10000;
        int fuzzy_flow = 22000;
        
        double ehigh = 6.0; //Batas nilai minimum high engagement
        double eavg2 = 5.0; // Batas nilai maksimum avg engagement
        double eavg1 = 3.0; // Batas nilai minimum avg engagement
        double elow = 1.0; // Batas nilai maksimum low engagement
        
        double fuzzy_ehigh = 5.0;
        double fuzzy_eavg2 = 5.8;
        double fuzzy_eavg1 = 1.0;
        double fuzzy_elow = 3.5;
        
        ArrayList<Double> F_HighValue = new ArrayList<>();
        ArrayList<Double> F_LowValue = new ArrayList<>();
        ArrayList<Double> F_AvgValue = new ArrayList<>();
        
        ArrayList<Double> E_HighValue = new ArrayList<>();
        ArrayList<Double> E_LowValue = new ArrayList<>();
        ArrayList<Double> E_AvgValue = new ArrayList<>();
        
        ArrayList<Double> ScoreValue = new ArrayList<>();
        
        Double[][][] table = new Double[100][9][3];
        Double[][] skor = new Double[100][3];
        
        ArrayList<Double> Final =new ArrayList<>();
        ArrayList<Integer> Winner =new ArrayList<>();
    
        public void ReaderNParser(){
        int count =0;
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(CSVfile))) {
            while ((line = br.readLine()) != null) {
                if (count == 1){
                    String[] data = line.split(",");
                    dataId.add(Integer.parseInt(data[0]));
                    dataFolls.add(Double.parseDouble(data[1]));
                    dataER.add(Double.parseDouble(data[2]));
                }
                count=1;
            }

        } catch (IOException e) {
            e.printStackTrace();
          }
        }
        
        public void fuzzification(){
            for (int i = 0; i < dataId.size();i++){
                if (dataFolls.get(i)<= flow){
                    F_LowValue.add(1.0);
                    F_AvgValue.add(0.0);
                    F_HighValue.add(0.0);
                }else if (dataFolls.get(i) <= fuzzy_flow){
                    F_LowValue.add((fuzzy_flow - dataFolls.get(i)) / (fuzzy_flow - flow) );
                    F_HighValue.add(0.0);
                    if (dataFolls.get(i)>= favg1){
                        F_AvgValue.add(1.0);
                    }else{
                        F_AvgValue.add( (dataFolls.get(i)-fuzzy_favg1) /(favg1 - fuzzy_favg1) );
                    } 
                }else if (dataFolls.get(i)>= favg1 && dataFolls.get(i)<= favg2){
                    F_AvgValue.add(1.0);
                    F_HighValue.add(0.0);
                    if(dataFolls.get(i)<fuzzy_flow){
                        F_LowValue.add((fuzzy_flow - dataFolls.get(i)) / (fuzzy_flow - flow) );
                    }else{
                        F_LowValue.add(0.0);
                    }
                }else if (dataFolls.get(i)<fhigh){
                    F_LowValue.add(0.0);
                    F_HighValue.add((dataFolls.get(i)-fuzzy_fhigh) / (fhigh-fuzzy_fhigh));
                    if (dataFolls.get(i)<= fuzzy_favg2){
                        F_AvgValue.add((fuzzy_favg2-dataFolls.get(i))/ (fuzzy_favg2 - favg2));
                    }else{
                        F_AvgValue.add(0.0);
                    }
                }else{
                    F_HighValue.add(1.0);
                    F_LowValue.add(0.0);
                    F_AvgValue.add(0.0);
                }
            }
            for (int i = 0; i < dataId.size();i++){
                if (dataER.get(i)<= elow){
                    E_LowValue.add(1.0);
                    E_AvgValue.add(0.0);
                    E_HighValue.add(0.0);
                }else if (dataER.get(i) <= fuzzy_elow){
                    E_LowValue.add((fuzzy_elow - dataER.get(i)) / (fuzzy_elow - elow) );
                    E_HighValue.add(0.0);
                    if (dataER.get(i)>= eavg1){
                        E_AvgValue.add(1.0);
                    }else{
                        E_AvgValue.add( (dataER.get(i)-fuzzy_eavg1) /(eavg1 - fuzzy_eavg1) );
                    } 
                }else if (dataER.get(i)>= eavg1 && dataER.get(i)<= eavg2){
                    E_AvgValue.add(1.0);
                    E_HighValue.add(0.0);
                    if(dataER.get(i)<fuzzy_elow){
                        E_LowValue.add((fuzzy_elow - dataER.get(i)) / (fuzzy_elow - elow) );
                    }else{
                        E_LowValue.add(1.0);
                    }
                }else if (dataER.get(i)<ehigh){
                    E_LowValue.add(0.0);
                    E_HighValue.add((dataER.get(i)-fuzzy_ehigh) / (ehigh-fuzzy_ehigh));
                    if (dataER.get(i)<= fuzzy_eavg2){
                        E_AvgValue.add((fuzzy_eavg2-dataER.get(i))/ (fuzzy_eavg2 - eavg2));
                    }else{
                        E_AvgValue.add(0.0);
                    }
                }else if (dataER.get(i)>=ehigh){
                    E_HighValue.add(1.0);
                    E_LowValue.add(0.0);
                    E_AvgValue.add(0.0);
                }
            }
            
        }
        
        public void inference(){
            for (int i = 0; i<dataId.size();i++){
                table[i][0][0] = F_HighValue.get(i);
                table[i][0][1] = E_HighValue.get(i);
                if (table[i][0][0]>table[i][0][1]){
                    table[i][0][2] = table[i][0][1];
                }else{
                    table[i][0][2] = table[i][0][0];
                }
                
                table[i][1][0] = F_HighValue.get(i);
                table[i][1][1] = E_AvgValue.get(i);
                if (table[i][1][0]>table[i][1][1]){
                    table[i][1][2] = table[i][1][1];
                }else{
                    table[i][1][2] = table[i][1][0];
                }
                
                table[i][2][0] = F_HighValue.get(i);
                table[i][2][1] = E_LowValue.get(i);
                if (table[i][2][0]>table[i][2][1]){
                    table[i][2][2] = table[i][2][1];
                }else{
                    table[i][2][2] = table[i][2][0];
                }
                
                table[i][3][0] = F_AvgValue.get(i);
                table[i][3][1] = E_HighValue.get(i);
                if (table[i][3][0]>table[i][3][1]){
                    table[i][3][2] = table[i][3][1];
                }else{
                    table[i][3][2] = table[i][3][0];
                }
                
                table[i][4][0] = F_AvgValue.get(i);
                table[i][4][1] = E_AvgValue.get(i);
                if (table[i][4][0]>table[i][4][1]){
                    table[i][4][2] = table[i][4][1];
                }else{
                    table[i][4][2] = table[i][4][0];
                }
                
                table[i][5][0] = F_AvgValue.get(i);
                table[i][5][1] = E_LowValue.get(i);
                if (table[i][5][0]>table[i][5][1]){
                    table[i][5][2] = table[i][5][1];
                }else{
                    table[i][5][2] = table[i][5][0];
                }
                
                table[i][6][0] = F_LowValue.get(i);
                table[i][6][1] = E_HighValue.get(i);
                if (table[i][6][0]>table[i][6][1]){
                    table[i][6][2] = table[i][6][1];
                }else{
                    table[i][6][2] = table[i][6][0];
                }
                
                table[i][7][0] = F_LowValue.get(i);
                table[i][7][1] = E_AvgValue.get(i);
                if (table[i][7][0]>table[i][7][1]){
                    table[i][7][2] = table[i][7][1];
                }else{
                    table[i][7][2] = table[i][7][0];
                }
                
                table[i][8][0] = F_LowValue.get(i);
                table[i][8][1] = E_LowValue.get(i);
                if (table[i][8][0]>table[i][8][1]){
                    table[i][8][2] = table[i][8][1];
                }else{
                    table[i][8][2] = table[i][8][0];
                }    
            }
            for (int i =0; i<dataId.size();i++){
                Double MaxA = table[i][0][2];
                Double MaxC = table[i][2][2];
                Double MaxR = table[i][7][2];
                //Accepted Count
                if(table[i][1][2]>MaxA){
                    MaxA =table[i][1][2];
                }
               if(table[i][3][2]>MaxA){
                    MaxA =table[i][3][2];
                }
               //Considered Count
               if(table[i][4][2]>MaxC){
                    MaxC =table[i][4][2];
                }
               if(table[i][5][2]>MaxC){
                    MaxC =table[i][5][2];
                }
               if(table[i][6][2]>MaxC){
                    MaxC =table[i][6][2];
                }
               //Rejected Count
               if(table[i][7][2]>MaxR){
                    MaxR =table[i][7][2];
                }
               if(table[i][8][2]>MaxR){
                    MaxR =table[i][8][2];
                }
               skor[i][0]=MaxA;
               skor[i][1]=MaxC;
               skor[i][2]=MaxR;
            }
            
        }
        
        public void defuzzification(){
            Double z1;
            Double z2;
            for (int i=0; i<dataId.size();i++){
                z1 = skor[i][0]*100 + skor[i][1]*75 + skor[i][2]*50;
                z2 = skor[i][0]+skor[i][1]+skor[i][2];
                Final.add(z1/z2);
            }
            Double max=0.0;
            int idx=0;
            for(int i=0;i<dataId.size();i++){
                if (Final.get(i)>max){
                    max = Final.get(i);
                    idx = i+1;
                }
            }
            Winner.add(idx);
            
            for (int j=0;j<19;j++){
                max = 0.0;
                idx=0;
                for (int i=0;i<dataId.size();i++){
                    if(Final.get(i)>=max){
                        int checker=0;
                        for(int k =0;k<Winner.size();k++){
                            if(i+1 == Winner.get(k)){
                                checker++;
                            }
                        }
                        if (checker <1){
                            max = Final.get(i);
                            idx = i+1;
                        }
                    }
                }
                Winner.add(idx);
            }
        }
        
        public void makeCSV() throws IOException{
             File file = new File("Chosen.csv");
             FileWriter fw = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(fw);

            bw.write("ID");
            bw.newLine();
            for(int i=0;i<Winner.size();i++)
            {
                bw.write(Integer.toString(Winner.get(i)));
                bw.newLine();
            }
            bw.close();
            fw.close();
        }
        
        
        
        
        
        
        
        
        
        
        
        
}
