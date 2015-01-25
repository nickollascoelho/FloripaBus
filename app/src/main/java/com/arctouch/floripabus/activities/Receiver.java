package com.arctouch.floripabus.activities;

public interface Receiver<T> {

    void onReceive(T result);

}
