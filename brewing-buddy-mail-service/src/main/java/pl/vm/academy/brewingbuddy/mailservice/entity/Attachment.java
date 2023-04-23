package pl.vm.academy.brewingbuddy.mailservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "ATTACHMENT")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attachment_id")
    private Integer attachmentId;

    @Lob
    byte[] content;
    @ManyToOne
    @JoinColumn(name = "email_id", nullable = false)
    private EmailData emailData;
}