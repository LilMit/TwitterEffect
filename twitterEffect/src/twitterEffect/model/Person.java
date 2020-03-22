/**
 * 
 */
package twitterEffect.model;

/**
 * @author elaineparr
 *
 */
public class Person {
	protected String personName;
	protected String occupation;
	/**
	 * @param personName
	 * @param occupation
	 */
	public Person(String personName, String occupation) {
		this.personName = personName;
		this.occupation = occupation;
	}
	/**
	 * @return the personName
	 */
	public String getPersonName() {
		return personName;
	}
	/**
	 * @param personName the personName to set
	 */
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	

}
