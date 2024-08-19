package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(unique = true, nullable = false)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower;

	@ManyToOne
    @JoinColumn(name = "following_id")
    private Member following;
	
	public Follow() {
    }
	
	public Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }
}
