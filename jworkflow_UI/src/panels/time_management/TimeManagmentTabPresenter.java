package panels.time_management;

import activities.projects.Project;
import activities.projects.Projects;

import java.util.ArrayList;
import java.util.List;

class TimeManagmentTabPresenter {
    private ITimeManagementView _view;

    TimeManagmentTabPresenter(ITimeManagementView view) {
        _view = view;
    }

    void onProjectComboBoxPressed(String value) {
        Project project = Projects.INSTANCE.info().get(value);
        if (project != null) {
            _view.setCurrentProject(project);
            _view.setTimeSpentForProject(_view.getCurrentProject().getTimeSpentInSec());
        } else {
            _view.setCurrentProject(null);
            _view.setTimeSpentForProject(0);
        }
    }

    void fillProjectComboBox() {
        List<String> names = new ArrayList<>();
        for (Project project : Projects.INSTANCE.info().getProjects()) {
            names.add(project.getProjectName());
        }

        _view.populateProjectBox(names.toArray(new String[Projects.INSTANCE.info().getProjects().size()]));

        if (names.size() > 0) {
            onProjectComboBoxPressed(names.get(0));
        }
    }

    void addProject() {
        boolean added = Projects.INSTANCE.info().addEmpty("hello world");
        if (added) {
            Projects.INSTANCE.saveChanges();
            fillProjectComboBox();
        }
    }

    void removeProject() {
        Project project = Projects.INSTANCE.info().get("hello");
        boolean removed = project != null && Projects.INSTANCE.info().remove(project);
        Projects.INSTANCE.saveChanges();

        if (removed && _view.getComboBoxSize() > 0) {
            fillProjectComboBox();
        }
    }
}
