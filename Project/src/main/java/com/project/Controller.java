package com.project;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

    @FXML
    private Button Backbutton;

    @FXML
    private Button Backbutton1;


    @FXML
    private TextField usernameInForgot;

    @FXML
    private TextField ForgotPasswordAnswer;

    @FXML
    private ComboBox<String> ForgotQuestion;

    @FXML
    private Hyperlink HyperlinkForgotPassword;

    @FXML
    private TextField UseradminRegister;

    @FXML
    private TextField answer;

    @FXML
    private Button buttonAlready;

    @FXML
    private Button changePassword;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private AnchorPane createForm;

    @FXML
    private Button createNewAccount;

    @FXML
    private Button loginBotton;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField passWAdminRegister;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private Button proceedBtn;

    @FXML
    private ComboBox<String> question;

    @FXML
    private AnchorPane questionForm;

    @FXML
    private AnchorPane questionForm1;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField registerUser;

    @FXML
    private Button signUpBotton;

    @FXML
    private TextField textFieldUsername;

    private String[] questionList = { "What is your favorite Color?", "What is your favorite Food?" };

    private Alert regAlert;

    private Connection con;

    public static String username;

    private void clearRegistrationForm() {
        registerUser.clear();
        registerPassword.clear();
        question.getSelectionModel().clearSelection();
        answer.clear();
        UseradminRegister.clear();
        passWAdminRegister.clear();
    }

    public void regBtn() {
        if (registerUser.getText().isEmpty()
                || registerPassword.getText().isEmpty()
                || question.getSelectionModel().isEmpty()
                || answer.getText().isEmpty()
                || UseradminRegister.getText().isEmpty()
                || passWAdminRegister.getText().isEmpty()) {
            regAlert = new Alert(AlertType.ERROR);
            regAlert.setTitle("Register error");
            regAlert.setHeaderText(null);
            regAlert.setContentText("Please fill in all the fields");
            regAlert.show();
            return;
        }

        try {
            con = DatabaseConection.gC();
            if (con == null) {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Database Error");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Cannot connect to database!");
                regAlert.show();
                return;

            }
            if (DatabaseForUser.checkUser(con, registerUser.getText())) {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Register Error");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Username already exists!");
                regAlert.show();
                return;

            }

            String dbUser = DatabaseForUser.getUser(con, 1);
            String dbPassword = DatabaseForUser.getPassword(con, 1);
            if (dbUser == null || dbPassword == null) {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Register Error");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Admin account not found!");
                regAlert.show();
                return;

            }
            if (UseradminRegister.getText().equals(dbUser) && passWAdminRegister.getText().equals(dbPassword)) {
                String[] user = new String[5];
                user[0] = registerUser.getText();
                user[1] = registerPassword.getText();
                user[2] = question.getSelectionModel().getSelectedItem().toString();
                user[3] = answer.getText();
                user[4] = "Cashier";
                DatabaseForUser.insert(con, user[0], user[1], user[2], user[3], user[4]);
                regAlert = new Alert(AlertType.INFORMATION);
                regAlert.setTitle("Register Success");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Account has been success create");
                regAlert.show();
                clearRegistrationForm();

                TranslateTransition slider = new TranslateTransition();

                slider.setNode(createForm);
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.5));

                slider.setOnFinished((ActionEvent e) -> {
                    buttonAlready.setVisible(false);
                    createNewAccount.setVisible(true);
                });

                slider.play();

            } else {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Admin Verification");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Invalid admin credentials!");
                regAlert.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void regquestionList() {
        ArrayList<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        question.setItems(listData);
    }

    public void forgotPasswordList(){
        ArrayList<String> ListQ = new ArrayList<>();
        for (String data : questionList) {
            ListQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(ListQ);
        ForgotQuestion.setItems(listData);

    }

    private Alert alert;

    public void proceedBtn(){
        if(ForgotQuestion.getSelectionModel().isEmpty() || ForgotPasswordAnswer.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
            return;
        }else{
            try(Connection con = DatabaseConection.gC()){
                if(con == null){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Database Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Cannot connect to database!");
                    alert.showAndWait();
                    return;
                }
                if(DatabaseForUser.checkSecurityQuestion(con, usernameInForgot.getText(), (String)ForgotQuestion.getSelectionModel().getSelectedItem(),ForgotPasswordAnswer.getText() )){
                    questionForm.setVisible(false);
                    questionForm1.setVisible(true);

                    usernameInForgot.clear();
                    ForgotPasswordAnswer.clear();

                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Security Question or Answer!");
                    alert.showAndWait();
                    return;
                }
                

            }catch(SQLException e){
                e.printStackTrace();
            }
        }

    }

    public void changePassword(){
        if(newPassword.getText().isEmpty() || confirmPassword.getText().isEmpty()){
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the fields");
            alert.showAndWait();
            return;
        }else{
            if(newPassword.getText().equals(confirmPassword.getText())){
                try(Connection con = DatabaseConection.gC()){
                    if(con == null){
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Database Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Cannot connect to database!");
                        alert.showAndWait();
                        return;
                    }
                    if(DatabaseForUser.updatePassword(con, usernameInForgot.getText(), newPassword.getText())){
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Success Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Password has been successfully changed!");
                        alert.showAndWait();

                        newPassword.clear();
                        confirmPassword.clear();
                        
                        questionForm1.setVisible(false);
                        loginForm.setVisible(true);
                    }else{
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Failed to update password!");
                        alert.showAndWait();
                    }
                    


                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Password does not match!");
                alert.showAndWait();
            }

        }
    }

    public void switchForgotPassword(){
        forgotPasswordList();
        loginForm.setVisible(false);
        questionForm.setVisible(true);
    }
    
    public void switchBack(){
        newPassword.clear();
        confirmPassword.clear();

        usernameInForgot.clear();
        ForgotPasswordAnswer.clear();

        questionForm.setVisible(false);
        questionForm1.setVisible(false);
        loginForm.setVisible(true);
    }

@FXML
    public void loginBotton() {
        if (textFieldUsername.getText().isEmpty() || passwordfield.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Username or Password is empty!!");
            alert.showAndWait();
            return;
        }

        try {
            con = DatabaseConection.gC();
            if (con == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setHeaderText(null);
                alert.setContentText("Cannot connect to database!");
                alert.showAndWait();
                return;
            }

            if (DatabaseForUser.checkLogin(con, textFieldUsername.getText(),passwordfield.getText())) {
                if(DatabaseForUser.checkEmployeeType(con, textFieldUsername.getText())){
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Login success!!");
                alert.showAndWait();

                Parent root = FXMLLoader.load(getClass().getResource("ControllerAdmin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Management System");
                stage.setMinHeight(800);
                stage.setMinWidth(1280);
                stage.setScene(scene);
                stage.show();
                
                Stage currenStage = (Stage)loginBotton.getScene().getWindow();
                currenStage.close();
                }else{
                    username = textFieldUsername.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login success!!");
                    alert.showAndWait();
    
                    Parent root = FXMLLoader.load(getClass().getResource("CashierLogin.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setTitle("Menu");
                    stage.setMinHeight(800);
                    stage.setMinWidth(1280);
                    stage.setScene(scene);
                    stage.show();

                    Stage currenStage = (Stage)loginBotton.getScene().getWindow();
                    currenStage.close();
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Login incorrect!!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("System Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred: " + e.getMessage());
            alert.showAndWait();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void switchForm(ActionEvent event) {

        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == createNewAccount) {
            slider.setNode(createForm);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                buttonAlready.setVisible(true);
                createNewAccount.setVisible(false);

                questionForm.setVisible(false);
                loginForm.setVisible(true);
                questionForm1.setVisible(false);
                
                textFieldUsername.clear();
                passwordfield.clear();

                regquestionList();
            });

            slider.play();
        } else if (event.getSource() == buttonAlready) {
            slider.setNode(createForm);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                buttonAlready.setVisible(false);
                createNewAccount.setVisible(true);

                clearRegistrationForm();

                questionForm.setVisible(false);
                loginForm.setVisible(true);
                questionForm1.setVisible(false);
            });

            slider.play();
        }
    }
    @Override
    public void initialize(java.net.URL arg0, java.util.ResourceBundle arg1) {
    }
}