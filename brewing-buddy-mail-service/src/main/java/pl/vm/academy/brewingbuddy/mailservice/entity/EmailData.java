package pl.vm.academy.brewingbuddy.mailservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Entity
@Setter
@Getter
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "email_id")
    private Integer emailId;

    @Email(message = "Sender must match email address!")
    private String sender;

    @Email(message = "Recipient must match email address!")
    private String recipient;

    private String text;
}