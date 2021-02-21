import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class FoodInfo {
    public int value;
    public int weight;
    public double unitCost;

    FoodInfo(int value, int weight, double unitCost) {
        this.value = value;
        this.weight = weight;
        this.unitCost = unitCost;
    }

    @Override
    public String toString() {
        return "FoodInfo{" +
                "value=" + value +
                ", weight=" + weight +
                ", unitCost=" + unitCost +
                '}';
    }
}

public class FractionalKnapsack {
    private static double getOptimalValue(int capacity, int[] values, int[] weights) {
        double value = 0;
        List<FoodInfo> foodList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            foodList.add(new FoodInfo(values[i], weights[i], (double) values[i] / weights[i]));
        }
        foodList.sort(Comparator.comparingDouble((FoodInfo o) -> o.unitCost).reversed());
        for (FoodInfo food : foodList) {
            int taken = Integer.min(food.weight, capacity);
            capacity -= taken;
            value += (food.unitCost) * taken;
            if (capacity == 0) break;
        }
        return value;
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int capacity = scanner.nextInt();
        int[] values = new int[n];
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = scanner.nextInt();
            weights[i] = scanner.nextInt();
        }
        System.out.println(getOptimalValue(capacity, values, weights));
    }
} 
