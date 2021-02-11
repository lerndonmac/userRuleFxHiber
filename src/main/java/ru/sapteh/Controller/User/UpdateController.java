package ru.sapteh.Controller.User;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.DAO.impl.UserDAOimpl;
import ru.sapteh.model.User;

public class UpdateController {
    public User user;
    @FXML
    public TextField fNText;@FXML
    public Button updateButton;@FXML
    public TextField lNTex;

    @FXML
    public void initialize(){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        DAO<User,Integer> DAOUser = new UserDAOimpl(factory);
        fNText.setText(user.getLastName());
        lNTex.setText(user.getLastName());
        updateButton.setOnAction(actionEvent -> {
            user.setFirstName(fNText.getText());
            user.setLastName(lNTex.getText());
            assert !(user.getFirstName().equals(""));
            DAOUser.update(user);
            factory.close();
            updateButton.getScene().getWindow().hide();
        });

    }
}
