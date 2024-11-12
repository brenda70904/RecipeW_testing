package reviewApp.tools;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import reviewApp.dal.*;
import reviewApp.model.*;

public class Inserter {
	public static void main(String[] args)throws SQLException{
		
		 // Initialize DAO instances
        UsersDao usersDao = UsersDao.getInstance();
        CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
        CompaniesDao companiesDao = CompaniesDao.getInstance();
        RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
        ReviewsDao reviewsDao = ReviewsDao.getInstance();
        RecommendationsDao recommendationsDao = RecommendationsDao.getInstance();
        SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
        ReservationsDao reservationsDao = ReservationsDao.getInstance();
        TakeOutRestaurantsDao takeOutRestaurantsDao = TakeOutRestaurantsDao.getInstance();


        
        // ========== Users ==========
        // Create User 1
        Users user1 = new Users("john_doe", "password123", "John", "Doe", "john@example.com", "1234567890");
        user1 = usersDao.create(user1);
        System.out.println("Created User: " + user1.getUserName());
        
        Users user2 = new Users("jane_smith", "securePass", "Jane", "Smith", "jane@example.com", "0987654321");
        user2 = usersDao.create(user2);
        System.out.println("Created User: " + user2.getUserName());
        
        Users user3 = new Users("alice_wonder", "alicePass", "Alice", "Wonderland", "alice@example.com", "5678901234");
        user3 = usersDao.create(user3);
        System.out.println("Created User: " + user3.getUserName());
        
        // First Read - Retrieve user by username "john_doe"
        Users retrievedUser1 = usersDao.getUserByUserName("john_doe");
        if (retrievedUser1 != null) {
            System.out.println("Retrieved User: " + retrievedUser1.getUserName() + ", Email: " + retrievedUser1.getEmail());
        } else {
            System.out.println("User 'john_doe' not found.");
        }
        
        // Second Read - Retrieve user by username "alice_wonder"
        Users retrievedUser2 = usersDao.getUserByUserName("alice_wonder");
        if (retrievedUser2 != null) {
            System.out.println("Retrieved User: " + retrievedUser2.getUserName() + ", Email: " + retrievedUser2.getEmail());
        } else {
            System.out.println("User 'alice_wonder' not found.");
        }

      
        // DELETE operation - Delete "john_doe"
        System.out.println("\nDELETE operation:");
        usersDao.delete(user1);
        System.out.println("Deleted User 'john_doe'");

        // Verify deletion by attempting to retrieve "john_doe" again
        Users deletedUserCheck = usersDao.getUserByUserName("john_doe");
        if (deletedUserCheck == null) {
            System.out.println("User 'john_doe' has been deleted successfully.");
        } else {
            System.out.println("User 'john_doe' was not deleted.");
        }
        
     // ========== CreditCards ==========
 
     // Create a credit card for Jane Smith
        java.sql.Date expirationDate1 = new java.sql.Date(System.currentTimeMillis() + 31536000000L); // One year from now
        CreditCards card1 = new CreditCards(4111111111111111L, expirationDate1, "jane_smith");
        card1 = creditCardsDao.create(card1);
        System.out.println("Created Credit Card: " + card1.getCardNumber() + " for user: " + card1.getUserName());

        // Create another credit card for Jane Smith
        java.sql.Date expirationDate2 = new java.sql.Date(System.currentTimeMillis() + 63072000000L); // Two years from now
        CreditCards card2 = new CreditCards(4222222222222222L, expirationDate2, "jane_smith");
        card2 = creditCardsDao.create(card2);
        System.out.println("Created Credit Card: " + card2.getCardNumber() + " for user: " + card2.getUserName());

        // Create a credit card for Alice
        java.sql.Date expirationDate3 = new java.sql.Date(System.currentTimeMillis() + 31536000000L); // One year from now
        CreditCards card3 = new CreditCards(4333333333333333L, expirationDate3, "alice_wonder");
        card3 = creditCardsDao.create(card3);
        System.out.println("Created Credit Card: " + card3.getCardNumber() + " for user: " + card3.getUserName());

        // Read individual credit card by card number
        System.out.println("\nREAD operations:");
        CreditCards retrievedCard = creditCardsDao.getCreditCardByCardNumber(4111111111111111L);
        if (retrievedCard != null) {
            System.out.println("Retrieved Credit Card: " + retrievedCard.getCardNumber() + 
                              ", Expiration: " + retrievedCard.getExpiration());
        } else {
            System.out.println("Credit Card not found.");
        }

        // Read all credit cards for a user
        List<CreditCards> janesCards = creditCardsDao.getCreditCardsByUserName("jane_smith");
        System.out.println("\nJane's credit cards:");
        for (CreditCards card : janesCards) {
            System.out.println("Card Number: " + card.getCardNumber() + 
                              ", Expiration: " + card.getExpiration());
        }

        // Update expiration date for card1
        System.out.println("\nUPDATE operation:");
        java.sql.Date newExpirationDate = new java.sql.Date(System.currentTimeMillis() + 94608000000L); // Three years from now
        CreditCards updatedCard = creditCardsDao.updateExpiration(card1, newExpirationDate);
        if (updatedCard != null) {
            System.out.println("Updated expiration date for card: " + updatedCard.getCardNumber() + 
                              " to: " + updatedCard.getExpiration());
        }

        // Delete card2
        System.out.println("\nDELETE operation:");
        CreditCards deletedCard = creditCardsDao.delete(card2);
        if (deletedCard == null) {
            System.out.println("Successfully deleted card: " + card2.getCardNumber());
        } else {
            System.out.println("Failed to delete card: " + card2.getCardNumber());
        }

        // Verify deletion by attempting to retrieve the deleted card
        CreditCards deletedCardCheck = creditCardsDao.getCreditCardByCardNumber(card2.getCardNumber());
        if (deletedCardCheck == null) {
            System.out.println("Verified: Card " + card2.getCardNumber() + " has been deleted.");
        } else {
            System.out.println("Card " + card2.getCardNumber() + " still exists in the database.");
        }
        
        
     // ========== Companies ==========
        
        System.out.println("\nCreating companies:");
        Companies company1 = new Companies("Tasty Bites Corp", "A family restaurant corporation focused on authentic cuisine");
        company1 = companiesDao.create(company1);
        System.out.println("Created Company: " + company1.getCompanyName());

        Companies company2 = new Companies("Global Eats Inc", "International restaurant chain specializing in diverse cuisines");
        company2 = companiesDao.create(company2);
        System.out.println("Created Company: " + company2.getCompanyName());

        System.out.println("\nReading company information:");
        Companies retrievedCompany = companiesDao.getCompanyByCompanyName("Tasty Bites Corp");
        if (retrievedCompany != null) {
            System.out.println("Retrieved Company: " + retrievedCompany.getCompanyName() + 
                              "\nAbout: " + retrievedCompany.getAbout());
        }
    
        System.out.println("\nUpdating company information:");
        String newAbout = "A family-owned restaurant corporation celebrating authentic cuisine since 2024";
        Companies updatedCompany = companiesDao.updateAbout(company1, newAbout);
        if (updatedCompany != null) {
            System.out.println("Updated Company: " + updatedCompany.getCompanyName() + 
                              "\nNew About: " + updatedCompany.getAbout());
        }

        // ========== Restaurants ==========
        System.out.println("\nCreating restaurants:");

        // Create restaurant for Tasty Bites Corp
        Restaurants restaurant1 = new Restaurants(
            "Asian Fusion Kitchen",
            "Modern Asian fusion cuisine",
            "Sushi, Dim Sum, Noodles",
            "Mon-Sun 11:00-22:00",
            true,
            Restaurants.CuisineType.ASIAN,
            "123 Main St",
            "Suite 101",
            "Seattle",
            "WA",
            98101,
            "Tasty Bites Corp"
        );
        restaurant1 = restaurantsDao.create(restaurant1);
        System.out.println("Created Restaurant: " + restaurant1.getName());

        // Create restaurant for Global Eats Inc
        Restaurants restaurant2 = new Restaurants(
            "Mediterranean Delight",
            "Authentic European Mediterranean cuisine",
            "Pasta, Seafood, Mezze",
            "Mon-Sat 12:00-23:00",
            true,
            Restaurants.CuisineType.EUROPEAN,
            "456 Pike St",
            "",
            "Seattle",
            "WA",
            98102,
            "Global Eats Inc"
        );
        restaurant2 = restaurantsDao.create(restaurant2);
        System.out.println("Created Restaurant: " + restaurant2.getName());
        
        Restaurants restaurant3 = new Restaurants(
        		"Sushi To Go", "Quick and delicious sushi takeout", "Sushi menu",
                "10:00 AM - 9:00 PM", true, Restaurants.CuisineType.ASIAN,
                "123 Ocean Drive", null, "Seattle", "WA", 98101, "Tasty Bites Corp"
            );
            restaurant3 = restaurantsDao.create(restaurant3);
            System.out.println("Created Restaurant: " + restaurant1.getName());

        // read
        System.out.println("\nReading restaurant information:");

        // Read by RestaurantId
        Restaurants retrievedRestaurant = restaurantsDao.getRestaurantById(restaurant1.getRestaurantId());
        if (retrievedRestaurant != null) {
            System.out.println("Retrieved Restaurant by ID: " + retrievedRestaurant.getName() + 
                              "\nCuisine: " + retrievedRestaurant.getCuisineType());
        }

        // Read by Cuisine Type
        System.out.println("\nRetrieving all Asian restaurants:");
        List<Restaurants> asianRestaurants = restaurantsDao.getRestaurantsByCuisine(Restaurants.CuisineType.ASIAN);
        for (Restaurants restaurant : asianRestaurants) {
            System.out.println("Found Restaurant: " + restaurant.getName() + 
                              " (" + restaurant.getCuisineType() + ")");
        }

        // Read by Company Name
        System.out.println("\nRetrieving all restaurants for Tasty Bites Corp:");
        List<Restaurants> companyRestaurants = restaurantsDao.getRestaurantsByCompanyName("Tasty Bites Corp");
        for (Restaurants restaurant : companyRestaurants) {
            System.out.println("Found Restaurant: " + restaurant.getName() + 
                              " at " + restaurant.getStreet1() + ", " + restaurant.getCity());
        }

        
        // ========== Reviews ==========
        // Create a new review for a restaurant by user "jane_smith"
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Reviews review1 = new Reviews("Great food and ambiance!", 4.5, "jane_smith", 1); // Assuming restaurantId 1 exists
        review1 = reviewsDao.create(review1);
        System.out.println("Created Review ID: " + review1.getReviewId() + ", Content: " + review1.getContent());

        // Create another review for a different restaurant by user "alice_wonder"
        Reviews review2 = new Reviews("Loved the sushi, highly recommend!", 5.0, "alice_wonder", 2); // Assuming restaurantId 2 exists
        review2 = reviewsDao.create(review2);
        System.out.println("Created Review ID: " + review2.getReviewId() + ", Content: " + review2.getContent());
        
        // Retrieve a review by its ID
        Reviews retrievedReview = reviewsDao.getReviewById(review1.getReviewId());
        if (retrievedReview != null) {
            System.out.println("Retrieved Review ID: " + retrievedReview.getReviewId() + 
                               ", Content: " + retrievedReview.getContent() + 
                               ", Rating: " + retrievedReview.getRating());
        } else {
            System.out.println("Review not found.");
        }

        // Retrieve reviews by user "jane_smith"
        List<Reviews> janesReviews = reviewsDao.getReviewsByUserName("jane_smith");
        System.out.println("\nReviews by jane_smith:");
        for (Reviews review : janesReviews) {
            System.out.println("Review ID: " + review.getReviewId() + ", Content: " + review.getContent() + 
                               ", Rating: " + review.getRating());
        }

        // Retrieve reviews by restaurant ID 1
        List<Reviews> restaurantReviews = reviewsDao.getReviewsByRestaurantId(1);
        System.out.println("\nReviews for Restaurant ID 1:");
        for (Reviews review : restaurantReviews) {
            System.out.println("Review ID: " + review.getReviewId() + ", Content: " + review.getContent() + 
                               ", Rating: " + review.getRating() + ", User: " + review.getUserName());
        }
        
     // ========== Recommendations ==========
        System.out.println("\nCreating recommendations:");
        Recommendations recommendation1 = new Recommendations("jane_smith", 1);
        recommendation1 = recommendationsDao.create(recommendation1);
        System.out.println("Created Recommendation ID: " + recommendation1.getRecommendationId() + ", User: " + recommendation1.getUserName());

        Recommendations recommendation2 = new Recommendations("alice_wonder", 2);
        recommendation2 = recommendationsDao.create(recommendation2);
        System.out.println("Created Recommendation ID: " + recommendation2.getRecommendationId() + ", User: " + recommendation2.getUserName());

        System.out.println("\nReading recommendations:");
        Recommendations retrievedRecommendation = recommendationsDao.getRecommendationById(recommendation1.getRecommendationId());
        if (retrievedRecommendation != null) {
            System.out.println("Retrieved Recommendation ID: " + retrievedRecommendation.getRecommendationId() +
                               ", User: " + retrievedRecommendation.getUserName());
        } else {
            System.out.println("Recommendation not found.");
        }

        List<Recommendations> userRecommendations = recommendationsDao.getRecommendationsByUserName("jane_smith");
        System.out.println("\nRecommendations by jane_smith:");
        for (Recommendations recommendation : userRecommendations) {
            System.out.println("Recommendation ID: " + recommendation.getRecommendationId() + ", Restaurant ID: " + recommendation.getRestaurantId());
        }

     // ========== SitDownRestaurants ===========
        SitDownRestaurants sitDownRestaurant1 = new SitDownRestaurants(1,"Asian Fusion Kitchen",
                "Modern Asian fusion cuisine",
                "Sushi, Dim Sum, Noodles",
                "Mon-Sun 11:00-22:00",
                true,
                Restaurants.CuisineType.ASIAN,
                "123 Main St",
                "Suite 101",
                "Seattle",
                "WA",
                98101,
                "Tasty Bites Corp",
                20);
        //read
        sitDownRestaurant1 = sitDownRestaurantsDao.create(sitDownRestaurant1);
        System.out.println("Created SitDownRestaurant: " + sitDownRestaurant1.getName() + " with capacity: " + sitDownRestaurant1.getCapacity());
     
		System.out.println("\nReading SitDownRestaurant by ID:");
		SitDownRestaurants retrievedSitDownRestaurantById = sitDownRestaurantsDao.getSitDownRestaurantById(sitDownRestaurant1.getRestaurantId());
		if (retrievedSitDownRestaurantById != null) {
		    System.out.println("Retrieved SitDownRestaurant: " + retrievedSitDownRestaurantById.getName() + 
		                   ", Capacity: " + retrievedSitDownRestaurantById.getCapacity());
		} else {
		     System.out.println("SitDownRestaurant not found.");
		}

		System.out.println("\nReading SitDownRestaurants by Company Name 'Tasty Bites Corp':");
		List<SitDownRestaurants> sitDownRestaurantsByCompanyName = sitDownRestaurantsDao.getSitDownRestaurantsByCompanyName("Tasty Bites Corp");
		for (SitDownRestaurants restaurant : sitDownRestaurantsByCompanyName) {
		    System.out.println("Found SitDownRestaurant: " + restaurant.getName() + ", Capacity: " + restaurant.getCapacity());
		}

		System.out.println("\nUpdating SitDownRestaurant Capacity:");
		SitDownRestaurants updatedSitDownRestaurant = retrievedSitDownRestaurantById;
		if (updatedSitDownRestaurant != null) {
		    updatedSitDownRestaurant.setCapacity(30); // New capacity
		System.out.println("Updated SitDownRestaurant: " + updatedSitDownRestaurant.getName() + 
		                  ", New Capacity: " + updatedSitDownRestaurant.getCapacity());
		} else {
		    System.out.println("SitDownRestaurant for update not found.");
		}
	
		

     // ========== Reservations ==========
        System.out.println("\nCreating reservations:");
        Timestamp start = Timestamp.valueOf("2024-11-01 18:00:00");
        Timestamp end = Timestamp.valueOf("2024-11-01 20:00:00");
        Reservations reservation1 = new Reservations(0, start, end, 4, "jane_smith", 1);
        reservation1 = reservationsDao.create(reservation1);
        System.out.println("Created Reservation ID: " + reservation1.getReservationId());

        Reservations reservation2 = new Reservations(1, start, end, 2, "alice_wonder", 1);
        reservation2 = reservationsDao.create(reservation2);
        System.out.println("Created Reservation ID: " + reservation2.getReservationId());

        System.out.println("\nReading reservations:");
        Reservations retrievedReservation = reservationsDao.getReservationById(reservation1.getReservationId());
        if (retrievedReservation != null) {
            System.out.println("Retrieved Reservation ID: " + retrievedReservation.getReservationId() +
                               ", User: " + retrievedReservation.getUserName() +
                               ", Start Time: " + retrievedReservation.getStart() +
                               ", Size: " + retrievedReservation.getSize());
        } else {
            System.out.println("Reservation not found.");
        }

        List<Reservations> userReservations = reservationsDao.getReservationsByUserName("jane_smith");
        System.out.println("\nReservations by jane_smith:");
        for (Reservations reservation : userReservations) {
            System.out.println("Reservation ID: " + reservation.getReservationId() +
                               ", Start Time: " + reservation.getStart() +
                               ", Size: " + reservation.getSize());
        }
        
     // ========== DELETE Reservations ==========
        System.out.println("\nDeleting reservation:");
        reservationsDao.delete(reservation1);
        System.out.println("Deleted Reservation ID: " + reservation1.getReservationId());

        System.out.println("All operations completed.");
    }

	
}
