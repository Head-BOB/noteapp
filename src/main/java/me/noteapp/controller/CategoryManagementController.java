package me.noteapp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.noteapp.model.Category;
import me.noteapp.model.Note;
import me.noteapp.service.CategoryService;
import org.controlsfx.control.CheckListView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryManagementController {

    @FXML
    private CheckListView<Category> categoriesListView;
    @FXML
    private TextField newCategoryField;
    @FXML
    private Button addNewCategoryButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Stage dialogStage;
    private Note currentNote;
    private final CategoryService categoryService = new CategoryService();
    private final ObservableList<Category> allUserCategories = FXCollections.observableArrayList();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void loadData(Note note) {
        this.currentNote = note;
        try {
            allUserCategories.setAll(categoryService.getCategoriesForUser());
            categoriesListView.setItems(allUserCategories);

            List<Category> assignedCategories = categoryService.getCategoriesForNote(currentNote.getId());
            for (Category assigned : assignedCategories) {
                categoriesListView.getCheckModel().check(assigned);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not load categories.");
        }
    }

    @FXML
    private void handleAddNewCategory() {
        String categoryName = newCategoryField.getText().trim();
        if (categoryName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Error", "Category name cannot be empty.");
            return;
        }

        try {
            Category newCategory = categoryService.createCategory(categoryName);
            allUserCategories.add(newCategory);
            newCategoryField.clear();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to create category.");
        }
    }

    @FXML
    private void handleSave() {
        List<Category> selectedCategories = categoriesListView.getCheckModel().getCheckedItems();
        try {
            List<Category> oldCategories = categoryService.getCategoriesForNote(currentNote.getId());
            for (Category cat : oldCategories) {
                categoryService.removeNoteFromCategory(currentNote.getId(), cat.getId());
            }
            for (Category cat : selectedCategories) {
                categoryService.addNoteToCategory(currentNote.getId(), cat.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update categories.");
        }
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}