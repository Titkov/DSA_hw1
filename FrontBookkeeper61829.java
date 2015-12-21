package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class FrontBookkeeper61829 implements IFrontBookkeeper {

    private List<Unit> army;

    public FrontBookkeeper61829() {
        this.army = new ArrayList<Unit>();
    }

    public List<Unit> getAllUnits() {
        return this.army;
    }

    public String updateFront(String[] news) {
        HandleNews(news);
        return null;
    }

    private void HandleNews(String[] news) {
        for(int i = 0; i < news.length; i++) {

            if(news[i].contains("=")) {
                AddUnits(news[i]);
            }

            else if(news[i].contains("after soldier")) {
                AttachAfter(news[i]);
            }

            else if(news[i].contains("attached to")) {
                AttachUnits(news[i]);
            }

            else if(news[i].contains("died")) {
                Remove(news[i]);
            }

            else if(news[i].contains("show soldier")) {
                ShowSoldier(news[i]);
            }

            else if(news[i].contains("show")) {
                Show(news[i]);
            }
        }
    }

    private void AddUnits(String news) {
        String nameOfUnit = "";
        Integer iterator = 0;

        for(int i=0; i<news.length();i++) {
            if(news.charAt(i) == ' ') {
                iterator = i;
                break;
            }
            nameOfUnit += news.charAt(i);
        }

        Unit unit = new Unit();
        unit.setName(nameOfUnit);

        List<Integer> indexes = new ArrayList<Integer>();

        String strArr = news.substring(iterator+4,news.length()-1);

        String[] array = strArr.split("\\, ",-1);

        if(strArr.length() > 0) {
            for(int j=0; j<array.length;j++) {
                indexes.add(Integer.parseInt(array[j]));
            }
        }

        unit.setSoldierNumbers(indexes);

        this.army.add(unit);
    }

    private Integer getUnitIndex(String name) {
        for(int i=0; i < this.army.size(); i++) {
            if(this.army.get(i).getName().toString().contentEquals(name)) {
                return i;
            }
        }

        return -1;
    }

    private void AttachUnits(String news) {
        String fromUnitName = "";
        String toUnitName = "";
        Integer iterator = 0;

        for(int i=0; i<news.length();i++) {

            if(news.charAt(i) == ' ') {
                iterator = i;
                break;
            }
            fromUnitName += news.charAt(i);
        }

        for(int i=iterator+13;i<news.length();i++) {
            toUnitName += news.charAt(i);
        }

        Unit toUnit = this.army.get(getUnitIndex(toUnitName));
        Unit fromUnit = this.army.get(getUnitIndex(fromUnitName));

        List<Integer> arrFromUnit = fromUnit.getUnitNumber();

        if(fromUnit.attachedTo == null)
        {

            for(int i=0; i < arrFromUnit.size(); i++) {

                toUnit.addIndexOfSoldier(arrFromUnit.get(i));
            }
            toUnit.setHasAttached(fromUnit);
            fromUnit.setAttachedTo(toUnit);
        }
        else {
            Unit third = fromUnit.getAttachedTo();
            third.getUnitNumber().removeAll(fromUnit.getUnitNumber());
            third.hasAttached = null;
            fromUnit.attachedTo = null;

            toUnit.getUnitNumber().addAll(fromUnit.getUnitNumber());
            toUnit.setHasAttached(fromUnit);
            fromUnit.setAttachedTo(toUnit);
        }
    }

    private void AttachAfter(String news) {
        String fromUnitName = "";
        String toUnitName = "";

        Integer iterator = 0;
        Integer afterIndex = news.indexOf("after");
        Integer soldierIndex = news.indexOf("soldier");

        Integer soldier = Integer.parseInt(news.substring(soldierIndex+8, news.length()));

        for(int i=0; i<news.length();i++) {

            if(news.charAt(i) == ' ') {
                iterator = i;
                break;
            }
            fromUnitName += news.charAt(i);
        }
        for(int i=iterator+13;i<afterIndex-1;i++) {
            toUnitName += news.charAt(i);
        }


        Unit toUnit = this.army.get(getUnitIndex(toUnitName));
        Unit fromUnit = this.army.get(getUnitIndex(fromUnitName));


        Integer indexOfSoldier = toUnit.getUnitNumber().indexOf(soldier)+1;

        if(fromUnit.attachedTo == null) {
            toUnit.getUnitNumber().addAll(indexOfSoldier + 1, fromUnit.soldierNumbers);

            toUnit.setHasAttached(fromUnit);
            fromUnit.setAttachedTo(toUnit);
        }
        else {
            Unit third = fromUnit.getAttachedTo();
            third.getUnitNumber().removeAll(fromUnit.getUnitNumber());
            third.hasAttached = null;
            fromUnit.attachedTo = null;

            toUnit.getUnitNumber().addAll(indexOfSoldier, fromUnit.getUnitNumber());
            toUnit.setHasAttached(fromUnit);
            fromUnit.setAttachedTo(toUnit);
        }

    }

    private void Remove(String news) {
        Integer from = 0;
        Integer to = 0;

        Integer betweenIndex = news.indexOf("between");
        Integer andIndex = news.indexOf("and");
        Integer fromIndex = news.indexOf("from");

        from = Integer.parseInt(news.substring(betweenIndex+8, andIndex-1));
        to = Integer.parseInt(news.substring(andIndex+4,fromIndex-1));

        List<Integer> fromTo = new ArrayList<Integer>();
        for(int i=from; i<=to;i++) {
            fromTo.add(i);
        }


        for(int i=0; i < this.army.size(); i++) {
            Unit curr = this.army.get(i);
            curr.soldierNumbers.removeAll(fromTo);
        }
    }

    private void Show(String news) {

        String unitName = "";
        for(int i = 5; i < news.length(); i++) {
            unitName += news.charAt(i);
        }

        Unit unit = this.army.get(getUnitIndex(unitName));

        System.out.println(unit.soldierNumbers);
        unitName = "";
    }

    private static String join(Collection<?> col, String delim) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> iter = col.iterator();
        if (iter.hasNext())
            sb.append(iter.next().toString());
        while (iter.hasNext()) {
            sb.append(delim);
            sb.append(iter.next().toString());
        }
        return sb.toString();
    }

    private void ShowSoldier(String news) {

        Integer soldierIndex = news.indexOf("soldier");

        Integer soldier = Integer.parseInt(news.substring(soldierIndex+8,news.length()));

        List<String> all = new ArrayList<String>();

        for(int i=0; i < this.army.size(); i++) {

            if(this.army.get(i).getUnitNumber().contains(soldier)) {
                all.add(this.army.get(i).getName());
            }
        }

        String joined = join(all,", ");
        System.out.println(joined);
    }
}
