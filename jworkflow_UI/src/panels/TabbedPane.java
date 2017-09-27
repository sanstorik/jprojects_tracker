package panels;

import javax.swing.*;
import java.awt.*;

public abstract class TabbedPane extends JPanel {
    public static final int TAB_WIDTH = 80;
    public static final int TAB_HEIGHT = 30;
    public static final int MAX_COUNT_OF_TABS = 8;
    protected JTabbedPane _pane;

    protected TabbedPane() {
        super(new GridLayout(1,1));
        _pane = new JTabbedPane();

        _pane.addChangeListener(e -> ((ChangeTabListener)_pane.getSelectedComponent()).onTabChanged() );
    }

    protected void createTab(int index, String title, Icon icon, JComponent context) {
        createTab(index, title, icon, context, TAB_WIDTH);
    }

    protected void createTab(int index, String title, Icon icon, JComponent context, int width) {
        if (index >= MAX_COUNT_OF_TABS)
            throw new IllegalArgumentException("tabs out of range, MAX - " + MAX_COUNT_OF_TABS);
        _pane.addTab(title, icon, context);

        JLabel label = new JLabel(title);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(width, TAB_HEIGHT));
        _pane.setTabComponentAt(index, label);
    }
}