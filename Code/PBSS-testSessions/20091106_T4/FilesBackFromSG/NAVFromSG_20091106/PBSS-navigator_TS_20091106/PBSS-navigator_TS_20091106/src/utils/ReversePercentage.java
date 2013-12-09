package utils;
/**
 * Class that holds the details of time required for bus to cross an intersection.
 * 
 * @author Stanley Sidik
 * 
 *
 */


public class ReversePercentage {

    public int reversePercentage(int seconds){
       
        int reversePercentage;
        if (seconds > 40) {
            
            reversePercentage = 4;
        }
        else{
        reversePercentage = seconds/10;
        }
        return  reversePercentage;
        
    }

    //test output
    public static void main(String[] args){
        
        //ReversePercentage s = new ReversePercentage();
        //int vTestResult = s.reversePercentage(30);
        // System.out.println("The total is:" +vTestResult);
        

    }
}