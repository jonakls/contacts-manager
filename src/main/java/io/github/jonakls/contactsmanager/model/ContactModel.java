package io.github.jonakls.contactsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactModel implements Model {

    private final int id;
    private UserModel user;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String company;
    private String notes;
    private boolean favorite;

    @Override
    public int getId() {
        return this.id;
    }
}
