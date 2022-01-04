package com.example.drawing_programjavafx;

import java.util.ArrayList;

public class InteractionModel {

    ArrayList<InteractionModelSubscriber> subscribers;

    public InteractionModel() {
        subscribers = new ArrayList<>();
    }

    public void addSubscriber(InteractionModelSubscriber aSub) {
        subscribers.add(aSub);
    }

    private void notifySubscribers() {
        subscribers.forEach(sub -> sub.iModelChanged());
    }

}
