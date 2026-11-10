package io.github.arisnguyen.d2s.pattern.behavioral.observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
