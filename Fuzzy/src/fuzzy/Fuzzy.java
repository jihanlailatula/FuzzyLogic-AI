
package fuzzy;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 *
 * @author Jihan Lailatul A
 */
public class Fuzzy {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        DecimalFormat df = new DecimalFormat("###.##");
    
        
        Counts C = new Counts();
        C.ReaderNParser();
        System.out.println("Data ID : " + C.dataId);
        System.out.println("Data Followers : " + C.dataFolls);
        System.out.println("Data Engagement Rate : " + C.dataER);
        C.fuzzification();
        System.out.println("");
        
        System.out.println("Data ID    : " + C.dataId);
        System.out.print("Folls High : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print(df.format( C.F_HighValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        System.out.print("Folls Avg  : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print(df.format(C.F_AvgValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        System.out.print("Folls Low  : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print( df.format(C.F_LowValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        
        System.out.println("");
        
        System.out.print("Engage High : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print(df.format( C.E_HighValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        System.out.print("Engage Avg  : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print(df.format(C.E_AvgValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        System.out.print("Engage Low  : [");
        for (int i=0; i<C.dataId.size();i++){
            System.out.print( df.format(C.E_LowValue.get(i)));
            if (i!=C.dataId.size()-1){
                System.out.print(", ");
            }
        }System.out.println("]");
        System.out.println("");
        C.inference();
        C.defuzzification();
        
        for (int i = 0; i<C.dataId.size();i++){
            System.out.println("ID: "+C.dataId.get(i));
            System.out.print("Gaji           ");
            System.out.print("  IPK");
            System.out.println("    Score");
            System.out.println("=================================================");
            
            System.out.print("High : "+df.format(C.table[i][0][0]));
            if (C.table[i][0][0]<0.01 || C.table[i][0][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  High : "+df.format(C.table[i][0][1]));
            if (C.table[i][0][1]<0.01 || C.table[i][0][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Accepted     : "+df.format(C.table[i][0][2]));
            
            System.out.print("High : "+df.format(C.table[i][1][0]));
            if (C.table[i][1][0]<0.01 || C.table[i][1][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  Avg  : "+df.format(C.table[i][1][1]));
            
            if (C.table[i][1][1]<0.01 || C.table[i][1][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Accepted     : "+df.format(C.table[i][1][2]));
            
            System.out.print("High : "+df.format(C.table[i][2][0]));
            if (C.table[i][2][0]<0.01 || C.table[i][2][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  Low  : "+df.format(C.table[i][2][1]));
            if (C.table[i][2][1]<0.01 || C.table[i][2][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Considered   : "+df.format(C.table[i][2][2]));
            
            System.out.print("Avg  : "+df.format(C.table[i][3][0]));
            
            if (C.table[i][3][0]<0.01 || C.table[i][3][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  High : "+df.format(C.table[i][3][1]));
            if (C.table[i][3][1]<0.01 || C.table[i][3][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Accepted     : "+df.format(C.table[i][3][2]));
            
            System.out.print("Avg  : "+df.format(C.table[i][4][0]));
            if (C.table[i][4][0]<0.01 || C.table[i][4][0]>0.95){
                System.out.print("   ");
            }
            System.out.print("  Avg  : "+df.format(C.table[i][4][1]));
            if (C.table[i][4][1]<0.01 || C.table[i][4][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Considered   : "+df.format(C.table[i][4][2]));
            
            System.out.print("Avg  : "+df.format(C.table[i][5][0]));
            if (C.table[i][5][0]<0.01 || C.table[i][5][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  Low  : "+df.format(C.table[i][5][1]));
            if (C.table[i][5][1]<0.01 || C.table[i][5][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Considered   : "+df.format(C.table[i][5][2]));
            
            System.out.print("Low  : "+df.format(C.table[i][6][0]));
            if (C.table[i][6][0]<0.01 || C.table[i][6][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  High : "+df.format(C.table[i][6][1]));
            if (C.table[i][6][1]<0.01 || C.table[i][6][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Considered   : "+df.format(C.table[i][6][2]));
            
            System.out.print("Low  : "+df.format(C.table[i][7][0]));
            if (C.table[i][7][0]<0.01 || C.table[i][7][0]>0.99){
                System.out.print("   ");
            }
            System.out.print("  Avg  : "+df.format(C.table[i][7][1]));
            if (C.table[i][7][1]<0.01 || C.table[i][7][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Rejected     : "+df.format(C.table[i][7][2]));
            
            System.out.print("Low  : "+df.format(C.table[i][8][0]));
            
            if (C.table[i][8][0]<0.01 || C.table[i][8][0]>0.99){
                System.out.print("    ");
            }
            System.out.print("  Low  : "+df.format(C.table[i][8][1]));
            if (C.table[i][8][1]<0.01 || C.table[i][8][1]>0.99){
                System.out.print("   ");
            }
            System.out.println("         Rejected     : "+df.format(C.table[i][8][2]));
            
            System.out.println("=================================================");
            System.out.println("Accepted Score   : "+df.format(C.skor[i][0]));
            System.out.println("Considered Score : "+df.format(C.skor[i][1]));
            System.out.println("Rejected Score   : "+df.format(C.skor[i][2]));
            System.out.println("Z : "+df.format(C.Final.get(i)));

            System.out.println("");
            System.out.println("");

        }
            C.makeCSV();  
            System.out.println("             CHOSEN STUDENTS                ");
            System.out.println("********************************************");
            System.out.println("*      Student ID      ||       Score      *");
            System.out.println("********************************************");
            for (int i=0;i<C.Winner.size();i++){
                System.out.print("*    ");
                String st = df.format(C.Final.get(C.Winner.get(i)-1));
                System.out.print("     "+C.Winner.get(i));
                if (C.Winner.get(i)<10){
                    System.out.print("  ");
                }else if (C.Winner.get(i)<100){
                    System.out.print(" ");
                }System.out.print("          ||");
                
                System.out.print("       "+st);
                if(st.length()<3){
                    System.out.print("      ");
                }if (st.length()<4){
                    System.out.print(" ");
                }if (st.length()<5){
                    System.out.print(" ");
                }
                System.out.print("      *");
                
                System.out.println("");
            }
            System.out.println("********************************************");
            System.out.println("");


        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
}
