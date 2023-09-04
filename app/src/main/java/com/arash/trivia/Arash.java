package com.arash.trivia;

public class Arash implements MyInterface{

    public void method1 (){
        Mehdi m = new Mehdi("hi");
        String a1 = m.getA();
        System.out.println(a1);


        m.sleep(new SleepCallback() {
            @Override
            public void sleep(int s) {

            }

            @Override
            public void wakeup(int w) {

            }
        });

        m.testMyInterface( this);


        m.testMyInterface(new MyInterface() {
            @Override
            public void testInterface(int res) {

            }
        });



    }


    @Override
    public void testInterface(int res) {

    }
}
