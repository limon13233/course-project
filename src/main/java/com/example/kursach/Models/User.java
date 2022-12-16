package com.example.kursach.Models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Обязательное поле")
    @Size(min = 1, max = 50, message = "Колл-во символов 1-50")
    private String surname;
    @NotBlank(message = "Обязательное поле")
    @Size(min = 1, max = 50, message = "Поле должно содержать от 1 до 50 символов")
    private String name;

    private String middleName;
    @NotNull(message = "Обязательное поле")
    private int number_passport;
    @NotNull(message = "Обязательное поле")
    private int serial_passport;
    @NotNull(message = "Обязательное поле")
    private Date birthday;
    @Pattern(regexp = "^[+][0-9][(][0-9][0-9][0-9][)][0-9][0-9][0-9][-][0-9][0-9][-][0-9][0-9]$", message = "Телефон должна быть записана в формате 8(***)***-**-**")
    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9]{6,15}$",
            message = "имя пользователя должно быть длиной от 6 до 15 и не содержать специальных символов")
    private String username;
//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,30}$"
//    ,message = "Как минимум 1 заглавная буква, как минимум 1 строчная буква и как минимум 1 цифра. От 8 до 30 символов")
    private String password;
    private Boolean active;
    @ManyToOne(optional = true, cascade = CascadeType.DETACH)
    private Membership membership;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<IssueBook> issuebooks;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    @NotNull
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @NotNull
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    @NotNull
    public int getNumber_passport() { return number_passport; }
    public void setNumber_passport(int number_passport) { this.number_passport = number_passport; }

    @NotNull
    public int getSerial_passport() { return serial_passport; }
    public void setSerial_passport(int serial_passport) { this.serial_passport = serial_passport; }

    @NotNull
    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }


    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone=phone;}

    public  String getFIO(){ return surname +" "+ name+ " "+middleName;}

    public User() { }

    public User(String username, String password, Boolean active, Set<Role> roles, String surname, String name, String middleName, int number_passport, int serial_passport, Date birthday,String phone,Membership membership) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.number_passport = number_passport;
        this.serial_passport =serial_passport;
        this.phone=phone;
        this.birthday = birthday;
        this.membership = membership;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public List<IssueBook> getIssuebooks() {
        return issuebooks;
    }

    public void setIssuebooks(List<IssueBook> issuebooks) {
        this.issuebooks = issuebooks;
    }
}
