package wrapper;

import interfaces.MotorGroup;

public class AdaptableDrive {

    MotorGroup[] motorGroups;
    String[] names;

    public AdaptableDrive(MotorGroup... motorGroups) {

        this.motorGroups = motorGroups;

    }

    // make sure the names are in the same order as the motorgroups they refer to
    public void giveNames(String... names) {

        this.names = names;

    }

    public void controlMotorGroups(double speed, String... names) {

        if (names[0].equals("All")) {

            for (int i = 0; i < motorGroups.length; i++) {

                motorGroups[i].control(speed);

            }

        } else {

            int[] nameIndexes = findIndexOfname(names);

            for (int j = 0; j < nameIndexes.length; j++) {

                motorGroups[nameIndexes[j]].control(speed);

            }

        }

    }

    private int[] findIndexOfname(String[] namesToFind) {
        int[] indexes = new int[namesToFind.length];
        int indexCounter = 0;

        for (int i = 0; i < namesToFind.length; i++) {

            for (int j = 0; j < this.names.length; j++) {

                if (namesToFind[i].equals(this.names[j])) {

                    indexes[indexCounter] = j;
                    indexCounter++;

                }

            }

        }

        return indexes;

    }

}