package com.arash.trivia;

public class Mehdi {
    String a ;

    public Mehdi(String a) {
        this.a = a;
    }
    public String getA() {
        return a;
    }

    public void testMyInterface(MyInterface mi) {
        int a3 =5;
        mi.testInterface(a3);
    }

    public void sleep (SleepCallback s){


        s.sleep(6);

        s.wakeup(7);

    }


}


interface MyInterface{
 void testInterface(int res);
}