import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


public class Solution {
    private Map<Long, Long> state;

    public Solution(String filename) { this.state = parser(filename); }

    private Map<Long, Long> parser(String filename) {
        Map<Long, Long> state = new HashMap<>();

        try {
            FileInputStream stream = new FileInputStream(filename);
            Scanner sc = new Scanner(stream);

            if (sc.hasNext()) {
                String[] initState = sc.next().split(",");

                for (String lanternFish : initState) {
                    Long fish = Long.parseLong(lanternFish);

                    if (state.get(fish) == null) {
                        state.put(fish, (long)1);
                    } else {
                        state.put(fish, state.get(fish) + 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return state;
    }

    public void solve(int days) {
        Map<Long, Long> newState;
        long total = 0;

        for (int day=0; day < days; ++day) {
            newState = new HashMap<>();

            for (Map.Entry<Long, Long> fish: this.state.entrySet()) {
                if (fish.getKey() == 0) {
                    newState.put((long) 8, fish.getValue());
                    newState.put((long) 6, fish.getValue());
                } else {
                    if ((newState.get((long) 6) != null) && (fish.getKey() == 7)) {
                        newState.put((long) 6, fish.getValue() + newState.get((long) 6));
                    } else {
                        newState.put(fish.getKey() - 1, fish.getValue());
                    }
                }
            }

            this.state = newState;
        }

        for (Long value : state.values()) { total += value; }

        System.out.println("Total lantern fish for " + days + " days: " + total);
    }
}