package ch.hefr.edu.sleepyfinder.ch.rangeEnum;

/**
 * Created by johnnyhaymoz on 28.08.16.
 */
public enum Range {
    FOOT_5MIN("5 minutes de marche",277),
    FOOT_10MIN("10 minutes de marche",554),
    FOOT_20MIN("20 minutes de marche",831),
    CAR_5MIN("5 minutes en voiture",2000),
    CAR_10MIN("10 minutes en voiture",4000),
    CAR_20MIN("20 minutes en voiture",8000);

    private final String value;
    private final int range;

    Range(String value, int range){
        this.value = value;
        this.range = range;
    }

    @Override
    public String toString() {
        return getValue();
    }

    public int getRange() {
        return range;
    }

    public String getValue(){
        return value;
    }


}
