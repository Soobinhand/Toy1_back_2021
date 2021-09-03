package com.lookie.toy1_back.tome.domain;

import com.lookie.toy1_back.tome.role.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})",message = "-를 생략하고 입력해주세요.")
    private String phone;
    
    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 2, max = 10, message = "아이디는 2자 이상 10자 이하로 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
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