package reviewApp.model;

public class FoodCartRestaurants extends Restaurants {
	protected boolean licensed;
	
	public FoodCartRestaurants(int restaurantId, String name, String description, String menu, String hours,
            boolean active, CuisineType cuisineType, String street1, String street2, 
            String city, String state, int zip, String companyName, boolean licensed) {
		super(restaurantId, name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip, companyName);
		this.licensed = licensed;
	}
	// Constructor without restaurantId for creation
    public FoodCartRestaurants(String name, String description, String menu, String hours, 
    		boolean active, CuisineType cuisineType, String street1, String street2, 
    		String city, String state,  int zip, String companyName, boolean licensed) {
        super(name, description, menu, hours, active, cuisineType, street1, street2, city, state, zip, companyName);
        this.licensed = licensed;
    }
	public boolean isLicensed() {
		return licensed;
	}
	public void setLicensed(boolean licensed) {
		this.licensed = licensed;
	}
    
}
