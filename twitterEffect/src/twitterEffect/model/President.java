/**
 * 
 */
package twitterEffect.model;

/**
 * @author elaineparr
 *
 */
public class President extends Person{
	protected Party partyAffiliation;
	
	public enum Party {
		REPUBLICAN, DEMOCRAT, INDEPENDENT
	}

	/**
	 * @param personName
	 * @param occupation
	 * @param partyAffiliation
	 */
	public President(String personName, Party partyAffiliation) {
		super(personName, "President");
		this.partyAffiliation = partyAffiliation;
	}
	
	public President(String personName) {
		super(personName);
	}

	/**
	 * @return the partyAffiliation
	 */
	public Party getPartyAffiliation() {
		return partyAffiliation;
	}

	/**
	 * @param partyAffiliation the partyAffiliation to set
	 */
	public void setPartyAffiliation(Party partyAffiliation) {
		this.partyAffiliation = partyAffiliation;
	}
	

}
