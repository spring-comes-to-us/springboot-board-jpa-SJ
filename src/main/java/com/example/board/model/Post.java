package com.example.board.model;

import com.example.board.dto.PostDto;
import com.example.board.dto.PostUpdateDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 20)
    @Column(name = "title", length = 30)
    private String title;

    @NotBlank
    @Lob
    private String contents;

    @Column(name = "created_by")
    private String createdBy;

    private Post(User user, PostDto postDto) {
        this.user = user;
        this.title = postDto.title();
        this.contents = postDto.contents();
        this.createdBy = user.getName();
    }

    public static Post from(User user, PostDto postDto) {
        return new Post(user, postDto);
    }

    public Long update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.title();
        this.contents = postUpdateDto.contents();
        return this.id;
    }
}
