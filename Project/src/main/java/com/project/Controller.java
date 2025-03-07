package com.project;

<<<<<<< HEAD
=======
import java.sql.Connection;
import java.util.ArrayList;
>>>>>>> main

import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Controller {
<<<<<<< HEAD
    String userAdmin = "Shine";
    String passwordAdmin = "123";
    String userCashier = "Cashier";
    String passwordCashier = "abc";
=======
>>>>>>> main

    @FXML
    private Hyperlink HyperlinkForgotPassword;

    @FXML
    private TextField answer;

    @FXML
    private Button buttonAlready;

    @FXML
    private Button buttonLogin;

    @FXML
    private AnchorPane createForm;

    @FXML
    private Button createNewAccount;

    @FXML
    private AnchorPane loginForm;

    @FXML
    private PasswordField passwordfield;

    @FXML
    private ComboBox<?> question;

    @FXML
    private AnchorPane registerForm;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private TextField UseradminRegister;

    @FXML
    private PasswordField passWAdminRegister;

    @FXML
    private TextField registerUser;

    @FXML
    private Button signUpBotton;

    @FXML
    private TextField textFieldUsername;

    private String[] questionList = { "What is your favorite Color?", "What is your favorite Food?" };

    private Alert regAlert;

    private Connection con;

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

            }
            if (DatabaseForUser.checkUser(con, registerUser.getText())) {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Register Error");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Username already exists!");
                regAlert.show();

            }

            String dbUser = DatabaseForUser.getUser(con, 1);
            String dbPassword = DatabaseForUser.getPassword(con, 1);
            if (dbUser == null || dbPassword == null) {
                regAlert = new Alert(AlertType.ERROR);
                regAlert.setTitle("Register Error");
                regAlert.setHeaderText(null);
                regAlert.setContentText("Admin account not found!");
                regAlert.show();

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

    private Alert alert;

    // ฟังค์ชั่น loginBotton คือ กด login
    // แล้วจะเด้งข้อความตามโค้ดที่ว่างเปล่าหรือใส่โค้ดผิด ถ้าถูกก็ย้ายไปหน้า lobby
    // แต่ยังไม่ได้ทำ controllerlobby
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
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Login success!!");
                alert.showAndWait();
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
<<<<<<< HEAD
    
    //ฟังค์ชั่นนี้เป็นการ สไลด์ หน้าจอตอนกดปุ่ม create account แต่เดี๋ยวจะย้ายฟังค์ชั่นนี้ไปไว้ตอนหลัง login admin ให้ create พนักงานได้อย่างเดียว d
    public void switchForm(ActionEvent event){
        
=======

    public void switchForm(ActionEvent event) {

>>>>>>> main
        TranslateTransition slider = new TranslateTransition();

        if (event.getSource() == createNewAccount) {
            slider.setNode(createForm);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) -> {
                buttonAlready.setVisible(true);
                createNewAccount.setVisible(false);

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
            });

            slider.play();
        }
    }
}