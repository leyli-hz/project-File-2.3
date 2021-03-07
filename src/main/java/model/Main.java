package model;

import controller.Mojoodi;
import controller.Pardakht;
import view.ViewMojoodi;
import view.ViewPardakht;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        Pardakht pardakht = new Pardakht();
        Mojoodi mojoodi = new Mojoodi();

        List<ViewMojoodi> viewMojoodis = mojoodi.exportToViewM();
        List<ViewPardakht> viewPardakhts = pardakht.exportToViewP();
        String s = mojoodi.checkTheMount(viewMojoodis, viewPardakhts);
        if (s != null) {
            System.out.println("you can pay by this deposite number: " + s);
            String s1 = mojoodi.updateDebtorMount(viewMojoodis, viewPardakhts);
            s1 = s1.concat(mojoodi.updateCreditorMount(viewMojoodis, viewPardakhts));
            mojoodi.writeUpdatedMount(s1);
        } else {
            System.out.println("you dont have enough money ! please check your bank account.");
        }

    }
}
