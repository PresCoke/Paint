package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.PaintViewControl;

public class ViewWithCanvas {
	
	private JFrame appWindow;
	private JComponent content;
	private Canvas drawingCanvas;
	private JToggleButton lineButton, rectButton, circButton, noDrawButton;
	private PaintViewControl control;
	private JSlider redSlider, greenSlider, blueSlider;
	private JPanel colourBar;
	
	public void initializeComponents()
    {
		control = new PaintViewControl(this);
		
    	this.drawingCanvas = new Canvas();
    	this.drawingCanvas.setBackground(Color.WHITE);
    	this.drawingCanvas.addMouseMotionListener(control.getMouseMotionListener());
    	this.drawingCanvas.addMouseListener(control.getMouseListener());
    	this.drawingCanvas.addKeyListener(control.getKeyListener());
    	this.drawingCanvas.setVisible(true);
    	
    	lineButton = new JToggleButton();
    	lineButton.setText("Line");
    	lineButton.setVisible(true);
    	lineButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		control.setLineDraw();
	    		
	    		rectButton.setSelected(false);
	    		circButton.setSelected(false);
	    		noDrawButton.setSelected(false);
	    	}
	    });
    	
    	rectButton = new JToggleButton();
    	rectButton.setText("Rect");
    	rectButton.setVisible(true);
    	rectButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		control.setRectDraw();
	    		
	    		lineButton.setSelected(false);
	    		circButton.setSelected(false);
	    		noDrawButton.setSelected(false);
	    	}
	    });
    	
    	circButton = new JToggleButton();
    	circButton.setText("Circ");
    	circButton.setVisible(true);
    	circButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		control.setCircDraw();
	    		
	    		lineButton.setSelected(false);
	    		rectButton.setSelected(false);
	    		noDrawButton.setSelected(false);
	    	}
	    });
    	
    	noDrawButton = new JToggleButton();
    	noDrawButton.setText("ND");
    	noDrawButton.setVisible(true);
    	noDrawButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		control.setNoDraw();
	    		
	    		lineButton.setSelected(false);
	    		rectButton.setSelected(false);
	    		circButton.setSelected(false);
	    	}
	    });
    	
    	JPanel buttonControl = new JPanel();
    	buttonControl.setLayout(new GridLayout(2, 2));
    	buttonControl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
    	buttonControl.add(noDrawButton);
    	buttonControl.add(lineButton);
    	buttonControl.add(rectButton);
    	buttonControl.add(circButton);
    	buttonControl.revalidate();
    	
    	colourBar = new JPanel();
    	colourBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	
    	JPanel colourControlHead = new JPanel();
    	colourControlHead.setLayout(new GridLayout(2, 3));
    	colourControlHead.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	colourControlHead.add(new JLabel("   Red"));
    	colourControlHead.add(new JLabel("  Green"));
    	colourControlHead.add(new JLabel("   Blue"));
    	colourControlHead.add(new JLabel("   255"));
    	colourControlHead.add(new JLabel("   255"));
    	colourControlHead.add(new JLabel("   255"));
    	
    	JPanel colourControlSlides = new JPanel();
    	colourControlSlides.setLayout(new GridLayout(1, 3));
    	colourControlSlides.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	redSlider = new JSlider();
    	redSlider.setMaximum(255);
    	redSlider.setOrientation(JSlider.VERTICAL);
    	redSlider.setInverted(false);
    	redSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce) {
				adjustColour();
			}
    	});
    	colourControlSlides.add(redSlider);
    	greenSlider = new JSlider();
    	greenSlider.setMaximum(255);
    	greenSlider.setOrientation(JSlider.VERTICAL);
    	greenSlider.setInverted(false);
    	greenSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce) {
				adjustColour();
			}
    	});
    	colourControlSlides.add(greenSlider);
    	blueSlider = new JSlider();
    	blueSlider.setMaximum(255);
    	blueSlider.setOrientation(JSlider.VERTICAL);
    	blueSlider.setInverted(false);
    	blueSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent ce) {
				adjustColour();
			}
    	});
    	colourControlSlides.add(blueSlider);
    	JPanel colourControlTail = new JPanel();
    	colourControlTail.setLayout(new GridLayout(1, 3));
    	colourControlTail.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	colourControlTail.add(new JLabel("   0"));
    	colourControlTail.add(new JLabel("   0"));
    	colourControlTail.add(new JLabel("   0"));
    	
    	JPanel tempPanel = new JPanel();
    	tempPanel.setLayout(new GridLayout(5, 1));
    	tempPanel.add(buttonControl);
    	tempPanel.add(colourBar);
    	tempPanel.add(colourControlHead);
    	tempPanel.add(colourControlSlides);
    	tempPanel.add(colourControlTail);
    	
    	JPanel contentPanel = new JPanel();
    	contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	contentPanel.setLayout(new BorderLayout());
    	contentPanel.add(tempPanel, BorderLayout.WEST);
    	contentPanel.add(this.drawingCanvas, BorderLayout.CENTER);
    	contentPanel.revalidate();
    	
    	this.content = contentPanel;
    	this.content.setOpaque(true);
    	
    	this.appWindow = new JFrame();
    	this.appWindow.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    		System.exit(0);
    		}
    	});
    	this.appWindow.setAlwaysOnTop(true);
    	this.appWindow.setMinimumSize(new Dimension(700, 700));
    	//this.appWindow.setResizable(false);
    	this.appWindow.setTitle("puh-puh-puh-Paint!");
    	this.appWindow.setContentPane(content);
    	
    	this.appWindow.pack();
    	this.adjustColour();
    }
	
	public void showWindow()
    {
    	this.appWindow.setVisible(true);
    }
	
	public Color getBackgroundColour()
	{
		return (this.drawingCanvas.getBackground());
	}
	public Graphics getGraphics()
    {
    	return (this.drawingCanvas.getGraphics());
    }
	
	public void clearCanvas()
	{
		Graphics g = this.drawingCanvas.getGraphics();
		g.clearRect(0, 0, this.drawingCanvas.getWidth(), this.drawingCanvas.getHeight());
	}
	private void adjustColour()
	{
		Color c = new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
		colourBar.setBackground(c);
		control.changeColour(c);
	}
}
