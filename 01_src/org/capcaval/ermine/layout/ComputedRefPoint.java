package org.capcaval.ermine.layout;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Point2D;

public class ComputedRefPoint {
	
	protected Point2D topLeftPoint; 
	protected Point2D topCenterPoint;
	protected Point2D topRightPoint;
	protected Point2D middleLeftPoint; 
	protected Point2D middleCenterPoint;
	protected Point2D middleRightPoint;
	protected Point2D bottomLeftPoint; 
	protected Point2D bottomCenterPoint;
	protected Point2D bottomRightPoint;
	protected Map<RefPointEnum, Point2D> map;

	
	public Point2D getTopLeftPoint() {
		return topLeftPoint;
	}
	public Point2D getTopCenterPoint() {
		return topCenterPoint;
	}
	public Point2D getTopRightPoint() {
		return topRightPoint;
	}
	public Point2D getMiddleLeftPoint() {
		return middleLeftPoint;
	}
	public Point2D getMiddleCenterPoint() {
		return middleCenterPoint;
	}
	public Point2D getMiddleRightPoint() {
		return middleRightPoint;
	}
	public Point2D getBottomLeftPoint() {
		return bottomLeftPoint;
	}
	public ComputedRefPoint(Point2D topLeftPoint, Point2D topCenterPoint,
			Point2D topRightPoint, Point2D middleLeftPoint,
			Point2D middleCenterPoint, Point2D middleRightPoint,
			Point2D bottomLeftPoint, Point2D bottomCenterPoint,
			Point2D bottomRightPoint) {
		super();
		this.topLeftPoint = topLeftPoint;
		this.topCenterPoint = topCenterPoint;
		this.topRightPoint = topRightPoint;
		this.middleLeftPoint = middleLeftPoint;
		this.middleCenterPoint = middleCenterPoint;
		this.middleRightPoint = middleRightPoint;
		this.bottomLeftPoint = bottomLeftPoint;
		this.bottomCenterPoint = bottomCenterPoint;
		this.bottomRightPoint = bottomRightPoint;

		// allocate the map
		this.map = new HashMap<RefPointEnum, Point2D>();
		
		this.map.put( RefPointEnum.topLeftPoint, this.topLeftPoint);
		this.map.put( RefPointEnum.topCenterPoint, this.topCenterPoint);
		this.map.put( RefPointEnum.topRightPoint, this.topRightPoint);
		this.map.put( RefPointEnum.middleLeftPoint, this.middleLeftPoint);
		this.map.put( RefPointEnum.middleCenterPoint, this.middleCenterPoint);
		this.map.put( RefPointEnum.middleRightPoint, this.middleRightPoint);
		this.map.put( RefPointEnum.bottomLeftPoint, this.bottomLeftPoint);
		this.map.put( RefPointEnum.bottomCenterPoint, this.bottomCenterPoint);
		this.map.put( RefPointEnum.bottomRightPoint, this.bottomRightPoint);
	}

	public Point2D getPoint(RefPointEnum refPoint){
		return this.map.get(refPoint);
	}
	public Point2D getBottomCenterPoint() {
		return bottomCenterPoint;
	}
	public Point2D getBottomRightPoint() {
		return bottomRightPoint;
	}
}
