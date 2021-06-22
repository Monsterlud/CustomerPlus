package model;

public class NumApptsByMonth {

    Month month;
    int number;

    public NumApptsByMonth (Month month, int number) {
        this.month = month;
        this.number = number;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
