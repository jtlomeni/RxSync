
import org.joda.time.LocalDate;
import org.joda.time.Days;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Class for Representation of the Patient and all their
 * information
 * @author jtlomeni
 */

public class Patient {
    public final int PERIOD = 28;
    public String firstName;
    public String lastName;
    public LocalDate birthday;
    public String streetAddress;
    public String cityAddress;
    public String stateAddress;
    public String zipAddress;

    public boolean insAllowsLongFlag;


    public String doctorName;
    public long doctorFaxNum;
    public String docEmail;
    public boolean faxFlag;
    public boolean emailFlag;
                        
    public MedicationList medList;
    /**
     * Default <Patient> constructor
     */
    public Patient(){
        firstName="";
        streetAddress="";
        cityAddress="";
        stateAddress="";
        zipAddress="";
        doctorName="";
        faxFlag=false;
        emailFlag=false;

    }
    /**
     *
     * Full Constructor of <Patient>
     * @param pfirst Patient first name
     * @param plast Patient last name
     * @param bday Patient birthday
     * @param street Patient street address
     * @param city Patient city address
     * @param state Patient state address
     * @param zip Patient zip code
     * @param allowsLong Flag for patients insurance (allows long fills)
     * @param dfirst Doctor first name
     * @param dlast Doctor last name
     * @param dFaxNum Doctor fax number
     * @param demail Doctor email
     * @param useFax Doctor flag to use fax
     * @param useEmail Doctor flag to use email
     * @param mlist <MedicationList> of patients medications
     */
    public Patient(String pfirst, String plast,LocalDate bday, String street, String city,
            String state, String zip, boolean allowsLong,
            String dfirst, String dlast,
            String dFaxNum, String demail, boolean useFax, boolean useEmail, MedicationList mlist)
    {
        firstName = pfirst;
        lastName = plast;
        birthday = bday;
        streetAddress = street;
        cityAddress = city;
        stateAddress = state;
        zipAddress = zip;

        insAllowsLongFlag = allowsLong;
   

        doctorName = dfirst + " " + dlast;
        doctorFaxNum = Long.parseLong(dFaxNum);
        docEmail = demail;
        faxFlag = useFax;
        emailFlag = useEmail;
        medList = mlist;
    }

    /**
     * The method sync finds figures out the optimal date in which
     * to sync medications depending on what flags the user has set
     * It then tells the user how much of each medication is need to
     * sync the medications
     * @return <LocalDate> The target date to sync to
     *
     *
     */
    public LocalDate syncRefill(){
        LocalDate targetDate = new LocalDate();  //targetDate is the date to which we want to sync
        Medication current = medList.firstMed;   //currentMed is instantiated to be the frist medication in the list
                              //row for output


            //checks to make sure that fill date is not on the weekend
            //else put target date on friday before
        targetDate = current.fillDate.plusDays(28);
        targetDate = checkWeekend(targetDate);
        System.out.println("Taget Date: " + targetDate.toString());
        System.out.println("Medication        Fill For(days)");
        System.out.println("================================");
        while(current!=null){
            Days d = Days.daysBetween(current.fillDate, targetDate);
            int numDays = d.getDays();
            if(numDays == 0){
                   numDays = PERIOD;
            }
            System.out.println(current.medName + "     "  +  numDays);
            current.setPlan(numDays);
            current = current.nextMed;
        }

        
        return targetDate;
    }
    /**
     * Syncs <MedicationList> to the <refillDate> of the medication with
     * the highest copay
     * @return <LocalDate> Target date of the refill
     */
    public LocalDate syncCopay(){
        LocalDate targetDate = highestCopay().plusDays(PERIOD);
        Medication current = medList.firstMed;
        targetDate = checkWeekend(targetDate);
        if(insAllowsLongFlag){
            while(current!=null){
                Days d = Days.daysBetween(current.fillDate, targetDate);
                int numDays = d.getDays();
                if(numDays == 0){
                    numDays = PERIOD;
                }
                current.setPlan(numDays);
                current = current.nextMed;

            }
        }
        return targetDate;

    }
    /**
     * Syncs <MedicationList> to <Medication> with the highest copay
     * accounts for insurance limitations
     * @return <LocalDate> target date of the sync
     */
    public LocalDate syncCopayShort(){
        LocalDate targetDate = highestCopay().plusDays(PERIOD);
        Medication current = medList.firstMed;
     
        targetDate = checkWeekend(targetDate);
            while(current!=null){
                Days d = Days.daysBetween(current.fillDate,targetDate);
                int numDays = d.getDays();
                if(numDays > PERIOD){
                    int diff = numDays - PERIOD;
                    LocalDate longFillTargetDate = current.fillDate.plusDays(diff);
                    longFillTargetDate = checkWeekend(longFillTargetDate);
                    current.setLongFillDate(longFillTargetDate);
                    d = Days.daysBetween(longFillTargetDate, targetDate);
                    numDays = d.getDays();
                    current.setPlan(numDays);
                    current.setDaysLeft(Days.daysBetween(current.fillDate, longFillTargetDate).getDays());
                    if(current.daysLeft==0){

                    }
                    current=current.nextMed;
                }else{
                    d = Days.daysBetween(current.fillDate, targetDate);
                    numDays = d.getDays();
                    if(numDays == 0){
                        numDays = PERIOD;
                    }
                    current.setPlan(numDays);
                    current.setLongFillDate(current.fillDate);
                    current = current.nextMed;
                }
            }
        return targetDate;
       
    }


    /**
     * Syncs <MedicationList> to <Medication><refillDate> with the highest copay
     * @return <LocalDate> target date of the medication with highest copay
     */
    public LocalDate highestCopay(){
        System.out.println("Syncing to highest copay");
        Medication current = medList.firstMed;
        LocalDate targetDate = current.fillDate;
        int highcopay = 0;
        while(current != null){
            if(current.copay > highcopay ){
                targetDate = current.fillDate;
                highcopay = current.copay;
            }
            current = current.nextMed;
        }

        return targetDate;
    }
    /**
     * checks targetDate to make sure that it is not the weekend
     * @param targetDate date in which to sync
     * @return targetDate date that is not on a weekend
     */
    private LocalDate checkWeekend(LocalDate targetDate){
        if(targetDate.getDayOfWeek() == 6){
            targetDate = targetDate.minusDays(1);
        }else if(targetDate.getDayOfWeek() == 7){
            targetDate = targetDate.minusDays(2);
        }
        return targetDate;
    }
    
}
