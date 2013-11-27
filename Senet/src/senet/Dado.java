/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package senet;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author SnowBlade
 */
public class Dado implements Serializable{
    
    /*
     *  * * * 
     *  * X * 
     *  * * *
     */
    
	public Dado(){
		
	}
	
	public int gerVal(){
		Random ran = new Random();
		int val=ran.nextInt(6);
                while (val==4){
                val=ran.nextInt(6);
                }
                val++;
                System.out.println("   * * *");
                System.out.println("   * "+val+" *");
                System.out.println("   * * *");
                System.out.println();
		return val;
	}
        
        

}


