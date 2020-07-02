package com.farbig.practice.nativejava;
//https://www.iitk.ac.in/esc101/05Aug/tutorial/native1.1/stepbystep/index.html

class HelloWorld {
    public native void displayHelloWorld();

    static {
        System.loadLibrary("hello");
    }
    
    public static void main(String[] args) {
        new HelloWorld().displayHelloWorld();
    }
}
