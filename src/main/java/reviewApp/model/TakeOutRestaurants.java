package reviewApp.model;

public class TakeOutRestaurants extends Restaurants{
	
	protected int maxWaitTime;
	
	public TakeOutRestaurants(int restaurantId, String name, String description, String menu, String hours,
            boolean active, CuisineType cuisineType, String street1, String street2, 
            String city, String state, int zip, String companyName, int maxWaitTime) {
		super(restaurantId, name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip, companyName);
		this.maxWaitTime = maxWaitTime;
	}
	// Constructor without restaurantId for creation
    public TakeOutRestaurants(String name, String description, String menu, String hours, 
    		boolean active, CuisineType cuisineType, String street1, String street2, 
    		String city, String state,  int zip, String companyName, int maxWaitTime) {
        super(name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip, companyName);
        this.maxWaitTime = maxWaitTime;
    }
	public int getMaxWaitTime() {
		return maxWaitTime;
	}
	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
}
