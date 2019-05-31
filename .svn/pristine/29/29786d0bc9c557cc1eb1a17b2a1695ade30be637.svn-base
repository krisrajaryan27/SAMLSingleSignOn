/**
 * 
 */
package com.talentPool.customReports.djhelper.manager;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.crosstabs.JRCellContents;
import net.sf.jasperreports.crosstabs.JRCrosstabCell;
import net.sf.jasperreports.crosstabs.JRCrosstabColumnGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabGroup;
import net.sf.jasperreports.crosstabs.JRCrosstabParameter;
import net.sf.jasperreports.crosstabs.design.JRDesignCellContents;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabBucket;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstabParameter;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.LineStyleEnum;
import net.sf.jasperreports.engine.type.StretchTypeEnum;
import ar.com.fdvs.dj.domain.DJCrosstab;
import ar.com.fdvs.dj.domain.DJCrosstabMeasure;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.util.LayoutUtils;

import com.talentPool.common.Logger.TPLogger;
import com.talentPool.common.properties.GlobalConstants;
import com.talentPool.customReports.djhelper.constants.DJHelperConstants;
import com.talentPool.customReports.djhelper.wrappers.DJComparatorWrapper;

/**
 * CrossTab Layout manager which overrides some of the functionality 
 * to provide some additional functionality that Custom Layout Manager does not provide for Cross Tab.
 * <br><br>Few things that are overrides are :
 * <li>Comparator Expression added for required column and row groups</li>
 * <li>Multiple Measure arranged in vertical order.</li>
 * @author PraveenK
 * @since  Dec 13, 2011
 */
public class CrossTabLayoutManager extends TPCustomLayoutManager {
	
	protected final String CROSSTAB_MEASURE_PARAM_START = "crosstab-measure__";
	protected final String CROSSTAB_MEASURE_PREFIX = "idx";
	
	private Map<String,String> parameters = null;
	
	public CrossTabLayoutManager(Map<String,String> parameters) {
		super();
		this.parameters=parameters;
	}
	
	
	/* (non-Javadoc)
	 * @see ar.com.fdvs.dj.core.layout.ClassicLayoutManager#layoutGroupCrosstabs(ar.com.fdvs.dj.domain.entities.DJGroup, net.sf.jasperreports.engine.design.JRDesignGroup)
	 */
	@Override
	protected void layoutGroupCrosstabs(DJGroup columnsGroup, JRDesignGroup jgroup) {
		super.layoutGroupCrosstabs(columnsGroup, jgroup);
		
		JRDesignBand band = LayoutUtils.getBandFromSection((JRDesignSection) jgroup.getGroupHeaderSection());
		
		JRElement[] jrElements = band.getElements();
		
		List<DJCrosstab> djCrossTabLst = columnsGroup.getHeaderCrosstabs();
		int i =0;
		for (JRElement jrElement : jrElements) {
			
			if(jrElement instanceof JRDesignCrosstab){
				JRDesignCrosstab crosst = (JRDesignCrosstab) jrElement;
				
				if(crosst.getColumnGroups()!=null){
					modifyCrossTabGroups(crosst.getColumnGroupsList());
				}
				if(crosst.getRowGroups()!=null){
					modifyCrossTabGroups(crosst.getRowGroupsList());
				}
				if(crosst.getMeasures()!=null && crosst.getMeasures().length>0){
					if(crosst.getMeasures().length>1){
						setMultipleMeasureColHeader(crosst);
						setCellsHorizontal(crosst);
					} 
					DJCrosstab djCrosst = djCrossTabLst.get(i++);
					modifyCrossTabParams(djCrosst.getMeasures(), crosst.getParameters());
				}
			}
		}
	}
	
	protected void modifyCrossTabGroups(final List<? extends JRCrosstabGroup> crossTabGroups){
		for (JRCrosstabGroup jRCrosstabGroup : crossTabGroups) {
			JRDesignCrosstabBucket bucket = (JRDesignCrosstabBucket) jRCrosstabGroup.getBucket();
			if(DJComparatorWrapper.class.isAssignableFrom(bucket.getExpression().getValueClass())){
				String comparatorParam = DJHelperConstants.DJ_COMPARATORS_MAP.get(bucket.getExpression().getValueClassName());
				JRDesignExpression expression = new JRDesignExpression();
				expression.setText("$P{"+comparatorParam+"}");
				//expression.setValueClassName(Comparator.class.getName());
				bucket.setComparatorExpression(expression);
				JRElement[] jrelements = jRCrosstabGroup.getHeader().getElements();
				for (JRElement jrElement : jrelements) {
					if(jrElement instanceof JRDesignTextField){
						JRDesignTextField jrDesignElement = (JRDesignTextField) jrElement;
						JRDesignExpression jRDesignExpression = (JRDesignExpression) jrDesignElement.getExpression();
						//jRDesignExpression.setValueClassName(String.class.getName());
						jRDesignExpression.setText(jRDesignExpression.getText()+".toString()");
					}
				}
			}
		}
	}
	
	protected void setCellsHorizontal(final JRDesignCrosstab crosst){
		List<JRCrosstabCell> cells =crosst.getCellsList();
		if(cells!=null){
			for (JRCrosstabCell jrCrosstabCell : cells) {
					int i=0;
					JRCellContents contents = jrCrosstabCell.getContents();
					JRElement[] cellElements = contents.getElements();
					for (int j = 0; j < cellElements.length; j++) {
						JRDesignTextField jrdtf = (JRDesignTextField)cellElements[j];
						jrdtf.setStretchType(StretchTypeEnum.RELATIVE_TO_BAND_HEIGHT);
						jrdtf.setWidth(jrCrosstabCell.getWidth()/cellElements.length);
						jrdtf.setY(0);
						jrdtf.setX(jrCrosstabCell.getWidth()/cellElements.length*i++);
						jrdtf.setHeight(jrCrosstabCell.getHeight());
						if(j!=cellElements.length-1){
							jrdtf.getLineBox().getRightPen().setLineWidth(0.5f);
							jrdtf.getLineBox().getRightPen().setLineStyle(LineStyleEnum.SOLID);
							jrdtf.getLineBox().getRightPen().setLineColor(Color.BLACK);									
						}
					}
			}
		}
	}
	
	protected void setMultipleMeasureColHeader(final JRDesignCrosstab crosst){
		List<JRCrosstabColumnGroup> colGroups = crosst.getColumnGroupsList();
		int measuresSize = crosst.getMeasures().length;
		int measureWidth = 0;
		if(colGroups!=null){
			JRCrosstabColumnGroup jrDesignCrosstabColumnGroup = colGroups.get(colGroups.size()-1);
			JRDesignCellContents  jrCellCotents =  (JRDesignCellContents)jrDesignCrosstabColumnGroup.getHeader();
			JRElement[] cellElements = jrCellCotents.getElements();
			JRDesignTextField jrdtf = null;
			for (JRElement jrElement2 : cellElements) {
				jrdtf = (JRDesignTextField)jrElement2;
				jrdtf.setHeight(jrDesignCrosstabColumnGroup.getHeight()/2);
			}
			
			measureWidth = jrdtf.getWidth()/measuresSize;
			
			for (int i = 0; i < measuresSize; i++) {
				JRDesignStaticText element0 = new JRDesignStaticText();
				element0.setWidth(measureWidth);
				element0.setHeight(jrDesignCrosstabColumnGroup.getHeight()/2);
				element0.setX(i*measureWidth);
				element0.setY(jrDesignCrosstabColumnGroup.getHeight()/2);
				element0.setHorizontalAlignment(HorizontalAlignEnum.CENTER);
				element0.getLineBox().getTopPen().setLineWidth(0.5f);
				element0.getLineBox().getTopPen().setLineStyle(LineStyleEnum.SOLID);
				element0.getLineBox().getTopPen().setLineColor(Color.BLACK);
				if(i!=measuresSize-1){
					element0.getLineBox().getRightPen().setLineWidth(0.5f);
					element0.getLineBox().getRightPen().setLineStyle(LineStyleEnum.SOLID);
					element0.getLineBox().getRightPen().setLineColor(Color.BLACK);
				}
				element0.setText(parameters.get("measure_label_"+i));
				jrCellCotents.addElement(element0);
			}
		}
	}
	
	/**
	 * @param measures
	 * @param parameters
	 */
	protected void modifyCrossTabParams(List<DJCrosstabMeasure> measures, JRCrosstabParameter[] parameters){
		for (JRCrosstabParameter jrCrosstabParameter : parameters) {
			if(jrCrosstabParameter instanceof JRDesignCrosstabParameter){
				if(jrCrosstabParameter.getName().indexOf(DJHelperConstants.TOTAL_PROVIDER_PARAM_NAME_SUFFIX)!=-1){
					int measureNo = getMeasureNo(jrCrosstabParameter.getName());
					DJCrosstabMeasure djcm = measures.get(measureNo);
					if(djcm.getPrecalculatedTotalProvider()!=null){
						((JRDesignCrosstabParameter)jrCrosstabParameter).setValueClass(djcm.getPrecalculatedTotalProvider().getClass());						
					}
				}else if(jrCrosstabParameter.getName().indexOf(DJHelperConstants.VALUE_FORMATTER_PARAM_NAME_SUFFIX)!=-1){
					int measureNo = getMeasureNo(jrCrosstabParameter.getName());
					DJCrosstabMeasure djcm = measures.get(measureNo);
					if(djcm.getValueFormatter()!=null){
						((JRDesignCrosstabParameter)jrCrosstabParameter).setValueClass(djcm.getValueFormatter().getClass());						
					}
				}				
			}
		}
	}
	
	/**
	 * Assuming the crossTab parameters generated by DJ are generated with convention
	 * <BR>{@link CrossTabLayoutManager.CROSSTAB_MEASURE_PARAM_START+CROSSTAB_MEASURE_PREFIX+measureseqNo+_*
	 * 
	 * @param name
	 * @return
	 */
	private int getMeasureNo(String name){
		int measureNo = -1;
		try {
			int idx = name.indexOf(CROSSTAB_MEASURE_PARAM_START+CROSSTAB_MEASURE_PREFIX);
			if(idx!=-1){
				name = name.substring(idx+(CROSSTAB_MEASURE_PARAM_START+CROSSTAB_MEASURE_PREFIX).length());
				if(name.indexOf("_")!=-1){
					name = name.substring(0,name.indexOf("_"));
					measureNo = Integer.parseInt(name);	
				}
			}			
		} catch (NumberFormatException e) {
			TPLogger.getLogger().error(GlobalConstants.ERROR, e);
			return -1;
		}
		return measureNo;
	}


	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}
}