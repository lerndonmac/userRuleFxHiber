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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.impl.RuleDAOimpl;
import ru.sapteh.DAO.impl.UserDAOimpl;
import ru.sapteh.DAO.impl.UserRuleDAOimpl;
import ru.sapteh.model.Rule;
import ru.sapteh.model.User;
import ru.sapteh.model.UserRule;


import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MainController {
    public MenuItem MenuRefrash;
    private SessionFactory factory;

    //userRule
    public TableView<UserRule> tableUserRule;
    public TableColumn<UserRule,Integer> columnUserRuleId;
    public TableColumn<UserRule,String> columnUserRuleUser;
    public TableColumn<UserRule,String> columnUserRuleRule;
    public Button userRuleUpdateButton;
    public Button userRuleDeleteButton;
    public TextField userRuleId;
    public TextField userRuleUserFirstName;
    public TextField userRuleUserLastName;
    public TextField userRuleRuleName;
    public TextField userRuleRegDate;
    private UserRule userRuleForUserRule;
    public Button userRuleCreateButton;
ObservableList<UserRule> userRulesForUserRulesObservable = FXCollections.observableArrayList();

    //rules
    public TableView<Rule> tableRules;
    public TableColumn<Rule,Integer> columnRulesId;
    public TableColumn<Rule,String> columnRulesName;
    public ComboBox<User> ruleUsersCombo;
    public TextField RuleIdText;
    public TextField ruleNameText;
    public Button createRuleButton;
    public Button updateRuleButton;
    public Button deleteRuleButton;
    private final ObservableList<Rule> rulesForRulesObservableList = FXCollections.observableArrayList();
    private final ObservableList<User> usersForRulesObservableList = FXCollections.observableArrayList();

  //user
    private User userOfUser;
    public TableView<User> tableUsersView;
    public TableColumn<User,String> userFirstNameColumn;
    public TableColumn<User,String> userLastNameColumn;
    public Button userDeleteButton;
    public Button userUpdateButton;
    public Button UserCreateButton;
    public ComboBox<Date> regDateBox;
    public ComboBox<Rule> rulesBox;
    public Label idText;
    public Label firstNameText;
    public Label lastNameText;
    private final ObservableList<Date> dateObservableList = FXCollections.observableArrayList();
    private final ObservableList<Rule> ruleForUserObservableList = FXCollections.observableArrayList();
    private final ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        //window
        {
            MenuRefrash.setOnAction(actionEvent -> {
                usersForRulesObservableList.clear();
                ruleForUserObservableList.clear();
                userObservableList.clear();
                rulesForRulesObservableList.clear();
                usersForRulesObservableList.clear();
                userRulesForUserRulesObservable.clear();
                initialize();
            });
        }
        //userRule
        {
            initDateBaseUserRule();
            userRuleDeleteButton.setOnAction(actionEvent -> {
                factory = new Configuration().configure().buildSessionFactory();
                DAO<UserRule, Integer> DAOUserRule = new UserRuleDAOimpl(factory);
                DAOUserRule.delete(userRuleForUserRule);
            });
            userRuleCreateButton.setOnAction(ActionEvent -> {
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
            columnUserRuleId.setCellValueFactory(new PropertyValueFactory<UserRule, Integer>("id"));
            columnUserRuleUser.setCellValueFactory(new PropertyValueFactory<UserRule, String>("userId"));
            columnUserRuleRule.setCellValueFactory(new PropertyValueFactory<UserRule, String>("ruleId"));
            tableUserRule.getSelectionModel().selectedItemProperty().addListener(((observable, oldUserRule, UserRule) -> selectedUserRule(UserRule)));
            tableUserRule.setItems(userRulesForUserRulesObservable);
        }
        //user
        {
            initDataBaseUser();
            UserCreateButton.setOnAction(actionEvent -> {
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
            userUpdateButton.setOnAction(actionEvent -> {

            });
            userDeleteButton.setOnAction(actionEvent -> {
                factory = new Configuration().configure().buildSessionFactory();
                DAO<User, Integer> userDAO = new UserDAOimpl(factory);
                assert userOfUser != null;
                userDAO.delete(userOfUser);
            });
            tableUsersView.getSelectionModel().selectedItemProperty().addListener((observable, oldUser, user) -> selectedUser(user));
            userFirstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
            userLastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
            tableUsersView.setItems(userObservableList);
            regDateBox.setItems(dateObservableList);
        }
        //rules
        {
            initDateBaseRules();
            columnRulesId.setCellValueFactory(new PropertyValueFactory<Rule, Integer>("id"));
            columnRulesName.setCellValueFactory(new PropertyValueFactory<Rule, String>("ruleName"));
            tableRules.getSelectionModel().selectedItemProperty().addListener((observable, oldRule, rule) -> selectedRule(rule));
            tableRules.setItems(rulesForRulesObservableList);
        }
    }
//userRules
    public void initDateBaseUserRule(){
        factory = new Configuration().configure().buildSessionFactory();
        DAO<UserRule, Integer> DAOUserRules = new UserRuleDAOimpl(factory);
        List<UserRule> userRules = DAOUserRules.findByAll();
        userRulesForUserRulesObservable.addAll(userRules);
        factory.close();
    }
    public void selectedUserRule(UserRule userRule){
        userRuleForUserRule = userRule;
        System.out.println(userRule.toString());
        userRuleId.setText(String.valueOf(userRule.getId()));
        userRuleRuleName.setText(userRule.getRuleId().getRuleName());
        userRuleUserFirstName.setText(userRule.getUserId().getFirstName());
        userRuleUserLastName.setText(userRule.getUserId().getLastName());
        userRuleRegDate.setText(String.format("%Td/%Tm/%Ty",userRule.getRegistrationDate(),userRule.getRegistrationDate(),userRule.getRegistrationDate()));

    }
//Rules
    public void selectedRule(Rule rule){
        factory =  new Configuration().configure().buildSessionFactory();
        System.out.println(rule);
        RuleIdText.setText(String.valueOf(rule.getId()));
        ruleNameText.setText(rule.getRuleName());
        usersForRulesObservableList.clear();
        UserRuleDAOimpl DAOUserRule = new UserRuleDAOimpl(factory);
        usersForRulesObservableList.addAll(DAOUserRule.getUserListByRule(rule));
        ruleUsersCombo.setItems(usersForRulesObservableList);
    }
    public void initDateBaseRules(){
        factory =  new Configuration().configure().buildSessionFactory();
        DAO<Rule, Integer> DAORules = new RuleDAOimpl(factory);
        List<Rule> rules = DAORules.findByAll();
        rulesForRulesObservableList.addAll(rules);
        factory.close();
    }
//Users
    public void selectedUser(User user) {
        userOfUser = user;
        System.out.println(user.toString());
        idText.setText(String.valueOf(user.getId()));
        firstNameText.setText(user.getFirstName());
        lastNameText.setText(user.getLastName());
        dateObservableList.clear();
        ruleForUserObservableList.clear();
        for (UserRule userRule:user.getUserRules()){
            dateObservableList.add(userRule.getRegistrationDate());
            ruleForUserObservableList.add(userRule.getRuleId());
        }
        regDateBox.setItems(dateObservableList);
        rulesBox.setItems(ruleForUserObservableList);
    }
    public void initDataBaseUser(){
        factory =  new Configuration().configure().buildSessionFactory();
        DAO<User, Integer> DAOUser = new UserDAOimpl(factory);
        List<User> users = DAOUser.findByAll();
        userObservableList.addAll(users);
        factory.close();
    }
}
