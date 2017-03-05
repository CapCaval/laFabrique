package org.capcaval.ermine.layout;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

import org.capcaval.ermine.layout.NodeWrapper.TwoPointsTypeEnum;
import org.capcaval.ermine.types.IntPoint;

public abstract class RefPointLayout<P, N> implements NodeLayout, NodeLayoutComputer {

	protected List<NodeWrapper<N>> wrapperList = new ArrayList<NodeWrapper<N>>();
	protected P pane;
	private int widthInPixel;
	private int heightInPixel;

	public RefPointLayout(P pane) {
		this.pane = pane;

		this.init(pane);
	}

	abstract protected void init(P pane);

	abstract protected void addNode(N node);

	abstract protected void removeNodeinParent(N node);

	abstract protected int getWidth(N node);

	abstract protected int getHeight(N node);

	public void addNode(N node, RefPointEnum refpoint) {
		// if null add it without layout
		if (refpoint != null) {
			// call the other addshape without deltax and deltay
			this.addNode(node, refpoint, 0, 0);
		} else {
			// add it to the shapePanel without a layout
			this.addNode(node);
		}
	}

	public void removeNode(N node) {
		for (NodeWrapper<?> wrapper : this.wrapperList) {
			if (wrapper.getPoint1().getNode() == node) {
				// remove
				this.wrapperList.remove(node);
				// remove from parent
				this.removeNodeinParent(node);

				// get out
				break;
			}
		}
	}

	public void addNode(N node, RefPointEnum refPoint, int deltaX, int deltaY) {
		// wrap it
		NodeWrapper<N> wrapper = new NodeWrapper<N>(node, refPoint, RefPointAlignEnum.topLeftAlign, deltaX, deltaY);
		// keep it
		this.wrapperList.add(wrapper);
		// add it to the shapePanel
		this.addNode(node);
		// compute the minimum size
		// TODO

		// recompute with the current size
		this.computeLayout(this.widthInPixel, this.heightInPixel);
	}

	public void addNode(N node, RefPointEnum refPointEnum, RefPointAlignEnum refPointAlignEnum) {
		// wrap it
		NodeWrapper<N> wrapper = new NodeWrapper<N>(node, refPointEnum, refPointAlignEnum, 0, 0);
		// keep it
		this.wrapperList.add(wrapper);
		// add it to the shapePanel
		this.addNode(node);
		// compute the minimum size
		// TODO

		// recompute with the current size
		this.computeLayout(this.widthInPixel, this.heightInPixel);
	}

	public void addCenteredNode(N node, RefPointEnum refPoint) {
		this.addCenteredNode(node, refPoint, 0, 0);
	}

	public void addCenteredNode(N node, RefPointEnum refPoint, int deltaX, int deltaY) {

		int width = this.getWidth(node);
		int height = this.getWidth(node);

		// wrap it
		NodeWrapper<N> wrapper = new NodeWrapper<N>(node, refPoint, RefPointAlignEnum.middleCenterAlign, deltaX - width
				/ 2, deltaY - height / 2);
		// keep it
		this.wrapperList.add(wrapper);
		// add it to the shapePanel
		this.addNode(node);
		// compute the minimum size
		// TODO it is going to be easy

		// recompute with the current size
		this.computeLayout(this.widthInPixel, this.heightInPixel);
	}

	public void addResizableNode(N node, int tlDeltaX, int tlDeltaY, int brDeltaX, int brDeltaY, TwoPointsTypeEnum type) {
		// wrap it
		NodeWrapper<N> wrapper = new NodeWrapper<N>(node, RefPointEnum.topLeftPoint, tlDeltaX, tlDeltaY,
				RefPointEnum.bottomRightPoint, brDeltaX, brDeltaY, type);
		// keep it
		this.wrapperList.add(wrapper);
		// add it to the shapePanel
		this.addNode(node);

		// recompute with the current size
		this.computeLayout(this.widthInPixel, this.heightInPixel);
	}

	public void addResizableNode(N node, int tlDeltaX, int tlDeltaY, int brDeltaX, int brDeltaY) {
		// by default move all
		addResizableNode(node, tlDeltaX, tlDeltaY, brDeltaX, brDeltaY, TwoPointsTypeEnum.bothWidthHeight);
	}

	public void addResizableNode(N node, RefPointEnum tlRefpoint, int tlDeltaX, int tlDeltaY, RefPointEnum brRefpoint,
			int brDeltaX, int brDeltaY, TwoPointsTypeEnum type) {
		// wrap it
		NodeWrapper<N> wrapper = new NodeWrapper<N>(node, tlRefpoint, tlDeltaX, tlDeltaY, brRefpoint, brDeltaX,
				brDeltaY, type);
		// keep it
		this.wrapperList.add(wrapper);
		// add it to the shapePanel
		this.addNode(node);

		// recompute with the current size
		this.computeLayout(this.widthInPixel, this.heightInPixel);
	}

	public void addResizableNode(N node, RefPointEnum tlRefpoint, int tlDeltaX, int tlDeltaY, RefPointEnum brRefpoint,
			int brDeltaX, int brDeltaY) {
		this.addResizableNode(node, tlRefpoint, tlDeltaX, tlDeltaY, brRefpoint, brDeltaX, brDeltaY,
				TwoPointsTypeEnum.bothWidthHeight);
	}

	// public void addResizableWidthNode(N node, RefPointEnum refpoint){
	//
	// }

	@Override
	public void computeLayout(int newWidthInPixel, int newHeightInPixel) {
		this.computeLayout(this.wrapperList, newWidthInPixel, newHeightInPixel);

	}

	public void computeLayout(List<NodeWrapper<N>> shapeList, int newWidthInPixel, int newHeightInPixel) {
		// firstly keep the bound
		this.widthInPixel = newWidthInPixel;
		this.heightInPixel = newHeightInPixel;

		// secondly compute all the ref point
		ComputedRefPoint allRefPoint = this.computeNewRefPoints(newWidthInPixel, newHeightInPixel);

		// compute new position for the given shape
		for (NodeWrapper<N> wrapper : shapeList) {
			// one point ref
			if (wrapper.point2 == null) {
				// get the shape
				N node = wrapper.getPoint1().getNode();
				// get the ref point position
				Point2D point = allRefPoint.getPoint(wrapper.getPoint1().getRefPoint());

				// get the align delta
				IntPoint deltaAlignPoint = this.computeAlignedPoint(node, wrapper.getRefPointAlignEnum());
				
				// the delta x and y sum from align and deltaxy
				int deltax = wrapper.getPoint1().getDeltaX() - deltaAlignPoint.getX();
				int deltay = wrapper.getPoint1().getDeltaY() - deltaAlignPoint.getY();
				
				// compute the new position
				this.setXOnNode(node, point.getX() + deltax);
				this.setYOnNode(node, point.getY() + deltay);

			} else { // two points ref
				// compute the two ref points if any
				Point2D pointTL = computePoint(newWidthInPixel, newHeightInPixel, wrapper.point1);

				Point2D pointBR = computePoint(newWidthInPixel, newHeightInPixel, wrapper.point2);

				// Compute the width of the node
				double width = pointBR.getX() - pointTL.getX();
				// Compute the height of the node
				double height = pointBR.getY() - pointTL.getY();

				N node = wrapper.getPoint1().getNode();

				// set width and height
				this.setXOnNode(node, pointTL.getX());
				this.setYOnNode(node, pointTL.getY());

				// set width and height
				this.setWidthOnNode(node, width);
				this.setHeightOnNode(node, height);
			}
		}
	}

	private Point2D computePoint(int width, int height, RefPoint<N> refPoint) {
		// compute x and y refPoint
		double refx = (double) width / 100 * refPoint.getPercentX();
		double refy = (double) height / 100 * refPoint.getPercentY();

		// compute now with the deltax and deltay
		Point2D point = new Point2D(refx + refPoint.getDeltaX(), refy + refPoint.getDeltaY());

		// System.out.println(refPoint + " out : " + point + " w : " + width +
		// " h : " + height);

		return point;
	}

	abstract protected void setXOnNode(N node, double d);

	abstract protected void setYOnNode(N node, double d);

	abstract protected void setWidthOnNode(N node, double d);

	abstract protected void setHeightOnNode(N node, double d);

	protected IntPoint computeAlignedPoint(N node, RefPointAlignEnum alignEnum) {
		int height = this.getHeight(node);
		int width = this.getWidth(node);

		int top = 0;
		int middle = width / 2;
		int bottom = height;
		int left = 0;
		int center = width / 2;
		int right = width;

		int deltax = top;
		int deltay = left;
		
		switch (alignEnum) {
			case topLeftAlign:
				deltax=left;
				deltay=top;
				break;
			case topCenterAlign:
				deltax=center;
				deltay=top;
				break;
			case topRightAlign:
				deltax=right;
				deltay=top;
				break;
			case middleLeftAlign:
				deltax=left;
				deltay=middle;
				break;
			case middleCenterAlign:
				deltax=center;
				deltay=middle;
				break;
			case middleRightAlign:
				deltax=right;
				deltay=middle;
				break;
			case bottomLeftAlign:
				deltax=left;
				deltay=bottom;
				break;
			case bottomCenterAlign:
				deltax=center;
				deltay=bottom;
				break;
			case bottomRightAlign:
				deltax=right;
				deltay=bottom;
				break;
	
			default:
				break;
		}

		return new IntPoint(deltax, deltay);
	}

	protected ComputedRefPoint computeNewRefPoints(int newWidthInPixel, int newHeightInPixel) {
		int top = 0;
		int middle = newHeightInPixel / 2;
		int bottom = newHeightInPixel;
		int left = 0;
		int center = newWidthInPixel / 2;
		int right = newWidthInPixel;

		// allocate the new instance
		ComputedRefPoint returnValue = new ComputedRefPoint(new Point2D(left, top), // topleft
				new Point2D(center, top), // topcenter
				new Point2D(right, top), // topright
				new Point2D(left, middle), // middleleft
				new Point2D(center, middle), // middlecenter
				new Point2D(right, middle), // middleright
				new Point2D(left, bottom), // bottomleft
				new Point2D(center, bottom), // bottomcenter
				new Point2D(right, bottom)); // bottomright

		return returnValue;
	}

	@Override
	public NodeLayoutComputer getShapeLayoutComputer() {
		// return itself as ashapeLayout Renderer
		return this;
	}

}
