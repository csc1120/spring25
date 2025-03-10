package week8;


public class BinarySearch {
    public static void main(String[] args) {
        int[] numbers = {2, 4, 6, 8, 10, 12, 14};
        System.out.println(binarySearchRecursive(numbers, 12));
    }

    public static boolean binarySearchRecursive(int[] data, int target) {
        return binarySearchRecursive(data, target, 0, data.length);
    }

    private static boolean binarySearchRecursive(int[] data, int target, int begin, int end) {
        if (begin == end) {
            return false;
        }
        boolean found = false;
        int middle = (begin + end) / 2;
        if (data[middle] == target) {
            found = true;
        } else if (data[middle] < target) {
            found = binarySearchRecursive(data, target, middle + 1, end);
        } else {
            found = binarySearchRecursive(data, target, begin, middle);
        }
        return found;
    }

    public static boolean binarySearch(int[] data, int target) {
        boolean found = false;
        int begin = 0;
        int end = data.length;
        while (begin < end && !found) {
            int middle = (begin + end) / 2;
            if (data[middle] == target) {
                found = true;
            } else if (data[middle] < target) {
                begin = middle + 1;
            } else {
                end = middle;
            }
        }
        return found;
    }

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        int answer = n * factorial(n-1);
        return answer;
    }

}
