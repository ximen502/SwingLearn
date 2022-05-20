import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.RoundRectangle2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

/**
 * 第0版模仿H5的模拟时钟
 * http://www.youhutong.com/index.php/yanshi/index/331.html
 */
class ClockFrameCaoGao : JFrame() {
    init {
        val clockPanel = ClockPanel()
        add(clockPanel)
        clockPanel.start()
    }

    inner class ClockPanel : JPanel() {
        val secondHandColor = Color(0xf3, 0xa8, 0x29)
        val minuteHandColor = Color(0x22, 0x22, 0x22)
        val hourHandColor = Color(0x22, 0x22, 0x22)
        val markColor = Color(0x22, 0x22, 0x22)
        var hour = 0
        var minute = 0
        var second = 0
        var end = 60

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            var g2: Graphics2D = g as Graphics2D
            g2.translate(width / 2, height / 2)
            drawAxis(g2)

            val theta = 2 * Math.PI / 60
            val radius = 150f
            val radiusLong = 164f
            val radiusEnd = 178
            g2.color = markColor
            var line: Line2D.Float
            val big = BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            val small = BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            /* 循环内的y值为负，则相当于坐标系变为了数学中的坐标系，y轴的正方向指向上方，而默认y轴的正方向指向下方，这时候刻度将会逆时针绘制
            * 如果y为正，则刻度会顺时针绘制，H5的代码是逆时针绘制的 */
            for (i in 0 until end) {
                var x1 = 0f
                var y1 = 0f
                var x2 = 0f
                var y2 = 0f
                if (i % 5 == 0) {
                    x1 = (Math.cos(theta * i) * radiusLong).toFloat()
                    y1 = (Math.sin(theta * i) * radiusLong).toFloat()
                    x2 = (Math.cos(theta * i) * radiusEnd).toFloat()
                    y2 = (Math.sin(theta * i) * radiusEnd).toFloat()
                    line = Line2D.Float(x1,y1,x2,y2)
                    g2.stroke = big
                } else {
                    x1 = (Math.cos(theta * i) * (radiusLong+5)).toFloat()
                    y1 = (Math.sin(theta * i) * (radiusLong+5)).toFloat()
                    x2 = (Math.cos(theta * i) * radiusEnd).toFloat()
                    y2 = (Math.sin(theta * i) * radiusEnd).toFloat()
                    line = Line2D.Float(x1,y1,x2,y2)
                    g2.stroke = small
                }
                g2.draw(line)
            }

//            var rect = RoundRectangle2D.Float(150f, -5f, 35f, 10f, 8f, 8f)
//            var rectSmall = RoundRectangle2D.Float(150f + 14f, -2.5f, 20f, 5f, 5f, 5f)

//            var angle = 0.0
//            var c = 0
//            while (angle < 2 * Math.PI) {
//                if (c % 5 == 0) {
//                    g2.fill(rect)
//                } else {
//                    g2.fill(rectSmall)
//                }
//                g2.rotate(theta)
//                angle += theta
//                c++
//            }

//            var numRadius = radius * 0.9f
//            //draw numbers
//            var numTheta = 2 * Math.PI / 12
//            for (i in 0..11) {
//                var x1 = Math.cos(numTheta * i - Math.PI / 3) * numRadius
//                var y1 = Math.sin(numTheta * i - Math.PI / 3) * numRadius
//                //println("x1:$x1, y1:$y1")
//                g2.drawString((i + 1).toString(), x1.toFloat(), y1.toFloat())
//            }
//
//
//            var x1 = 0f
//            var y1 = 0f
//            var x2 = 0f
//            var y2 = 0f
//
//            var hourTheta = 2 * Math.PI / 12
//            //draw hour hand
//            //修线条粗细
//            var basicStroke = BasicStroke(20.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
//            g2.stroke = basicStroke
//            g2.color = hourHandColor
//            x2 = radius * 0.75f * Math.cos(hourTheta * hour - Math.PI / 2).toFloat()
//            y2 = radius * 0.75f * Math.sin(hourTheta * hour - Math.PI / 2).toFloat()
//            var hourLine = Line2D.Float(x1, y1, x2, y2)
//            g2.draw(hourLine)
//
//            //draw minute hand
//            basicStroke = BasicStroke(9.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
//            g2.stroke = basicStroke
//            g2.color = minuteHandColor
//            x2 = radiusLong * 1.0f * Math.cos(theta * (minute) - Math.PI / 2).toFloat()
//            y2 = radiusLong * 1.0f * Math.sin(theta * (minute) - Math.PI / 2).toFloat()
//            var minuteLine = Line2D.Float(x1, y1, x2, y2)
//            g2.draw(minuteLine)
//
//            //draw second hand
//            basicStroke = BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
//            g2.stroke = basicStroke
//            g2.color = secondHandColor
//            x2 = radiusLong * 1.1f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
//            y2 = radiusLong * 1.1f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
//            var secondLine = Line2D.Float(x1, y1, x2, y2)
//            g2.draw(secondLine);
//
//            //draw second handle
//            basicStroke = BasicStroke(13.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
//            g2.stroke = basicStroke
//            g2.color = secondHandColor
//            x2 = radiusLong * 0.1f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
//            y2 = radiusLong * 0.1f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
//            secondLine = Line2D.Float(x1, y1, -x2, -y2)
//            g2.draw(secondLine);
//
//            // draw circle cover three hands
//            var worh = 16f
//            var circle = Ellipse2D.Float(0f - worh / 2f, 0f - worh / 2f, worh, worh)
//            g2.draw(circle)


//            var list = mutableListOf<OkPoint>()
//            var i = 0.0
//            val inc = Math.PI / 45 / 2
//            while (i <= 2 * Math.PI) {
//                var x = getX(20, i.toFloat())
//                var y = getY(20, i.toFloat())
//                var p = OkPoint(x, y)
//                list.add(p)
//                i += inc
//            }

            // start为起始点的角度，0代表x轴正方向，extent代表扫过的角度，正数为逆时针。
//            var arc = Arc2D.Float(30f, 30f, 100f, 100f, 0f, 120f, Arc2D.OPEN)
//            var arc2 = Arc2D.Float(-130f, -130f, 100f, 100f, 0f, 90f, Arc2D.CHORD)
//            var arc3 = Arc2D.Float(-130f, 130f, 100f, 100f, 0f, 45f, Arc2D.PIE)
//            g2.draw(arc)
//            g2.draw(arc2)
//            g2.draw(arc3)
//            g2.fill(arc)
//            g2.fill(arc2)
//            g2.fill(arc3)
        }

        fun drawAxis(g: Graphics?) {
            var g2: Graphics2D = g as Graphics2D
            g2.stroke = BasicStroke(1.0f)
            g2.color = Color.BLACK
            g2.drawLine(-width / 2, 0, width / 2, 0)
            g2.drawLine(0, -height / 2, 0, height / 2)
            //unit=10,vertical line,x1,y1,x2,y2
            // short line, long line
            val sl = 5
            val ll = 10
            //x axis
            for (i in 0..width / 2 step 10) {
                if (i % 50 == 0) {
                    g2.drawLine(i, 0, i, -ll)
                    g2.drawLine(-i, 0, -i, -ll)
                } else {
                    g2.drawLine(i, 0, i, -sl)
                    g2.drawLine(-i, 0, -i, -sl)
                }
            }
            //println(height / 2)
            //y axis
            for (i in 0..height / 2 step 10) {
                if (i % 50 == 0) {
                    g2.drawLine(0, i, ll, i)
                    g2.drawLine(0, -i, ll, -i)
                } else {
                    g2.drawLine(0, i, sl, i)
                    g2.drawLine(0, -i, sl, -i)
                }
            }
        }

        override fun getPreferredSize(): Dimension {
            return Dimension(250, 250)
        }

        private fun setCurrentTime(hour: Int, minute: Int, second: Int) {
            this.hour = hour
            this.minute = minute
            this.second = second
            //println("hour:${hour % 12}, minute:$minute, second:$second")
            repaint()
        }

        private fun setArcEnd(end:Int) {
            this.end = end
            repaint()
        }

        fun start() {
            var c = 0
            var timer = Timer(100) {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR_OF_DAY)
                var minute = calendar.get(Calendar.MINUTE)
                var second = calendar.get(Calendar.SECOND)
//                setCurrentTime(hour % 12, minute, second)
//                setCurrentTime(12, 0, 0)
                setArcEnd(c++%60)
            }
            timer.start()
        }
    }

}

fun main(args: Array<String>) {
    var frame = ClockFrameCaoGao()
    frame.apply {
        setSize(500, 450)
        title = "Kotlin clock"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

}