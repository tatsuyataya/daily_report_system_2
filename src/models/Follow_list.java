package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
    @NamedQuery(
            name = "getFL",
            query = "SELECT f FROM Follow_list AS f WHERE f.follower = :fr AND f.followed = :fd"
            ),
    @NamedQuery(
    		name = "getMyFolloweds",
    		query = "SELECT f FROM Follow_list AS f WHERE f.follower = :fr"
    		),
    @NamedQuery(
    		name = "getFollowingCount",
    		query = "SELECT COUNT(f) FROM Follow_list AS f WHERE f.follower = :fr AND f.followed = :fd"
    		),
})
@Table(name = "followlist")
public class Follow_list {
    
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
	@Column(name = "follower", nullable = false)
    private int follower;

	@Column(name = "followed", nullable = false)
    private int followed;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getFollower() {
		return follower;
	}

	public void setFollower(int follower) {
		this.follower = follower;
	}

	public int getFollowed() {
		return followed;
	}

	public void setFollowed(int followed) {
		this.followed = followed;
	}

    
}
