import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

/**
 * 仿led屏的模拟时钟
 */
class ClockFrameKt4 : JFrame() {
    init {
        val clockPanel = ClockPanel()
        clockPanel.background = Color(0x29, 0x24, 0x21)
        add(clockPanel)
        clockPanel.start()
    }

    inner class ClockPanel : JPanel() {
        val secondHandColor = Color.GREEN
        val minuteHandColor = Color.RED
        val hourHandColor = Color.YELLOW
        val markColor = Color(0x22, 0x22, 0x22)
        val markBigColor = Color.RED
        val markSmallColor = Color.GREEN
        var hour = 0
        var minute = 0
        var second = 0

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            var g2: Graphics2D = g as Graphics2D
            g2.translate(width / 2, height / 2)
//            drawAxis(g2)

            g2.color = markColor
            g2.drawString("北极星", -20, 50)

            val theta = 2 * Math.PI / 60
            val radius = 150f
            val radiusLong = 164f
            val radiusEnd = 178
            g2.color = markColor
//            var line: Line2D.Float
//            val big = BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
//            val small = BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            var ellipse: Ellipse2D
            val size = 6f
            val sizeBig = 12f
            for (i in 0 until 60) {
                var x1 = 0f
                var y1 = 0f
                var x2 = 0f
                var y2 = 0f
                if (i % 5 == 0) {
                    x1 = (Math.cos(theta * i) * radiusLong).toFloat()
                    y1 = (Math.sin(theta * i) * radiusLong).toFloat()
                    x2 = (Math.cos(theta * i) * radiusEnd - sizeBig / 2).toFloat()
                    y2 = (Math.sin(theta * i) * radiusEnd - sizeBig / 2).toFloat()
                    //line = Line2D.Float(x1,y1,x2,y2)
                    ellipse = Ellipse2D.Float(x2, y2, sizeBig, sizeBig)
                    //g2.stroke = big
                    g2.color = markBigColor
                } else {
                    x1 = (Math.cos(theta * i) * (radiusLong)).toFloat()
                    y1 = (Math.sin(theta * i) * (radiusLong)).toFloat()
                    x2 = (Math.cos(theta * i) * radiusEnd - size / 2).toFloat()
                    y2 = (Math.sin(theta * i) * radiusEnd - size / 2).toFloat()
                    //line = Line2D.Float(x1,y1,x2,y2)
                    //g2.stroke = small
                    ellipse = Ellipse2D.Float(x2, y2, size, size)
                    g2.color = markSmallColor
                }
                //g2.draw(line)
                g2.fill(ellipse)
            }

//            var numRadius = radius * 0.9f
//            g2.font = Font("", Font.PLAIN, 22)
//            //draw numbers
//            var numTheta = 2 * Math.PI / 12
//            for (i in 0..11) {
//                var x1 = Math.cos(numTheta * i - Math.PI / 3) * numRadius
//                var y1 = Math.sin(numTheta * i - Math.PI / 3) * numRadius
//                //println("x1:$x1, y1:$y1")
//                g2.drawString((i + 1).toString(), x1.toFloat(), y1.toFloat())
//            }


            var x1 = 0f
            var y1 = 0f
            var x2 = 0f
            var y2 = 0f

            var hourTheta = 2 * Math.PI / 12
            //draw hour hand
            //修线条粗细
            var basicStroke = BasicStroke(15.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = hourHandColor
            /* hourTheta * (minute / 60f)分针变化对时针的影响 2pi/12*(minute/60) */
            x2 = radius * 0.75f * Math.cos(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2).toFloat()
            y2 = radius * 0.75f * Math.sin(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2).toFloat()
            var hourLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(hourLine)

            //println("second:$second, ${theta * (second / 60f)}")

            //draw minute hand
            basicStroke = BasicStroke(5.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = minuteHandColor
            /* theta * (second / 60f)秒针变化对分针的影响 //2pi/60 * (second/60)*/
            x2 = radiusLong * 1.0f * Math.cos(theta * (minute) + theta * (second / 60f) - Math.PI / 2).toFloat()
            y2 = radiusLong * 1.0f * Math.sin(theta * (minute) + theta * (second / 60f) - Math.PI / 2).toFloat()
            var minuteLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(minuteLine)

            //draw second hand
            basicStroke = BasicStroke(2.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = radiusLong * 1.1f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
            y2 = radiusLong * 1.1f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
            var secondLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(secondLine);

            //draw second handle
            basicStroke = BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = radiusLong * 0.2f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
            y2 = radiusLong * 0.2f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
            secondLine = Line2D.Float(x1, y1, -x2, -y2)
            g2.draw(secondLine);

            // draw circle cover three hands
            var worh = 8f
            var circle = Ellipse2D.Float(0f - worh / 2f, 0f - worh / 2f, worh, worh)
            g2.draw(circle)

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
            repaint()
        }

        fun start() {
            var c = 0
            var timer = Timer(1000) {
                val calendar = Calendar.getInstance()
                var hour = calendar.get(Calendar.HOUR_OF_DAY)
                var minute = calendar.get(Calendar.MINUTE)
                var second = calendar.get(Calendar.SECOND)
                setCurrentTime(hour % 12, minute, second)
            }
            timer.start()
        }
    }

}

fun main(args: Array<String>) {
    var frame = ClockFrameKt4()
    frame.apply {
        setSize(500, 450)
        title = "Kotlin clock"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

}