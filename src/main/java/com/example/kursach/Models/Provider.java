package com.example.kursach.Models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "provider")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 1,max = 100,message = "Поле не должно быть пустым")
    private String nameprovider;
    @Size(min = 1,max = 16,message = "Поле не должно быть пустым")
    private String phone;
    @Size(min = 1,max = 100,message = "Поле не должно быть пустым")
    private String address;
    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER)
    private Collection<order> orders;


    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}


    public String getNameprovider(){return nameprovider;};
    public void setNameprovider(String nameprovider){this.nameprovider=nameprovider;}


    public String getPhone(){return phone;};
    public void setPhone(String phone){this.phone=phone;}

    public String getAddress(){return address;};
    public void setAddress(String address){this.address=address;}

    public Collection<order> getOrders(){return orders;};
    public void setOrders(Collection<order> address){this.orders=orders;}

    public Provider() { }

    public Provider(String nameprovider,String phone, String address) {
        this.nameprovider = nameprovider;
        this.phone = phone;
        this.nameprovider = address;
    }
}
