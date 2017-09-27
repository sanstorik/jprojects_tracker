package panels.infopanel;

import panels.ChangeTabListener;
import panels.TabbedPane;
import panels.settings.SettingsTabView;
import panels.time_management.TimeManagementTabView;

import javax.swing.*;
import java.awt.*;

/**
 * Pane that's gonna be inside
 * with tabs data and project
 * to show all activity information.
 */
public class InformationTabView extends TabbedPane implements ChangeTabListener {

    public InformationTabView() {
        super();
        createGUI();
    }

    @Override public void onTabChanged() { /*empty*/ }

    private void createGUI() {
        createTab(0, "Projects", null, new ProjectsInformationTabView());
        createTab(1, "Days", null, new DaysInformationTabView());
        add(_pane);
    }

}
