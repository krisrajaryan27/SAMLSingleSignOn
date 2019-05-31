/**
 * 
 */
package com.talentPool.customReports.djhelper.manager;

import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.utils.Utils;
import com.talentPool.customReports.djhelper.utils.LayoutManagerUtil;

/**
 * Layout manager that is specific to TalentPool. 
 * Few features that are missing in Classic layout manager is implemented in this class.
 * 
 *   
 * @author PraveenK
 * @since  Feb 17, 2012
 */
public class TPCustomLayoutManager extends ClassicLayoutManager {

	/* (non-Javadoc)
	 * @see ar.com.fdvs.dj.core.layout.ClassicLayoutManager#generateTitleBand()
	 */
	protected void generateTitleBand() {
		super.generateTitleBand();
		try {
			if (!Utils.isBlankOrNull(getReport().getSubtitle()) &&
					LayoutManagerUtil.isJRExpression(getReport().getSubtitle())) {
				JRDesignBand band = (JRDesignBand) getDesign().getPageHeader();
				JRDesignTextField jrdesignTextFiled = null;
				jrdesignTextFiled = (JRDesignTextField)band.getElements()[band.getElements().length-1];
				JRDesignExpression jrDesignExpression = (JRDesignExpression)jrdesignTextFiled.getExpression();
				jrDesignExpression.setText(getReport().getSubtitle());
			}			
		} catch (ClassCastException cce) {
			TPLogger.getLogger().error(cce);
		}catch (Exception e) {
			TPLogger.getLogger().error("Could not ovveride subtitle change implementation due to improper design or due to update of dynamic japser design");
		}
	}

}
