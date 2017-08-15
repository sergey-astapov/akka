package com.fullstak.ninja;

public class Shipping {
    private static int minimalNumberOfPackages(int items, int availableLargePackages, int availableSmallPackages) {
        int i = items / 5;
        if (availableLargePackages == 0) {
            return items <= availableSmallPackages ? items : -1;
        } else if (i > availableLargePackages) {
            i = availableLargePackages;
        }

        int diff = items - i * 5;
        if (availableSmallPackages == 0) {
            return i <= availableLargePackages && diff == 0 ? i : -1;
        }
        if (diff == 0) {
            return i;
        } else if (diff <= availableSmallPackages) {
            return i + diff;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(minimalNumberOfPackages(16, 2, 10));
    }
}
