package utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MaximumMatchingTest {

    @Test
    public void getMaximumMatching() {

        int[][] arr = {
                {1, 1, 1, 0},
                {1, 0, 0, 1},
                {0, 1, 0, 0}
        };

        int max = MaximumMatching.getMaximumMatchingMatrix(arr);

        int[][] arr1 = {
                {1, 1, 1, 0},
                {1, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };
        int max1 = MaximumMatching.getMaximumMatchingMatrix(arr1);
        Assert.assertEquals(3, max);
        Assert.assertEquals(4, max1);


    }

    @Test
    public void testGetMaximumMatchingList() {
        List<Integer>[] arr1 = new List[3];
        int m1 = 4;
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = new ArrayList<>();
        }
        arr1[0].add(0);
        arr1[0].add(1);
        arr1[0].add(2);
        arr1[1].add(0);
        arr1[1].add(3);
        arr1[2].add(1);

        int max = MaximumMatching.getMaximumMatchingList(arr1, m1);

        List<Integer>[] arr2 = new List[5];
        int m2 = 4;
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = new ArrayList<>();
        }
        arr2[0].add(0);
        arr2[0].add(1);
        arr2[0].add(2);
        arr2[1].add(0);
        arr2[1].add(3);
        arr2[2].add(1);
        arr2[3].add(3);
        arr2[4].add(2);
        int max1 = MaximumMatching.getMaximumMatchingList(arr2, m2);
        Assert.assertEquals(3, max);
        Assert.assertEquals(4, max1);
    }
}