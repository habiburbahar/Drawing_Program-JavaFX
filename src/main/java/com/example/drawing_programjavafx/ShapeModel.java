package com.example.drawing_programjavafx;

import java.util.ArrayList;

public class ShapeModel {
    private ArrayList<ShapeModelSubscriber> subscribers;

    public ShapeModel() {
        subscribers = new ArrayList<>();
    }
    public void addSubscriber(ShapeModelSubscriber newSub) {
        subscribers.add(newSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(subscriber -> subscriber.modelChanged());
    }


}
