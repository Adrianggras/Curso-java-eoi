package eoi.leerJSon;

public class Player {

	private String id;
	private String name;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String countryOfBirth;
	private String nacionality;
	private String position;
	private String firstNumber;
	private String lastUpdated;
	
	public Player() {
		
	}

	public Player(String id, String name, String firstName, String lastName, String dateOfBirth, String countryOfBirth,
			String nacionality, String position, String firstNumber, String lastUpdated) {
		super();
		this.id = id;
		this.name = name;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.countryOfBirth = countryOfBirth;
		this.nacionality = nacionality;
		this.position = position;
		this.firstNumber = firstNumber;
		this.lastUpdated = lastUpdated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getNacionality() {
		return nacionality;
	}

	public void setNacionality(String nacionality) {
		this.nacionality = nacionality;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFirstNumber() {
		return firstNumber;
	}

	public void setFirstNumber(String firstNumber) {
		this.firstNumber = firstNumber;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", countryOfBirth=" + countryOfBirth + ", nacionality=" + nacionality
				+ ", position=" + position + ", firstNumber=" + firstNumber + ", lastUpdated=" + lastUpdated + "]";
	}
	
	
}
