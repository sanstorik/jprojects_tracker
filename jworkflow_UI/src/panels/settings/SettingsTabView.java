package panels.settings;

import panels.ChangeTabListener;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class SettingsTabView extends JPanel implements ISettingsView, ChangeTabListener {
    private SettingsTabPresenter _presenter;
    private GridBagConstraints _constraints;

    public SettingsTabView() {
        _presenter = new SettingsTabPresenter(this);
        _constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());
        createButtons();
    }

    @Override public void onTabChange() { /*empty*/ }

    @Override public File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showDialog(null, "Choose file");

        return result == JFileChooser.APPROVE_OPTION ?
                fileChooser.getSelectedFile() : null;
    }

    private void createButtons() {
        createButton("Import projects", 1, 0, 20, 0, (e) -> _presenter.importProjects());
        createButton("Export project", 1, 1, 20, -50, (e) -> _presenter.exportProjects());
        createButton("Import days", 4, 0, 20, 0, (e) -> _presenter.importDays());
        createButton("Export days", 4, 1, 20, -50, (e) -> _presenter.exportDays());

        createImage(FileUtils.loadImage("recycle.png"), 2, 1, 1, new Insets(-250,0,0,0), 200, 200, 1);
    }

    private void createButton(String title, int row, int column, int spacing, int spacingBottom, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(200,60));
        button.setBackground(Color.lightGray);
        button.setFont(FileUtils.getDefaultFont(Font.BOLD, 20));
        button.addActionListener(actionListener);

        changeConstraints(GridBagConstraints.NORTH, 10, 1, row, column , new Insets(spacing, 0, spacingBottom, 0));

        add(button, _constraints);
    }

    private void createImage(Image image, int row, int column, int weightX, Insets insets, int width, int height, int gridWidth) {
        JLabel picLabel = new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        picLabel.setOpaque(false);

        changeConstraints(GridBagConstraints.WEST, weightX, 1, row, column, insets);
        _constraints.gridwidth = gridWidth;

        add(picLabel, _constraints);
    }

    private void changeConstraints(int anchor, int weightX, int weightY, int row, int column, Insets insets) {
        _constraints = new GridBagConstraints();
        _constraints.anchor = anchor;
        _constraints.weightx = weightX;
        _constraints.weighty = weightY;
        _constraints.gridx = row;
        _constraints.gridy = column;
        _constraints.insets = insets;
    }
}

interface ISettingsView {
    File chooseFile();
}
