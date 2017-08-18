package panels;

import activities.days.Days;
import activities.projects.Projects;
import native_hooks.ActivityGlobalListener;
import panels.settings.SettingsTabView;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public final class WorkflowTabbedPane extends JPanel {
    private static final int TAB_WIDTH = 80;
    private static final int TAB_HEIGHT = 30;
    private static final int MAX_COUNT_OF_TABS = 8;
    private JTabbedPane _pane;

    public WorkflowTabbedPane() {
        super(new GridLayout(1,1));
        createPane();

        add(_pane);
    }

    private void createPane() {
        _pane = new JTabbedPane();

        createTab(0, "Time management", null, makeStringPanel("Hello"), (int)(TAB_WIDTH * 1.5f));
        createTab(1, "Settings", null, new SettingsTabView());
        ActivityGlobalListener.of(Days.INSTANCE.getCurrentDay()).startTracking();

        new java.util.Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Days.INSTANCE.saveChanges();
            }
        }, 5000);
    }

    private JComponent makeStringPanel(String text) {
        JPanel panel = new JPanel(false);

        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1,1));
        panel.add(label);
        return panel;
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
