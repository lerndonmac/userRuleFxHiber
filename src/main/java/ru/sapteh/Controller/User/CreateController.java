package ru.sapteh.Controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.impl.UserDAOimpl;
import ru.sapteh.model.Rule;
import ru.sapteh.model.User;
import ru.sapteh.model.UserRule;

public class CreateController {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
@FXML
    public TextField fNText;@FXML
    public TextField lNText;@FXML
    public Button createButton;

    @FXML
    public void initialize(){
        createButton.setOnAction(actionEvent -> {
            DAO<User,Integer> DAOUser = new UserDAOimpl(factory);
            User user = new User();
            user.setFirstName(fNText.getText());
            user.setLastName(lNText.getText());
            if (!(user.getFirstName().equals(""))&&!(user.getLastName().equals("")))
            {DAOUser.create(user);}else {
                System.out.print("error");
            }
        });
    }
}
