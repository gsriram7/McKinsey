package datascience;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Solution {

    // Fixed Timeout error. Runs in O(nlogn) instead of O(n^2)
    static int[] jobOffers(int[] scores, int[] lowerLimits, int[] upperLimits) {
        int[] res = new int[lowerLimits.length];

        TreeMap<Integer, Integer> map = new TreeMap<>();

        for (int s : scores)
            map.put(s, map.getOrDefault(s, 0) + 1);

        for (int i = 0; i < lowerLimits.length; i++) {
            NavigableMap<Integer, Integer> submap = map.subMap(lowerLimits[i], true, upperLimits[i], true);
            int count = 0;
            for (Integer j : submap.values()) count += j;
            res[i] = count;
        }

        // If this solution works, please give a star to my repo (:
        return res;
    }

    static float[] predictMissingHumidity(String startDate, String endDate, String[] knownTimestamps, float[] humidity, String[] timestamps) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        float[] res = new float[timestamps.length];
        Date stdt = sdf.parse(startDate.split(" ")[0]);
        Date eddt = sdf.parse(endDate.split(" ")[0]);

        int c = 0;
        for (String tst : timestamps) {

            Date td = sdf.parse(tst.split(" ")[0]);
            if (stdt.compareTo(td) <= 0 && eddt.compareTo(td) >= 0) {
                float avg = 0;
                int count = 0;
                for (int i = 0; i < knownTimestamps.length; i++) {
                    Date kt = time.parse(knownTimestamps[i].split(" ")[1]);
                    Date tt = time.parse(tst.split(" ")[1]);
                    Date kd = sdf.parse(knownTimestamps[i].split(" ")[0]);

                    if (kt.compareTo(tt) <= 0 && kd.compareTo(td) <= 0) {

                        avg += humidity[i];
                        count++;
                    }
                }
                if (count != 0)
                    res[c] = avg / count;
                else
                    res[c] = 0;
                c++;
            }

        }

        return res;
    }

    static float[] predictTemperature(String startDate, String endDate, float[] temperature, int n) {
        int f = temperature.length / 24;

        float[] mvg = new float[24 * n];
        int l = 0;
        for (int j = 0; j < 24; j++) {
            for (int k = 0; k < f; k++)
                mvg[l] += temperature[k * +j];
            mvg[l] = mvg[l] / f;
            l++;
        }
        return mvg;

    }

}
