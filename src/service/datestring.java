/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author MBM info
 */
public class datestring {

    String s = "Thu Apr 27 14:09:17 WEST 2017";

    public void start() {
        for (int i = 0; i < s.length(); i++) {
            String f = "";
            if (Character.isDigit(s.charAt(i))) {
                    f = f + s.charAt(i);
            }

            System.out.println(f);

        }
    }

}
