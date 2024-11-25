package com.ftn.event_hopper.models;

import jdk.tools.jlink.plugin.Plugin;

import java.util.ArrayList;
import java.util.UUID;

public class EventType {
    private UUID id;
    private String name;
    private String description;
    private boolean isDeactivated;
    private ArrayList<Event> events;
    private ArrayList<Category> categories;
}
