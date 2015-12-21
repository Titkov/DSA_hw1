package com.company;

import java.util.ArrayList;
import java.util.List;

public class Unit {
    List<Integer> soldierNumbers;
    String name;
    Unit attachedTo;
    Unit hasAttached;

    public Unit() {
        this.soldierNumbers = new ArrayList<Integer>();
        this.attachedTo = null;
        this.hasAttached = null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public List<Integer> getUnitNumber() {
        return this.soldierNumbers;
    }

    public void setSoldierNumbers(List<Integer> value) {
        this.soldierNumbers = value;
    }

    public void addIndexOfSoldier(Integer num) {
        this.soldierNumbers.add(num);
    }

    public Unit getAttachedTo() {
        return this.attachedTo;
    }
    public void setAttachedTo(Unit unit) {
        this.attachedTo = unit;
    }

    public Unit getHasAttached() {
        return this.hasAttached;
    }
    public void setHasAttached(Unit unit) {
        this.hasAttached = unit;
    }
}
