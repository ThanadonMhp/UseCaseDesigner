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

    public ComponentPreference findByIDAndType (int ID, String type) {
        for (ComponentPreference componentPreference : componentPreferenceList) {
            if (componentPreference.getID() == ID && componentPreference.getType().equals(type)) {
                return componentPreference;
            }
        }
        return null;
    }

    public boolean isPreferenceExist(int ID, String type) {
        for (ComponentPreference componentPreference : componentPreferenceList) {
            if (componentPreference.getID() == ID && componentPreference.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
