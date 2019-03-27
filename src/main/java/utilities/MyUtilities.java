/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author avalldeperas
 */
public class MyUtilities {
    
    private static MyUtilities myUtilities;
    
    public static MyUtilities getInstance() {
        if (myUtilities == null) {
            myUtilities = new MyUtilities();
        }
        return myUtilities;
    }
    
    public static Date stringToDate(String strDate) {
        try {
            SimpleDateFormat formatter =new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
            return formatter.parse(strDate);
        } catch (ParseException ex) {
            Logger.getLogger(MyUtilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        return null;
    }
    
    public static String dateToString(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        return dateFormat.format(date);  
    }
}
