package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class PreferenceList {
    private ArrayList<Preference> preferenceList;

    public PreferenceList() {
        preferenceList = new ArrayList<Preference>();
    }

    public ArrayList<Preference> getPreferenceList() {
        return preferenceList;
    }

    public void addPreference(Preference preference) {
        preferenceList.add(preference);
    }

    public void removePreference(Preference preference) {
        preferenceList.remove(preference);
    }

}
