package panels.time_management;

import activities.projects.Project;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TimeManagementTabView extends JPanel implements ITimeManagementView {
    private TimeManagmentTabPresenter _presenter;
    private GridBagConstraints _constraints;
    private JLabel _currentTimeSpentJLabel;
    private JComboBox<String> _projectComboBox;
    private Project _chosenProject;

    private JButton _startTimerButton;
    private JButton _stopTimerButton;

    public TimeManagementTabView() {
        _presenter = new TimeManagmentTabPresenter(this);
        _constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        createGUI();
        populateProjectBox(new String[]{"Project One", "Project Two"});
    }

    @Override public void populateProjectBox(String[] values) {
        _projectComboBox.removeAllItems();

        for (String item : values) {
            _projectComboBox.addItem(item);
        }
    }

    @Override public Project getCurrentProject() {
        return _chosenProject;
    }

    @Override public void setCurrentProject(Project project) {
        _chosenProject = project;
    }

    private void createGUI() {
        createImage(FileUtils.loadImage("timer.png"), 0, 0, 1, new Insets(0, 20, 0, 0), 150, 150, 2);
        _currentTimeSpentJLabel = createTimeLabel(3, 0, 1, new Insets(0, -50, 0, 0), 2, 50);

        _projectComboBox = createProjectsChooseBox();
        createButton("Rename project", 9, 1, new Insets(0,0,0,10), e -> {});
        createButton("Add project", 9, 2, new Insets(0,0,0,10), e -> {});
        createButton("Remove project", 9, 3, new Insets(0,0,0,10), e -> {});
        _startTimerButton = createButton("Start", 1, 1, new Insets(0,0,0,0), e -> {});
        _stopTimerButton = createButton("Stop", 3, 1, new Insets(0,0,0,0), e -> {});
    }

    private JComboBox<String> createProjectsChooseBox() {
        JComboBox<String> projects = new JComboBox<>();
        projects.setFont(FileUtils.getDefaultFont(GridBagConstraints.NONE, 25));
        projects.addItemListener((e -> {}));

        changeConstraints(GridBagConstraints.EAST, 1, 1, 9, 0, new Insets(0,0,0,10));
        add(projects, _constraints);

        return projects;
    }


    private JLabel createTimeLabel(int row, int column, int weightX, Insets insets, int gridWidth, int fontSize) {
        JLabel label = new JLabel("00:00:00");
        label.setFont(new Font("Calibri", Font.BOLD, fontSize));

        changeConstraints(GridBagConstraints.WEST, weightX, 1, row, column, insets);
        _constraints.gridwidth = gridWidth;
        add(label, _constraints);

        return label;
    }

    private void createImage(Image image, int row, int column, int weightX, Insets insets, int width, int height, int gridWidth) {
        JLabel picLabel = new JLabel(new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        picLabel.setOpaque(false);

        changeConstraints(GridBagConstraints.WEST, weightX, 1, row, column, insets);
        _constraints.gridwidth = gridWidth;

        add(picLabel, _constraints);
    }


    private JButton createButton(String title, int row, int column, Insets insets, ActionListener actionListener) {
        JButton button = new JButton(title);
        button.setPreferredSize(new Dimension(200,60));
        button.setBackground(Color.lightGray);
        button.setFont(FileUtils.getDefaultFont(Font.BOLD, 20));
        button.addActionListener(actionListener);

        changeConstraints(GridBagConstraints.EAST, 10, 1, row, column , insets);

        add(button, _constraints);

        return button;
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

interface ITimeManagementView {
    void populateProjectBox(String[] values);
    Project getCurrentProject();
    void setCurrentProject(Project project);
}
