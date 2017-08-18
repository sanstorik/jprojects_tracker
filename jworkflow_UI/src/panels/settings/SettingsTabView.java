package panels.settings;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SettingsTabView extends JPanel implements ISettingsView {
    private SettingsTabPresenter _presenter;
    private GridBagLayout _layout;
    private GridBagConstraints _constraints;

    public SettingsTabView() {
        _presenter = new SettingsTabPresenter(this);
        _layout = new GridBagLayout();
        _constraints = new GridBagConstraints();
        setLayout(_layout);
        createButtons();
    }

    @Override public File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, "Choose file");

        return result == JFileChooser.APPROVE_OPTION ?
                fileChooser.getSelectedFile() : null;
    }

    private void createButtons() {
        createButton("Import projects", 1, 0, 20, 1).addActionListener((event) -> _presenter.importProjects());
        createButton("Export project", 1, 1, 20, 1);
        createButton("Import data", 1, 2, 20, 1);
        createButton("Export data", 1, 3, 20, 1);
    }

    private JButton createButton(String title, int row, int column, int spacing, int weightX) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(FileUtils.loadImage("images/download.jpg")));

        button.setPreferredSize(new Dimension(200,60));
        _constraints.anchor = GridBagConstraints.NORTH;
        _constraints.weightx = weightX;
        _constraints.weighty = 1;
        _constraints.gridx = row;
        _constraints.gridy = column;
        _constraints.insets = new Insets(spacing, 0, 0, 0);

        add(button, _constraints);
        return button;
    }

}

interface ISettingsView {
    File chooseFile();
}
