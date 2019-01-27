package de.pkmnplatin.wnp4j.util;

public class TimeConverter {

    private static final long[] TIME_UNITS = {1, 60, 60 * 60, 24 * 60 * 60, 30 * 24 * 60 * 60, 365 * 30 * 24 * 60 * 60};

    public static long toMillis(String raw) {
        long millis = 0L;
        String[] split = raw.split(":");
        reverse(split);
        for(int count = 0; count < split.length; count++) {
            try {
                 int parsed = Integer.parseInt(split[count]);
                millis += (parsed * TIME_UNITS[count]) * 1000;
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        return millis;
    }

    private static void reverse(final Object[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return;
        }
        int i = startIndexInclusive < 0 ? 0 : startIndexInclusive;
        int j = Math.min(array.length, endIndexExclusive) - 1;
        Object tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    private static void reverse(final Object[] array) {
        if (array == null) {
            return;
        }
        reverse(array, 0, array.length);
    }

}
