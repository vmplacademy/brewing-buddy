package pl.vm.academy.brewingbuddy.mailservice.business.core.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "t_email_data")
public class EmailDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Email(message = "Sender must match email address!")
    private String sender;

    @Email(message = "Recipient must match email address!")
    private String recipient;

    private String text;

    @OneToMany(mappedBy = "emailDataEntity")
    private Set<AttachmentEntity> attachmentEntities;
}