package com.talentPool.desktop.saml.context;

import java.util.HashMap;
import java.util.Map;

public class CircleOfTrust {

	private static final String ts = "Thead Safe Access";

	private final Map<String, IdP> IDPs;

	private SP sp;

	private static CircleOfTrust circleOfTrust;

	private CircleOfTrust() {
		this.IDPs = new HashMap<>();
	}

	public static CircleOfTrust getInstance() {
		synchronized (ts) {
			if (circleOfTrust == null) {
				circleOfTrust = new CircleOfTrust();
			}

			return circleOfTrust;
		}
	}

	/**
	 * Gets IdP with the given entity ID.
	 *
	 * @param entityID
	 *            IdP ID.
	 * @return IdP.
	 */
	public IdP getIdP(final String entityID) {
		return entityID == null ? getIdP() : IDPs.get(entityID);
	}

	/**
	 * Gets random IdP.
	 *
	 * @return IdP.
	 */
	public IdP getIdP() {
		return IDPs.isEmpty() ? null : IDPs.values().iterator().next();
	}

	/**
	 * Adds IdP to the COT.
	 *
	 * @param entityID
	 *            IdP ID.
	 * @param idp
	 *            IdP.
	 * @return IdP.
	 */
	IdP addIdP(final String entityID, final IdP idp) {
		return IDPs.put(entityID, idp);
	}

	/**
	 * Gets local SP.
	 *
	 * @return SP.
	 */
	public SP getSp() {
		return sp;
	}

	/**
	 * Sets local SP.
	 *
	 * @param sp
	 *            SP.
	 */
	void setSp(SP sp) {
		this.sp = sp;
	}

}
