package utils;

public class KeyBinderDictionary {

    String[] names;

    int[] vals;

    int addIndex = 0;

    public KeyBinderDictionary(int size) {

        vals = new int[size];
        names = new String[size];

    }

    public void add(String key, int val) {

        names[addIndex] = key;
        vals[addIndex] = val;
        addIndex++;
        
    }

    public int get(String key) {

        for (int i = 0; i < names.length; i++) {

            if (key.equals(names[i])) {

                return vals[i];

            }

        }

        return 0;

    }

}