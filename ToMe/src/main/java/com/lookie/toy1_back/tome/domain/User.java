package com.lookie.toy1_back.tome.domain;

import com.lookie.toy1_back.tome.role.UserRole;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Table(name = "tome_user")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String phone;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole userRole;

    @Builder
    public User(String name, String phone, String username, String password, UserRole userRole){
        this.name = name;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }



    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (!(o instanceof User)){
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.name, user.name) &&
                Objects.equals(this.phone, user.phone) &&
                Objects.equals(this.username, user.username) &&
                Objects.equals(this.password, user.password);

    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id, this.name, this.phone,
                this.username, this.password);
    }


}