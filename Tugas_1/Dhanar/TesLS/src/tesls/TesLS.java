/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesls;

import java.io.File;

/**
 *
 * @author dhanarp
 */

public class TesLS {

    public static void lordLS(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        System.out.println(fileEntry.getName());
    }
}


    public static void main(String[] args) {
        final File folder = new File("/Users/dhanarp/");
        lordLS(folder);
    }
    
}
