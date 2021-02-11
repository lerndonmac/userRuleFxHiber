package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter@Getter@NoArgsConstructor@RequiredArgsConstructor
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "rule_name")
    @NonNull
    private String ruleName;
    @OneToMany(mappedBy = "ruleId",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<UserRule> userRules;

    @Override
    public String toString() {
        return ruleName;
    }
}
