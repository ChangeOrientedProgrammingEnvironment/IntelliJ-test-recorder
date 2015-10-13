package edu.oregonstate.cope.installer;

public class SurveyProvider {

    public static void configureSurveyPlugin(){
        System.out.println("configure Plugin");

        Survey dialog = new Survey();
        dialog.pack();
        dialog.setVisible(true);
    }
}
