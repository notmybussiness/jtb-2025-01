package com.example.demo.user;

import com.example.demo.answer.Answer;
import com.example.demo.comment.Comment;
import com.example.demo.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Question> questionList;
    @OneToMany(mappedBy = "author")
    private List<Answer> answerList;
    @OneToMany(mappedBy = "author")
    private List<Comment> commentList;
    
}
