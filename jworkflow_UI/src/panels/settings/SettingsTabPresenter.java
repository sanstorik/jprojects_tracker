package panels.settings;

import activities.days.Days;
import activities.projects.Projects;

import java.io.File;

class SettingsTabPresenter {
    private ISettingsView _view;

    SettingsTabPresenter(ISettingsView view) {
        _view = view;
    }

    void importProjects() {
        File file = _view.chooseFile();
        if (file != null) {
            Projects.INSTANCE.receive(file.getAbsolutePath());
            Projects.INSTANCE.saveChanges();
        }
    }

    void exportProjects() {
        File file = _view.chooseFile();
        if (file != null) {
            Projects.INSTANCE.export(file.getAbsolutePath());
        }
    }

    void importDays() {
        File file = _view.chooseFile();
        if (file != null) {
            Days.INSTANCE.receive(file.getAbsolutePath());
            Days.INSTANCE.saveChanges();
        }
    }

    void exportDays() {
        File file = _view.chooseFile();
        if (file != null) {
            Days.INSTANCE.export(file.getAbsolutePath());
        }
    }
}
