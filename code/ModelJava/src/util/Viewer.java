package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import com.google.common.base.Function;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import network.Agent;
import network.MEdge;
import network.Network;

public class Viewer {
	VisualizationViewer vv;
	public Viewer() {
		// TODO Auto-generated constructor stub
	}
	
	public void showGraph(Network network){
		// Color Transformer
        Function<Agent, Paint> vertexPaintTrans = new Function<Agent, Paint>() {
      	  public Paint apply(Agent a) {
      		  if(a.getCurrentOpinion()>0){
      			  return Color.GREEN;
      		  }
      		  return Color.RED;
      	  }
      	};
      	
      	// Shape Transformer
      	Function<Agent,Shape> vertexSize = new Function<Agent,Shape>(){
            public Shape apply(Agent a){
                Ellipse2D circle = new Ellipse2D.Double(-5, -5, 10, 10);
                // in this case, the vertex is twice as large
                return AffineTransform.getScaleInstance(.7, .7).createTransformedShape(circle);
            }
        };
      	
      	
        Dimension preferredSize = new Dimension(1000, 1000);
        
      	JFrame jf = new JFrame();
		Graph g = network.graph;
		CircleLayout<Agent, MEdge> myLayout = new CircleLayout<Agent, MEdge>(g);
		myLayout.setSize(preferredSize);
		
		vv = new VisualizationViewer(myLayout);
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaintTrans);
		vv.getRenderContext().setVertexShapeTransformer(vertexSize);
		vv.setPreferredSize(preferredSize);
		
		jf.getContentPane().add(vv);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
                
	}
	
	public void reDraw(){
		vv.repaint();
	}

}
