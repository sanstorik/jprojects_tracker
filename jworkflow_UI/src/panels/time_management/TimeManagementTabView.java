package panels.time_management;

import activities.projects.Project;
import utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TimeManagementTabView extends JPanel implements ITimeManagementView {
    private TimeManagementTabPresenter _presenter;
    private GridBagConstraints _constraints;
    private JLabel _currentTimeSpentJLabel;
    private JLabel _timeSpentForProjectJLabel;
    private JComboBox<String> _projectComboBox;
    private Project _chosenProject;

    private JButton _startTimerButton;
    private JButton _stopTimerButton;

    public TimeManagementTabView() {
        _presenter = new TimeManagementTabPresenter(this);
        _constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        createGUI();
        _presenter.fillProjectComboBox();
    }

    @Override public void populateProjectBox(String[] values) {
        _projectComboBox.removeAllItems();

        for (String item : values) {
            _projectComboBox.addItem(item);
        }
    }

    @Override public int getComboBoxSize() {
        return _projectComboBox.getItemCount();
    }

    @Override public Project getCurrentProject() {
        return _chosenProject;
    }

    @Override public void setCurrentProject(Project project) {
        _chosenProject = project;
    }

    @Override public void setTimeSpentForProject(int time) {
        setTimeSpentForTimer(_timeSpentForProjectJLabel, time);
    }

    @Override public void setCurrentTimeSpent(int time) {
        setTimeSpentForTimer(_currentTimeSpentJLabel, time);
    }

    private void createGUI() {
        createImage(FileUtils.loadImage("timer.png"), 0, 0, 1, new Insets(0, 20, 0, 0), 150, 150, 2);
        _currentTimeSpentJLabel = createTimeLabel(3, 0, 1, new Insets(0, -50, 0, 0), 2, 50);
        _timeSpentForProjectJLabel = createTimeLabel(1, 4, 1, new Insets(0, 50, 0, 0), 2, 50);
        JLabel label = createTimeLabel(1, 3, 1, new Insets(0, 50, 0, 0), 2, 25);
        label.setFont(FileUtils.getDefaultFont(Font.BOLD, 20));
        label.setText("Current project total time:");

        _projectComboBox = createProjectsChooseBox();

        createButton("Rename project", 9, 1, new Insets(0,0,0,10), e -> {});
        createButton("Add project", 9, 2, new Insets(0,0,0,10), e -> _presenter.addProject());
        createButton("Remove project", 9, 3, new Insets(0,0,0,10), e -> _presenter.removeProject());

        _startTimerButton = createButton("Start", 1, 1, new Insets(0,0,0,0), e -> {});
        _stopTimerButton = createButton("Stop", 3, 1, new Insets(0,0,0,0), e -> {});

    }

    private JComboBox<String> createProjectsChooseBox() {
        JComboBox<String> projects = new JComboBox<>();
        projects.setFont(FileUtils.getDefaultFont(GridBagConstraints.NONE, 25));
        projects.addItemListener((e -> _presenter.onProjectComboBoxPressed(e.getItem().toString())));

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

    /**
     * Abstraction in setting time to timer
     * @param timer needed timer to be changed
     * @param timeSpent time in seconds
     */
    private void setTimeSpentForTimer(final JLabel timer, final int timeSpent) {
        int hoursInt = timeSpent / 3600;
        int minutesInt = (timeSpent - hoursInt * 3600) / 60;
        int secondsInt = (timeSpent - minutesInt * 60 - hoursInt * 3600);

        String hours = hoursInt < 10 ? 0 + String.valueOf(hoursInt) : String.valueOf(hoursInt);
        String minutes = minutesInt < 10 ? 0 + String.valueOf(minutesInt) : String.valueOf(minutesInt);
        String seconds = secondsInt < 10 ? 0 + String.valueOf(secondsInt) : String.valueOf(secondsInt);

        timer.setText(String.format("%s:%s:%s", hours, minutes, seconds));
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
    int getComboBoxSize();
    Project getCurrentProject();
    void setCurrentProject(Project project);

    /**
     * Set text of total time spent for project
     * @param time time in seconds
     */
    void setTimeSpentForProject(int time);

    /**
     * Set text of current session timer
     * @param time time in seconds
     */
    void setCurrentTimeSpent(int time);
}
