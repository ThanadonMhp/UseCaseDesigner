package ku.cs.usecasedesigner.models;

import java.util.ArrayList;

public class ComponentPreferenceList {
    private ArrayList<ComponentPreference> componentPreferenceList;

    public ComponentPreferenceList() {
        componentPreferenceList = new ArrayList<ComponentPreference>();
    }

    public ArrayList<ComponentPreference> getComponentPreferenceList() {
        return componentPreferenceList;
    }

    public void addComponentPreference(ComponentPreference componentPreference) {
        componentPreferenceList.add(componentPreference);
    }

    public void removeComponentPreference(ComponentPreference componentPreference) {
        componentPreferenceList.remove(componentPreference);
    }

    public ComponentPreference findByPositionID(int positionID) {
        for (ComponentPreference componentPreference : componentPreferenceList) {
            if (componentPreference.getPositionID() == positionID) {
                return componentPreference;
            }
        }
        return null;
    }
}
