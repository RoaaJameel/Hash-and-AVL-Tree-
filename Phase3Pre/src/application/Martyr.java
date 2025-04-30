package application;

public class Martyr implements Comparable<Martyr> {
	private String fullName;
	private String dateOfDeath;
	private int age;
	private String district;
	private String location;
	private char gender;

	public Martyr() {
	}

	public Martyr(String fullName, String dateOfDeath, int age, String district, String location, char gender) {
		this.fullName = fullName;
		this.dateOfDeath = dateOfDeath;
		this.age = age;
		this.district = district;
		this.location = location;
		this.gender = gender;
	}

	// Getters and setters
	public String getFullName() {
		return fullName;
	}

	public void setfullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "\n\n\nMartyr " + "Name:    " + fullName + "\n" + "Date Of Death:    " + dateOfDeath +"\n Age:    " + age + "\n District:    "
				+ district +   "\n Location:    " + location +"\n Gender:    " + gender +"\n";
	}

	@Override
	public int compareTo(Martyr other) {
		int districtComparison = this.district.compareTo(other.district);
		if (districtComparison != 0) {
			return districtComparison;
		}
		return this.fullName.compareTo(other.fullName);
	}

}
