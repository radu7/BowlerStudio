package com.neuronrobotics.nrconsole.plugin.cartesian;

import com.neuronrobotics.sdk.addons.kinematics.math.TransformNR;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TransformWidget extends GridPane implements IOnAngleChange, EventHandler<ActionEvent> {
	
	private IOnTransformChange onChange;
	AngleSliderWidget rw;
	AngleSliderWidget rx;
	AngleSliderWidget ry;
	AngleSliderWidget rz;
	private TextField tx;
	private TextField ty;
	private TextField tz;

	public TransformWidget(String title, TransformNR initialState, IOnTransformChange onChange){
		this.onChange = onChange;
		tx = new TextField(DHLinkWidget.getFormatted(initialState.getX()));
		ty = new TextField(DHLinkWidget.getFormatted(initialState.getY()));
		tz = new TextField(DHLinkWidget.getFormatted(initialState.getZ()));
		tx.setOnAction(this);
		ty.setOnAction(this);
		tz.setOnAction(this);
		rw = new AngleSliderWidget(this, -180, 180, Math.toDegrees(initialState.getRotation().getRotationMatrix2QuaturnionW()), 100);
		rx = new AngleSliderWidget(this, -180, 180, Math.toDegrees(initialState.getRotation().getRotationMatrix2QuaturnionX()), 100);
		ry = new AngleSliderWidget(this, -180, 180, Math.toDegrees(initialState.getRotation().getRotationMatrix2QuaturnionY()), 100);
		rz = new AngleSliderWidget(this, -180, 180, Math.toDegrees(initialState.getRotation().getRotationMatrix2QuaturnionZ()), 100);

		getColumnConstraints().add(new ColumnConstraints(30)); // translate text
	    getColumnConstraints().add(new ColumnConstraints(60)); // translate values
	    getColumnConstraints().add(new ColumnConstraints(30)); // units
	    getColumnConstraints().add(new ColumnConstraints(30)); // rotate text
	    setHgap(20);// gab between elements
	    
	    
	    add(	new Text(title), 
	    		1,  0);
	    add(	new Text("(r)W"), 
	    		3,  0);
	    add(	rw, 
	    		4,  0);
	    //X line
	    add(	new Text("X"), 
	    		0,  1);
		add(	tx, 
				1,  1);
		 add(	new Text(" mm"), 
	    		2,  1);
		 add(	new Text("(r)X"), 
	    		3,  1);
		 add(	rx, 
	    		4,  1);
	    //Y line
	    add(	new Text("Y"), 
	    		0,  2);
		add(	ty, 
				1,  2);
		 add(	new Text(" mm"), 
	    		2,  2);
		 add(	new Text("(r)Y"), 
	    		3,  2);
		 add(	ry, 
				4,  2);
	    //Z line
	    add(	new Text("Z"), 
	    		0,  3);
		add(	tz, 
				1,  3);
		 add(	new Text(" mm"), 
	    		2,  3);
		 add(	new Text("(r)Z"), 
	    		3,  3);
		 add(	rz, 
	    		4,  3);
		
	}

	@Override
	public void onSliderMoving(AngleSliderWidget source, double newAngleDegrees) {
		onChange.onTransformChaging(new TransformNR(	Double.parseDouble(tx.getText()),
				Double.parseDouble(ty.getText()),
				Double.parseDouble(tz.getText()),
				Math.toRadians(rw.getValue()),
				Math.toRadians(rx.getValue()),
				Math.toRadians(ry.getValue()),
				Math.toRadians(rz.getValue())));
	}

	@Override
	public void onSliderDoneMoving(AngleSliderWidget source,
			double newAngleDegrees) {
		handle(null);
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		onChange.onTransformFinished(new TransformNR(	Double.parseDouble(tx.getText()),
													Double.parseDouble(ty.getText()),
													Double.parseDouble(tz.getText()),
													Math.toRadians(rw.getValue()),
													Math.toRadians(rx.getValue()),
													Math.toRadians(ry.getValue()),
													Math.toRadians(rz.getValue()))
		);
	}

}
