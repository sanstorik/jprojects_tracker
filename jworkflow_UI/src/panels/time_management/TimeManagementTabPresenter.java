package panels.time_management;

import activities.projects.Project;
import activities.projects.Projects;
import native_hooks.ActivityGlobalListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

class TimeManagementTabPresenter {
    private ITimeManagementView _view;
    private static final int PROJECT_NAME_MAX_CHARACTERS = 20;
    private Timer _timer;
    private Project _currentProject;
    private int _currentTimeSpent;
    private ActivityGlobalListener _projectListener;
    private int _chosenProjectIndex;

    TimeManagementTabPresenter(ITimeManagementView view) {
        _view = view;
        _timer = new Timer(1000, e -> {
            _currentProject.increaseTimeSpent(1);
            _currentTimeSpent += 1;
            _view.setCurrentTimeSpent(_currentTimeSpent);
            _view.setTimeSpentForProject(_currentProject.getTimeSpentInSec());
            Projects.INSTANCE.saveChanges();
        });
    }

    void startTimer() {
        if (_currentProject != null) {
            _projectListener = ActivityGlobalListener.of(_currentProject);
            _projectListener.startTracking();
            _timer.start();
            _view.blockButtonsOnTimerStart();
        }
    }

    void stopTimer() {
        _timer.stop();
        _projectListener.stopTracking();
        _currentTimeSpent = 0;
        _view.setCurrentTimeSpent(0);
        _view.unblockButtonsOnTimerEnd();
    }

    void onProjectComboBoxPressed() {
        if (_timer.isRunning()) {
            _view.setComboBoxSelectedIndex(_chosenProjectIndex);
            return;
        }

        Project project = Projects.INSTANCE.info().get(_view.getComboBoxSelectedItem());
        if (project != null) {
            _currentProject = project;
            _chosenProjectIndex = _view.getComboBoxSelectedIndex();
            _view.setTimeSpentForProject(_currentProject.getTimeSpentInSec());
        } else {
            _currentProject = null;
            _view.setTimeSpentForProject(0);
        }
    }

    void fillProjectComboBox() {
        if (_timer.isRunning()) return;

        List<String> names = new ArrayList<>();
        for (Project project : Projects.INSTANCE.info().getProjects()) {
            names.add(project.getProjectName());
        }

        _view.populateProjectBox(names.toArray(new String[Projects.INSTANCE.info().getProjects().size()]));

        if (names.size() > 0) {
            onProjectComboBoxPressed();
        }
    }

    void addProject() {
        String value = _view.inputFileNameDialog("Project dialog", "Input project name");

        if (value != null && !value.matches("[\\s]*") && value.length() < PROJECT_NAME_MAX_CHARACTERS
                && Projects.INSTANCE.info().addEmpty(value.trim())) {
            Projects.INSTANCE.saveChanges();
            fillProjectComboBox();
        } else {
            _view.showErrorMessage("Error", "Could not add new project.");
        }
    }

    void removeProject() {
        String value = _view.inputFileNameDialog("Project dialog", "Input project name");
        Project project;

        if (value != null && (project = Projects.INSTANCE.info().get(value)) != null
                && Projects.INSTANCE.info().remove(project)) {
            Projects.INSTANCE.saveChanges();
            if (_view.getComboBoxSize() > 0) {
                fillProjectComboBox();
            }
        } else {
            _view.showErrorMessage("Error", "Could not remove the project.");
        }
    }

    void renameProject() {
        String value = _view.inputFileNameDialog("Project dialog", "Input new project name");

        if (value != null && value.length() < PROJECT_NAME_MAX_CHARACTERS
                && Projects.INSTANCE.info().rename(_currentProject.getProjectName(), value)) {
            Projects.INSTANCE.saveChanges();
            fillProjectComboBox();
        } else {
            _view.showErrorMessage("Error", "Could not rename the project.");
        }

    }
}
