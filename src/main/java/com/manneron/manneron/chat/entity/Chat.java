package com.manneron.manneron.chat.entity;

import com.manneron.manneron.common.util.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "chats")
@Getter
@NoArgsConstructor
public class Chat extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    @MapsId("id")
    private Chatroom chatroom;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int feedback;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int copy;

    public Chat(Chatroom chatroom, String content, String role) {
        this.chatroom = chatroom;
        this.content = content;
        this.role = role;
    }
}
