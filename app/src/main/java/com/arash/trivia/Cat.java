package com.arash.trivia;

public class Cat extends Animal {

    Animal a = new Animal() {
        @Override
        public void makeNoise() {

        }
    };
    @Override
    public void makeNoise() {
        System.out.println("meawww");
    }
}
