package ru.sapteh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.impl.UserDAOimpl;
import ru.sapteh.model.Rule;
import ru.sapteh.model.User;
import ru.sapteh.model.UserRule;


import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainController {
@FXML
    public Button userRuleCreateButton;
    private final ObservableList<User> userObservableList = FXCollections.observableArrayList();
    private final ObservableList<Date> dateObservableList = FXCollections.observableArrayList();
    private final ObservableList<Rule> ruleObservableList = FXCollections.observableArrayList();

  private SessionFactory factory;
  private User user;
  private Rule rule;
  private UserRule userRule;

  @FXML
    public TableView<User> tableUsersView;@FXML
    public TableColumn<User,String> userFirstNameColumn;@FXML
    public TableColumn<User,String> userLastNameColumn;@FXML
    public Button userDeleteButton;@FXML
    public Button userUpdateButton;@FXML
    public Button UserCreateButton;@FXML
    public ComboBox<Date> regDateBox;@FXML
    public ComboBox<Rule> rulesBox;@FXML
    public Label idText;@FXML
    public Label firstNameText;@FXML
    public Label lastNameText;



    @FXML
    public void initialize(){
        UserCreateButton.setOnAction(actionEvent->{
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/user/CreateWindow.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Create Window");
            assert root != null;
            primaryStage.setScene(new Scene(root));
            primaryStage.showAndWait();
        });
        userUpdateButton.setOnAction(actionEvent ->{

        } );

        userDeleteButton.setOnAction(actionEvent -> {
            factory =  new Configuration().configure().buildSessionFactory();
            initDataBase();
            DAO<User,Integer> userDAO = new UserDAOimpl(factory);
            assert user != null;
            userDAO.delete(user);
        });

        userRuleCreateButton.setOnAction(ActionEvent->{
            Stage primaryStage = new Stage();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ru.sapteh/view/CreateUserRule.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("Create Window");
            assert root != null;
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        });

        factory =  new Configuration().configure().buildSessionFactory();
        initDataBase();

        tableUsersView.getSelectionModel().selectedItemProperty().addListener
                ((observable,oldUser,user)-> selected(user));
        userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("firstName"));
        userLastNameColumn.setCellValueFactory(new PropertyValueFactory<User,String>("lastName"));
        tableUsersView.setItems(userObservableList);

        regDateBox.setItems(dateObservableList);
    }

    public void initDataBase(){
        DAO<User, Integer> DAOUser = new UserDAOimpl(factory);
        List<User> users = DAOUser.findByAll();
        userObservableList.addAll(users);
        factory.close();
    }

    public void selected(User user) {
        System.out.println(user.toString());
        idText.setText(String.valueOf(user.getId()));
        firstNameText.setText(user.getFirstName());
        lastNameText.setText(user.getLastName());
        dateObservableList.clear();
        ruleObservableList.clear();
        for (UserRule userRule:user.getUserRules()){
            dateObservableList.add(userRule.getRegistrationDate());
            ruleObservableList.add(userRule.getRuleId());
        }
        regDateBox.setItems(dateObservableList);
        rulesBox.setItems(ruleObservableList);
    }
}
