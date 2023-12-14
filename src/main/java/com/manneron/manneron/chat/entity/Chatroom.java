package com.manneron.manneron.chat.entity;

import com.manneron.manneron.common.util.TimeStamped;
import com.manneron.manneron.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "chatrooms")
@Getter
@NoArgsConstructor
public class Chatroom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String category;

    public Chatroom(User user, String title, String category) {
        this.user = user;
        this.title = title;
        this.category = category;
    }
}
