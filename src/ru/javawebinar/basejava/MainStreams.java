package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;

public class MainStreams {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 3, 2, 3};
        System.out.println(Arrays.toString(nums));
        System.out.println(minValue(nums));
        List<Integer> list = Arrays.stream(nums).boxed().toList();
        System.out.println(oddOrEven(list));
    }

    private static int minValue(int[] nums) {
        return Arrays.stream(nums)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    private static List<Integer> oddOrEven(List<Integer> nums) {
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        return nums.stream()
                .filter(x -> sum % 2 == 0 ? x % 2 != 0 : x % 2 == 0)
                .toList();
    }
}
