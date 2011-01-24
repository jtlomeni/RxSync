/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jtlomeni
 */
public class storeInfo implements java.io.Serializable {
    String name;
    String address;
    String city;
    String state;
    String zip;
    String phone;
    String fax;
    String sync;

    public storeInfo(String n, String a, String c, String s, String z, String p, String f, String syn){
        name=n;
        address=a;
        city=c;
        state=s;
        zip=z;
        phone=p;
        fax=f;
        sync=syn;
    }
}
