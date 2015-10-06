/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesmkdir;

import java.io.File;

/**
 *
 * @author dhanarp
 */
public class TesMKDIR {

    public static void lordMKDIR(String input)
    {
        File theDir = new File(input);
        if (!theDir.exists()) 
        {
            System.out.println("creating directory: " + input);
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
            if(result) {    
                System.out.println("DIR created");  
            }
        }
    }
    public static void main(String[] args) {
        lordMKDIR("/Users/dhanarp/random/lel");
    }
    
}
