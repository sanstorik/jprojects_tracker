package panels;


import panels.settings.SettingsTabView;
import panels.time_management.TimeManagementTabView;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;

public final class WorkflowTabbedPane extends JPanel {
    private static final int TAB_WIDTH = 80;
    private static final int TAB_HEIGHT = 30;
    private static final int MAX_COUNT_OF_TABS = 8;
    private JTabbedPane _pane;

    public WorkflowTabbedPane() {
        super(new GridLayout(1,1));
        FileUtils.registerFont("Peppa Pig.ttf");

        createPane();
        add(_pane);
    }

    private void createPane() {
        _pane = new JTabbedPane();

        createTab(0, "Time management", null, new TimeManagementTabView(), (int)(TAB_WIDTH * 1.5f));
        createTab(1, "Settings", null, new SettingsTabView());
        _pane.addChangeListener(e -> ((ChangeTabListener)_pane.getSelectedComponent()).onTabChange() );
    }

    private void createTab(int index, String title, Icon icon, JComponent context) {
        createTab(index, title, icon, context, TAB_WIDTH);
    }

    private void createTab(int index, String title, Icon icon, JComponent context, int width) {
        if (index >= MAX_COUNT_OF_TABS)
            throw new IllegalArgumentException("tabs out of range, MAX - " + MAX_COUNT_OF_TABS);
        _pane.addTab(title, icon, context);

        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(width, TAB_HEIGHT));
        _pane.setTabComponentAt(index, label);
    }
}
