package com.bzu.widget;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 使用Java 2D API改进毛刺
 */
public class StillClockPlus2D extends JPanel {
	private int hour;
	private int minute;
	private int second;

	public StillClockPlus2D() {
		setCurrentTime();
	}

	public StillClockPlus2D(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
		repaint();
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
		repaint();
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawClock(g2d);
	}

	private void drawClock(Graphics2D g) {
		// Initialize clock parameters
		int clockRadius = (int)(Math.min(getWidth(), getHeight()*0.8*0.5));
		int xCenter = getWidth() / 2;
		int yCenter = getHeight()/2;

		// Draw small line
		int x1,y1,x2,y2;
		int si = (int) (clockRadius*0.95);
		int mi = (int) (clockRadius*0.9);
		for(int i=0;i<60;i++){
			if (i%5==0) {
				x1 = (int)(xCenter + clockRadius*Math.sin(i*(2*Math.PI/60)));
				y1 = (int)(yCenter - clockRadius*Math.cos(i*(2*Math.PI/60)));
				x2 = (int)(xCenter + mi*Math.sin(i*(2*Math.PI/60)));
				y2 = (int)(yCenter - mi*Math.cos(i*(2*Math.PI/60)));
				g.drawLine(x1, y1, x2, y2);

				if (i<30) {
					g.drawString(String.valueOf(i==0?12:i/5), x2-5, y2+10);
				} else {
					g.drawString(String.valueOf(i==0?12:i/5), x2, y2);
				}
			} else {
				x1 = (int)(xCenter + clockRadius*Math.sin(i*(2*Math.PI/60)));
				y1 = (int)(yCenter - clockRadius*Math.cos(i*(2*Math.PI/60)));
				x2 = (int)(xCenter + si*Math.sin(i*(2*Math.PI/60)));
				y2 = (int)(yCenter - si*Math.cos(i*(2*Math.PI/60)));
				g.drawLine(x1, y1, x2, y2);
			}
		}

		// Draw circle
		g.setColor(Color.BLACK);
		g.drawOval(xCenter-clockRadius, yCenter-clockRadius, 2*clockRadius, 2*clockRadius);

		// Draw second hand
		int sLength = (int)(clockRadius * 0.8);
		int xSecond = (int)(xCenter + sLength*Math.sin(second*(2*Math.PI/60)));
		int ySecond = (int)(yCenter - sLength*Math.cos(second*(2*Math.PI/60)));
		g.setColor(Color.RED);
		g.drawLine(xCenter, yCenter, xSecond, ySecond);

		// Draw minute hand
		int mLength = (int)(clockRadius*0.65);
		int xMinute = (int)(xCenter+mLength*Math.sin(minute*(2*Math.PI/60)));
		int yMinute = (int)(yCenter-mLength*Math.cos(minute*(2*Math.PI/60)));
		g.setColor(Color.BLUE);
		g.drawLine(xCenter, yCenter, xMinute, yMinute);

		// Draw hour hand
		int hLength = (int)(clockRadius * 0.5);
		int xHour = (int)(xCenter+hLength*Math.sin((hour%12+minute/60.0)*(2*Math.PI/12)));
		int yHour = (int)(yCenter - hLength*Math.cos((hour%12+minute/60.0)*(2*Math.PI/12)));
		g.setColor(Color.GREEN);
		g.drawLine(xCenter, yCenter, xHour, yHour);
	}

	public void setCurrentTime() {
		// Construct a calendar for the current date and time
		Calendar calendar = new GregorianCalendar();
		
		// Set current hour, minute and second
		this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		this.minute = calendar.get(Calendar.MINUTE);
		this.second = calendar.get(Calendar.SECOND);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(200, 200);
	}
	
}
