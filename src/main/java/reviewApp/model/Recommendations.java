package reviewApp.model;

public class Recommendations {
	
	protected int recommendationId;
	protected String userName;
	protected int restaurantId;
	
	
	
	public Recommendations(int recommendationId, String userName, int restaurantId) {
		this.recommendationId = recommendationId;
		this.userName = userName;
		this.restaurantId = restaurantId;
	}
	public Recommendations(String userName, int restaurantId) {
		this.userName = userName;
		this.restaurantId = restaurantId;
	}
	
	public int getRecommendationId() {
		return recommendationId;
	}
	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	
}
