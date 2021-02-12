package ru.sapteh.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Getter@Setter@NoArgsConstructor@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Column(name = "last_name")
    private String lastName;
    @NonNull
    @Column(name = "first_name")
    private String firstName;
    @OneToMany(mappedBy = "userId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<UserRule> userRules;

    public Date getLastDate(){
        Iterator<UserRule> it = userRules.iterator();
        Date lastDate = new Date(0);
        for (int i = 0; i <getCount(); i++) {
            Date date = it.next().getRegistrationDate();
            if (lastDate.getTime() < date.getTime()) {
                lastDate = date;
            }
        }

        return lastDate;
    }
    @Transient
    private final int sizeRole = getCount();

    public Integer getCount(){
        if (userRules == null)userRules = new HashSet<>();
        return userRules.size();
    }

    @Override
    public String toString() {
        return  lastName+"  "+
                 firstName;
    }
}
