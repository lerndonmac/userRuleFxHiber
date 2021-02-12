package ru.sapteh.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.impl.RuleDAOimpl;
import ru.sapteh.DAO.impl.UserDAOimpl;
import ru.sapteh.DAO.impl.UserRuleDAOimpl;
import ru.sapteh.model.Rule;
import ru.sapteh.model.User;
import ru.sapteh.model.UserRule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class CreateUserRuleController {
    ObservableList<User> users = FXCollections.observableArrayList();
    ObservableList<Rule> rules = FXCollections.observableArrayList();
    @FXML
    public ComboBox<User> userCombo;@FXML
    public DatePicker regDatePicker;@FXML
    public ComboBox<Rule> ruleCombo;
    public Button createButton;

    @FXML
    public void initialize(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        DAO<User, Integer> DAOUser = new UserDAOimpl(factory);
        DAO<Rule, Integer> DAORule = new RuleDAOimpl(factory);
        DAO<UserRule,Integer> DAOUserRule = new UserRuleDAOimpl(factory);
        users.addAll(DAOUser.findByAll());
        rules.addAll(DAORule.findByAll());
        userCombo.setItems(users);
        ruleCombo.setItems(rules);
        createButton.setOnAction(ActionEvent->{
            String dateStr = regDatePicker.getValue().toString();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;

            try {
                date = df.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setTime(date.getTime()+10800000);
            UserRule userRule = new UserRule(date,
                    userCombo.getValue(),ruleCombo.getValue());
            assert date!=null;
            DAOUserRule.create(userRule);
//        factory.close();
        });

    }
}
