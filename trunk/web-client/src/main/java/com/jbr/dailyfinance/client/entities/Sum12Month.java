package com.jbr.dailyfinance.client.entities;

/**
 *
 * @author jbr
 */
public class Sum12Month implements Comparable<Sum12Month> {
    String categoryName;
    long categoryId;
    double monthSum[];

    public Sum12Month() {
    }

    public Sum12Month(String categoryName, long categoryId, double[] monthSum) {
        this.categoryName = categoryName;
        this.categoryId = categoryId;
        this.monthSum = monthSum;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double[] getMonthSum() {
        return monthSum;
    }

    public void setMonthSum(double[] monthSum) {
        this.monthSum = monthSum;
    }

    @Override
    public String toString() {
        return "Sum12Month{" + "categoryName=" + categoryName + " categoryId=" + categoryId + " monthSum=" + monthSum + '}';
    }



    public double getTotal() {
        double total = 0d;
        if (monthSum == null)
            return total;
        for (int i= 0; i <  monthSum.length; i++) {
            total += monthSum[i];
        }
        return total;
    }

    @Override
    public int compareTo(Sum12Month t) {
        return getCategoryName().compareToIgnoreCase(t.getCategoryName());
    }
}
