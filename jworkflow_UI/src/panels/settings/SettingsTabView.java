package panels.settings;

import panels.ChangeTabListener;
import panels.GridBagHelper;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class SettingsTabView extends JPanel implements ISettingsView, ChangeTabListener {
    private SettingsTabPresenter _presenter;
    private GridBagHelper _helper;

    public SettingsTabView() {
        _presenter = new SettingsTabPresenter(this);
        _helper = new GridBagHelper(this);

        createButtons();
    }

    @Override public void onTabChanged() { /*empty*/ }

    @Override public File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, "Choose file");

        return result == JFileChooser.APPROVE_OPTION ?
                fileChooser.getSelectedFile() : null;
    }

    private void createButtons() {
        _helper.createButton("Import projects", 1, 0, new Insets(0,0,0,0), (e) -> _presenter.importProjects());
        _helper.createButton("Export project", 1, 1, new Insets(0,0,0,0), (e) -> _presenter.exportProjects());
        _helper.createButton("Import days", 4, 0, new Insets(0,0,0,0), (e) -> _presenter.importDays());
        _helper.createButton("Export days", 4, 1, new Insets(0,0,0,0), (e) -> _presenter.exportDays());

        _helper.createImage(FileUtils.loadImage("recycle.png"), 2, 1, 1, new Insets(-250,0,0,0), 200, 200, 1);
    }
}

interface ISettingsView {
    File chooseFile();
}
