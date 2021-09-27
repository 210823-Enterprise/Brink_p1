package com.revature.dummymodels;

import java.util.Objects;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.Id;

@Entity(tableName="test_table")
public class Test {

	@Id(columnName="test_id")
	private int id;
	
	@Column(columnName="movie_title", columnType="VARCHAR")
	private String testMovieTitle;
	
	@Column(columnName="genre", columnType = "VARCHAR")
	private String testGenre;
	
	@Column(columnName="test_int", columnType = "INTEGER")
	private int testInt;
	
	@Column(columnName="test_boolean", columnType = "BOOLEAN")
	private Boolean testBoolean;
	
	@Column(columnName="test_double", columnType = "DOUBLE PRECISION ")
	private double testDouble;
	
	
	public Test() {
		super();
	}

	public Test(String testMovieTitle, String testGenre, int testInt, Boolean testBoolean, double testDouble) {
		super();
		this.testMovieTitle = testMovieTitle;
		this.testGenre = testGenre;
		this.testInt = testInt;
		this.testBoolean = testBoolean;
		this.testDouble = testDouble;
	}

	public Test(int id, String testMovieTitle, String testGenre, int testInt, Boolean testBoolean, double testDouble) {
		super();
		this.id = id;
		this.testMovieTitle = testMovieTitle;
		this.testGenre = testGenre;
		this.testInt = testInt;
		this.testBoolean = testBoolean;
		this.testDouble = testDouble;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTestMovieTitle() {
		return testMovieTitle;
	}

	public void setTestMovieTitle(String testMovieTitle) {
		this.testMovieTitle = testMovieTitle;
	}

	public String getTestGenre() {
		return testGenre;
	}

	public void setTestGenre(String testGenre) {
		this.testGenre = testGenre;
	}

	public int getTestInt() {
		return testInt;
	}

	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}

	public Boolean getTestBoolean() {
		return testBoolean;
	}

	public void setTestBoolean(Boolean testBoolean) {
		this.testBoolean = testBoolean;
	}

	public double getTestDouble() {
		return testDouble;
	}

	public void setTestDouble(double testDouble) {
		this.testDouble = testDouble;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, testBoolean, testDouble, testGenre, testInt, testMovieTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Test other = (Test) obj;
		return id == other.id && Objects.equals(testBoolean, other.testBoolean)
				&& Double.doubleToLongBits(testDouble) == Double.doubleToLongBits(other.testDouble)
				&& Objects.equals(testGenre, other.testGenre) && testInt == other.testInt
				&& Objects.equals(testMovieTitle, other.testMovieTitle);
	}

	@Override
	public String toString() {
		return "Test [id=" + id + ", testMovieTitle=" + testMovieTitle + ", testGenre=" + testGenre + ", testInt="
				+ testInt + ", testBoolean=" + testBoolean + ", testDouble=" + testDouble + "]";
	}

	
	
}