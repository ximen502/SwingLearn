import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Calendar;
/**
 * Swing仿h5版本的模拟时钟
 * http://www.youhutong.com/index.php/yanshi/index/331.html
 * @author xsc
 */
public class JClockPanel extends JPanel {
    Color secondHandColor = new Color(0xf3, 0xa8, 0x29);
    Color minuteHandColor = new Color(0x22, 0x22, 0x22);
    Color hourHandColor = new Color(0x22, 0x22, 0x22);
    Color markColor = new Color(0x22, 0x22, 0x22);
    int hour = 0;
    int minute = 0;
    int second = 0;

    public JClockPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(width / 2, height / 2);
        //drawAxis(g);

        float theta = (float) (2 * Math.PI / 60);
        float radius = 150f;
        float radiusLong = 164f;
        float radiusEnd = 178f;
        g2.setColor(markColor);
        Line2D.Float line;
        BasicStroke big = new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        BasicStroke small = new BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        for (int i = 0; i < 60; i++) {
            float x1 = 0f;
            float y1 = 0f;
            float x2 = 0f;
            float y2 = 0f;
            if (i % 5 == 0) {
                x1 = (float) (Math.cos(theta * i) * radiusLong);
                y1 = (float) (Math.sin(theta * i) * radiusLong);
                x2 = (float) (Math.cos(theta * i) * radiusEnd);
                y2 = (float) (Math.sin(theta * i) * radiusEnd);
                line = new Line2D.Float(x1, y1, x2, y2);
                g2.setStroke(big);
            } else {
                x1 = (float) (Math.cos(theta * i) * (radiusLong + 5));
                y1 = (float) (Math.sin(theta * i) * (radiusLong + 5));
                x2 = (float) (Math.cos(theta * i) * radiusEnd);
                y2 = (float) (Math.sin(theta * i) * radiusEnd);
                line = new Line2D.Float(x1, y1, x2, y2);
                g2.setStroke(small);
            }
            g2.draw(line);
        }

        float numRadius = radius * 0.9f;
        g2.setFont(new Font("", Font.PLAIN, 22));
        //draw numbers
        float numTheta = (float) (2 * Math.PI / 12f);
        for (int i = 0; i <= 11; i++) {
            float x1 = (float) (Math.cos(numTheta * i - Math.PI / 3) * numRadius);
            float y1 = (float) (Math.sin(numTheta * i - Math.PI / 3) * numRadius);
            g2.drawString(String.valueOf(i + 1), x1, y1);
        }


        float x1 = 0f;
        float y1 = 0f;
        float x2 = 0f;
        float y2 = 0f;

        float hourTheta = (float) (2 * Math.PI / 12);
        //draw hour hand
        //修线条粗细
        BasicStroke basicStroke = new BasicStroke(20.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2.setStroke(basicStroke);
        g2.setColor(hourHandColor);
        /* hourTheta * (minute / 60f)分针变化对时针的影响 2pi/12*(minute/60) */
        x2 = (float) (radius * 0.75f * Math.cos(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2));
        y2 = (float) (radius * 0.75f * Math.sin(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2));
        Line2D.Float hourLine = new Line2D.Float(x1, y1, x2, y2);
        g2.draw(hourLine);

        //println("second:$second, ${theta * (second / 60f)}")

        //draw minute hand
        basicStroke = new BasicStroke(9.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2.setStroke(basicStroke);
        g2.setColor(minuteHandColor);
        /* theta * (second / 60f)秒针变化对分针的影响 //2pi/60 * (second/60)*/
        x2 = (float) (radiusLong * 1.0f * Math.cos(theta * (minute) + theta * (second / 60f) - Math.PI / 2));
        y2 = (float) (radiusLong * 1.0f * Math.sin(theta * (minute) + theta * (second / 60f) - Math.PI / 2));
        Line2D.Float minuteLine = new Line2D.Float(x1, y1, x2, y2);
        g2.draw(minuteLine);

        //draw second hand
        basicStroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2.setStroke(basicStroke);
        g2.setColor(secondHandColor);
        x2 = (float) (radiusLong * 1.1f * Math.cos(theta * (second) - Math.PI / 2));
        y2 = (float) (radiusLong * 1.1f * Math.sin(theta * (second) - Math.PI / 2));
        Line2D.Float secondLine = new Line2D.Float(x1, y1, x2, y2);
        g2.draw(secondLine);

        //draw second handle
        basicStroke = new BasicStroke(13.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
        g2.setStroke(basicStroke);
        g2.setColor(secondHandColor);
        x2 = (float) (radiusLong * 0.1f * Math.cos(theta * (second) - Math.PI / 2));
        y2 = (float) (radiusLong * 0.1f * Math.sin(theta * (second) - Math.PI / 2));
        secondLine = new Line2D.Float(x1, y1, -x2, -y2);
        g2.draw(secondLine);

        // draw circle cover three hands
        float worh = 16f;
        Ellipse2D.Float circle = new Ellipse2D.Float(0f - worh / 2f, 0f - worh / 2f, worh, worh);
        g2.draw(circle);

    }

    private void drawAxis(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.BLACK);
        g2.drawLine(-width / 2, 0, width / 2, 0);
        g2.drawLine(0, -height / 2, 0, height / 2);
        //unit=10,vertical line,x1,y1,x2,y2
        // short line, long line
        int sl = 5;
        int ll = 10;
        //x axis
        for (int i = 0; i <= width / 2; i += 10) {
            if (i % 50 == 0) {
                g2.drawLine(i, 0, i, -ll);
                g2.drawLine(-i, 0, -i, -ll);
            } else {
                g2.drawLine(i, 0, i, -sl);
                g2.drawLine(-i, 0, -i, -sl);
            }
        }
        //println(height / 2)
        //y axis
        for (int i = 0; i <= height / 2; i += 10) {
            if (i % 50 == 0) {
                g2.drawLine(0, i, ll, i);
                g2.drawLine(0, -i, ll, -i);
            } else {
                g2.drawLine(0, i, sl, i);
                g2.drawLine(0, -i, sl, -i);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250, 250);
    }

    private void setCurrentTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        repaint();
    }

    public void start() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                int second = calendar.get(Calendar.SECOND);
                setCurrentTime(hour % 12, minute, second);
            }
        });
        timer.start();
    }
}
