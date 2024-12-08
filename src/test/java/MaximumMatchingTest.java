import org.junit.Assert;

public class MaximumMatchingTest {

    @org.junit.Test
    public void getMaximumMatching() {

        int[][] arr = {
                {1, 1, 1, 0},
                {1, 0, 0, 1},
                {0, 1, 0, 0}
        };

        int max = MaximumMatching.getMaximumMatching(arr);

        int[][] arr1 = {
                {1, 1, 1, 0},
                {1, 0, 0, 1},
                {0, 1, 0, 0},
                {0, 0, 0, 1},
                {0, 0, 1, 0}
        };
        int max1 = MaximumMatching.getMaximumMatching(arr1);
        Assert.assertEquals(3, max);
        Assert.assertEquals(4, max1);


    }
}