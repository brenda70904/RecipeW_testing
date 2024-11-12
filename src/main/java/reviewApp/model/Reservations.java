package reviewApp.model;

import java.sql.Timestamp;

public class Reservations {
	protected int reservationId;
	protected Timestamp start;
	protected Timestamp end;
	protected int size;
	protected String userName;
	protected int restaurantId;
	
	public Reservations(int reservationId, Timestamp start, Timestamp end, int size, String userName,
			int restaurantId) {
		this.reservationId = reservationId;
		this.start = start;
		this.end = end;
		this.size = size;
		this.userName = userName;
		this.restaurantId = restaurantId;
	}
	public Reservations (Timestamp start, Timestamp end, int size, String userName,
			int restaurantId) {
		this.start = start;
		this.end = end;
		this.size = size;
		this.userName = userName;
		this.restaurantId = restaurantId;
	}
	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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
