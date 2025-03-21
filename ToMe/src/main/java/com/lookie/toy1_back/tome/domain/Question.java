package com.lookie.toy1_back.tome.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Question extends BaseTime implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long q_num;

    @NotBlank(message = "질문을 입력해주세요.")
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList = new ArrayList<Answer>();
}