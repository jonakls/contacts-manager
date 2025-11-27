package io.github.jonakls.contactsmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class UserModel implements Model {

    private final int id;
    private String email;
    private String passwordHash;

    @Override
    public int getId() {
        return this.id;
    }
}
