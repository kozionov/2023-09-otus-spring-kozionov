package ru.otus.hw.model.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authors")
public class AuthorDoc {

    @Id
    private String id;
    private String firstName;
    private String lastName;

    public AuthorDoc(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
