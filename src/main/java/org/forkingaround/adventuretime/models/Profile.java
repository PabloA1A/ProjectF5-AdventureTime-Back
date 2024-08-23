package org.forkingaround.adventuretime.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profile")
    private Long id;

    private String email;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

/*     @ManyToOne
    @JoinColumn(name = "country_id", nullable = true)
    private Country country; */

    public Profile() {
    }

   // public Profile(String email, String address, User user, Country country) {
    public Profile(String email, String address, User user) {
        this.email = email;
        this.address = address;
        this.user = user;
       // this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // public Country getCountry() {
    //     return country;
    // }

    // public void setCountry(Country country) {
    //     this.country = country;
    // }

}
