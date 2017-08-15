package com.fullstak.ninja;

public class Branches {
    private static int count(int[] tree) {
        int ri = -1;
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] == -1) {
                ri = i;
                break;
            }
        }
        if (ri == -1) {
            return 0;
        }

        int lbc = 0;
        for (int i = 0; i < ri; i++) {
            if (i + 1 == ri) break;
            lbc++;
        }

        int rbc = 0;
        for (int i = ri + 1; i < tree.length; i++) {
            if (i + 1 == tree.length) break;
            rbc++;
        }
        return lbc + rbc;
    }

    public static void main(String[] args) {
        //System.out.println(Branches.count(new int[] { 1, 3, 1, -1, 3 }));
        System.out.println(Branches.count(new int[] {1, -1, 3,1 }));
    }
}