package io.github.jonakls.contactsmanager.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GroupModel implements Model {

    private final int id;
    private List<ContactModel> contacts;
    private String name;

    public GroupModel(final int id, final String name) {
        this.id = id;
        this.name = name;
        this.contacts = new ArrayList<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void addContact(final ContactModel contact) {
        this.contacts.add(contact);
    }

    public void removeContact(final ContactModel contact) {
        this.contacts.remove(contact);
    }
}
