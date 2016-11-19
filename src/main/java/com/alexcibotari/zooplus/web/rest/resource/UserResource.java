package com.alexcibotari.zooplus.web.rest.resource;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.core.Relation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Relation(value = "user", collectionRelation = "users")
public class UserResource extends AbstractAuditingResource {

    @Size(min = 3, max = 50)
    @Email
    private String email;

    @NotEmpty
    @Size(min = 6, max = 50)
    private String password;

    private Boolean enabled = Boolean.TRUE;

    private Set<String> authorities = new HashSet<>();

    @NotNull
    @Past
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDay;

    @NotNull
    @Valid
    private ContactDetails contact = new ContactDetails();

    public UserResource() {
    }

    public UserResource(String email, Boolean enabled, Set<String> authorities) {
        this.email = email;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public ContactDetails getContact() {
        return contact;
    }

    public void setContact(ContactDetails contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "email='" + email + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }

    public class ContactDetails {
        @NotEmpty
        private String street;
        @NotEmpty
        private String zip;
        @NotEmpty
        private String city;
        @NotEmpty
        private String country;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
