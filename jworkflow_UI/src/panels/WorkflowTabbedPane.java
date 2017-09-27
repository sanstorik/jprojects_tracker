package panels;


import panels.infopanel.InformationTabView;
import panels.settings.SettingsTabView;
import panels.time_management.TimeManagementTabView;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;

public final class WorkflowTabbedPane extends TabbedPane {

    public WorkflowTabbedPane() {
        super();
        FileUtils.registerFont("Peppa Pig.ttf");

        createPane();
        add(_pane);
    }

    private void createPane() {
        createTab(0, "Time management", null, new TimeManagementTabView(), (int) (TAB_WIDTH * 1.5f));
        createTab(1, "Settings", null, new SettingsTabView());
        createTab(2, "Information", null, new InformationTabView());
    }
}
