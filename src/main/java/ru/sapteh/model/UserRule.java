package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter@Setter@NoArgsConstructor@RequiredArgsConstructor
public class UserRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "registration_date")
    private Date registrationDate;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;
    @ManyToOne
    @NonNull
    @JoinColumn(name = "rule_id", nullable = false)
    private Rule ruleId;

    @Override
    public String toString() {
        return "UserRule{" +
                "id=" + id +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
