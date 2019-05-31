package com.talentPool.parser.regex;

public class Skill implements Comparable {
	int id;
	int numOccurrences;
	String name;

	public Skill(int id) {
		this.id = id;
		this.numOccurrences = 0;
	}

	public Skill(int id, String name, int numOccurrences) {
		this.id = id;
		this.numOccurrences = numOccurrences;
		this.name = name;
	}

	/**
	 * The sorting method is "inverted", i.e. s1 < s2 iff s1.occs > s2.occs. This inversion is done because sorted sets store elements in "ascending order" while we want the "most occurring elements at the beginning".
	 */

	public int compareTo(Object o) throws ClassCastException {
		if (!(o instanceof Skill))
			throw new ClassCastException();

		Skill other = (Skill) o;
		if(numOccurrences > other.numOccurrences){
			return -1;
		}else{
			return 1;
		}
		//return other.numOccurrences - numOccurrences;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Skill))
			return false;
		else
			return compareTo(o) == 0;
	}

	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the numOccurrences.
	 */
	public int getNumOccurrences() {
		return numOccurrences;
	}

	/**
	 * @param numOccurrences
	 *            The numOccurrences to set.
	 */
	public void setNumOccurrences(int numOccurrences) {
		this.numOccurrences = numOccurrences;
	}
}