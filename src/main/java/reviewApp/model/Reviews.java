package reviewApp.model;

import java.sql.Timestamp;

public class Reviews {

	protected int reviewId;
    protected Timestamp created;
    protected String content;
    protected double rating;
    protected String userName;
    protected Integer restaurantId;
    
	public Reviews(int reviewId, Timestamp created, String content, double rating, String userName,
			Integer restaurantId) {
		this.reviewId = reviewId;
		this.created = created;
		this.content = content;
		this.rating = rating;
		this.userName = userName;
		this.restaurantId = restaurantId;
	}
    // Constructor without reviewId and created (useful for creating a new review)
	public Reviews(String content, double rating, String userName, Integer restaurantId) {
        this.content = content;
        this.rating = rating;
        this.userName = userName;
        this.restaurantId = restaurantId;
	  }
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
}
