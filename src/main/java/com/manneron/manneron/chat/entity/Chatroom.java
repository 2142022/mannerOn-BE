package com.manneron.manneron.chat.entity;

import com.manneron.manneron.common.util.TimeStamped;
import com.manneron.manneron.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "chatrooms")
@Getter
@NoArgsConstructor
public class Chatroom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    @MapsId("id")
    private User user;

    @Column(nullable = false)
    @Lob
    private String title;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Chat> chatList = new ArrayList<>();

    public Chatroom(User user, String title, String category) {
        this.user = user;
        this.title = title;
        this.category = category;
    }
}
