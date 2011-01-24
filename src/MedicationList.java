/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * A linked List class to build a list of medications
 * for each patient
 * @author jtlomeni
 */

public class MedicationList {
    Medication firstMed;

    /**
     * Empty Constructor
     */
    public MedicationList(){
        firstMed=null;
    }

    /**
     * Method for testing whether the <MedicationList> is empty
     * @return
     */
    public boolean isEmptyList(){
        return firstMed==null;
    }


    /**
     * insert medication in order of next Fill date
     * @param newMed
     */
    public void insertMed(Medication newMed){
        Medication current = firstMed;
        if(isEmptyList()){
            firstMed=newMed;
        }else if(current.fillDate.isAfter(newMed.fillDate)){
            newMed.nextMed = current;
            firstMed = newMed;
        }else{
            while(current.nextMed!=null){
                current = current.nextMed;
            }
            current.nextMed = newMed;
        }
    }

    /**
     * Deletes Medication from <MedicationList>
     * @param medName
     */
    public void deleteMed(String medName){
       
        if(firstMed.medName.equals(medName)){
            firstMed = firstMed.nextMed;
        }else{
            Medication current = firstMed;
            while(!current.nextMed.medName.equals(medName)){
                current= current.nextMed;
            }
            current.nextMed = current.nextMed.nextMed;
        }


    }



}
