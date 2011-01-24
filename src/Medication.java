/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *  A class for the Medications
 * @author jtlomeni
 */
import org.joda.time.LocalDate;

public class Medication {
    String medName;
    String sig;
    int copay;
    LocalDate fillDate;
    Medication nextMed;

    int planFillLength;
    LocalDate longFillDate;
    int daysLeft;


    /**
     * Empty constructor for <Medication> class
     */
    public Medication(){
     medName = "";
    }

    /**
     * Full Constructor for <Medication> class
     * @param name
     * @param sg
     * @param fd
     * @param cp
     */
    public Medication(String name, String sg, LocalDate fd, int cp){
        medName = name;
        sig = sg;
        fillDate = fd;
        copay = cp;
    }

    /**
     * Method for setting <planFillLength>
     * @param n
     */
    public void setPlan(int n){
        planFillLength = n;
    }

    /**
     * Method for setting <longFillDate>
     * @param n
     */
    public void setLongFillDate(LocalDate n){
        longFillDate = n;
    }

    /**
     * Method for setting <daysLeft>
     * @param n
     */
    public void setDaysLeft(int n){
        daysLeft = n;
    }

}

