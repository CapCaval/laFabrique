package org.capcaval.ermine.layout;

import org.capcaval.lafabrique.pair.Pair;
import org.capcaval.lafabrique.pair.PairFactory;


public class NodeWrapper <T>{
	
	enum LayoutTypeEnum{ onePoint, twoPoints}
	enum TwoPointsTypeEnum{ onlyWidth, onlyHeight, bothWidthHeight}
	
	RefPoint<T> point1;
	RefPoint<T> point2;
	LayoutTypeEnum type;
	TwoPointsTypeEnum twoPointsType;
	RefPointAlignEnum align;
	
	public NodeWrapper(T node, RefPointEnum refPoint, RefPointAlignEnum align, int deltaX, int deltaY) {
		// this is a one point ref
		this.point1 = new RefPoint<T>(node, refPoint, deltaX, deltaY);
		this.point2 = null;
		this.type = LayoutTypeEnum.onePoint;
		this.align = align;
	}

	public NodeWrapper(T node, 
			RefPointEnum tlRefpoint, int tlDeltaX, int tlDeltaY, 
			RefPointEnum brRefpoint, int brDeltaX, int brDeltaY,
			TwoPointsTypeEnum twoPointsType) {
		// compute the ref points

		// compute the first point
		Pair<Integer, Integer> result = this.computePercentFromRefPoint(tlRefpoint);
		this.point1 = new RefPoint<T>(
				node, 
				result.firstItem(), // percentX
				result.secondItem(), // percentY 
				tlDeltaX, tlDeltaY);
		
		// compute the second one
		result = this.computePercentFromRefPoint(brRefpoint);
		this.point2 = new RefPoint<T>(
				node,  
				result.firstItem(), // percentX 
				result.secondItem(), // percentY 
				brDeltaX, brDeltaY);
		
		this.type = LayoutTypeEnum.twoPoints;
		this.twoPointsType = twoPointsType;
	}

	private Pair<Integer, Integer> computePercentFromRefPoint(RefPointEnum refpoint) {
		int percentX = 0;
		int percentY = 0;
		
		switch(refpoint){
			case topLeftPoint :		percentX = 0; percentY = 0; break;
			case topCenterPoint :	percentX = 50; percentY = 0; break;
			case topRightPoint :	percentX = 100; percentY = 0; break;
			case middleLeftPoint :	percentX = 0; percentY = 50; break;
			case middleCenterPoint :percentX = 50; percentY = 50; break;
			case middleRightPoint : percentX = 100; percentY = 50;break;
			case bottomLeftPoint : 	percentX = 0; percentY = 100;break;
			case bottomCenterPoint :percentX = 50; percentY = 100; break;
			case bottomRightPoint : percentX = 100; percentY = 100; break;

			default : break;
		}
		
		return PairFactory.factory.newPair(percentX, percentY);
	}

	public RefPoint<T> getPoint1() {
		return this.point1;
	}
	
	public RefPoint<T> getPoint2() {
		return this.point2;
	}
	
	public LayoutTypeEnum getType(){
		return this.type;
	}
	
	public TwoPointsTypeEnum getTwoPointsType(){
		return this.twoPointsType;
	}
	
	public RefPointAlignEnum getRefPointAlignEnum(){
		return this.align;
	}
}
