package panels.infopanel;

import panels.ChangeTabListener;
import panels.GridBagHelper;

import javax.swing.*;
import java.awt.*;

class ProjectsInformationTabView extends JPanel implements ChangeTabListener {
    GridBagHelper _helper;

    ProjectsInformationTabView() {
        _helper = new GridBagHelper(this);
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        _helper.createLabel("sosesh devachka projects", 0, 0, 1, new Insets(0,0,0,0), 2, 40);
    }

    @Override public void onTabChanged() {

    }
}
