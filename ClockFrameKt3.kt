import java.awt.*
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

/**
 * 第三版高仿H5模拟时钟部分代码优化
 */
class ClockFrameKt3 : JFrame() {
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

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            var g2: Graphics2D = g as Graphics2D
            g2.translate(width / 2, height / 2)
            //drawAxis(g2)

            val theta = 2 * Math.PI / 60
            val radius = 150f
            val radiusLong = 164f
            val radiusEnd = 178
            g2.color = markColor
            var line: Line2D.Float
            val big = BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            val small = BasicStroke(5.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            for (i in 0 until 60) {
                var x1 = 0f
                var y1 = 0f
                var x2 = 0f
                var y2 = 0f
                val cost = Math.cos(theta * i)
                val sint = Math.sin(theta * i)
                // hour marker
                if (i % 5 == 0) {
                    x1 = (cost * radiusEnd * 0.90).toFloat()
                    y1 = (sint * radiusEnd * 0.90).toFloat()
                    x2 = (cost * radiusEnd).toFloat()
                    y2 = (sint * radiusEnd).toFloat()
                    line = Line2D.Float(x1,y1,x2,y2)
                    g2.stroke = big
                } else {
                    // minute marker
                    x1 = (cost * radiusEnd * 0.95).toFloat()
                    y1 = (sint * radiusEnd * 0.95).toFloat()
                    x2 = (cost * radiusEnd).toFloat()
                    y2 = (sint * radiusEnd).toFloat()
                    line = Line2D.Float(x1,y1,x2,y2)
                    g2.stroke = small
                }
                g2.draw(line)
            }

            var numRadius = radius * 0.9f
            g2.font = Font("", Font.PLAIN, 22)
            var fontMetrics = getFontMetrics(g2.font)
            //draw numbers
            var numTheta = 2 * Math.PI / 12
            for (i in 0..11) {
                var x1 = Math.cos(numTheta * i - Math.PI / 3) * numRadius
                var y1 = Math.sin(numTheta * i - Math.PI / 3) * numRadius
                //println("x1:$x1, y1:$y1")
                // 测量字符串宽度
                var num = (i + 1).toString()
                var strW = fontMetrics.stringWidth(num)
                g2.drawString(num, (x1 - strW / 2).toFloat(), y1.toFloat())
            }

            var font = Font("",Font.PLAIN, 14)
            var fontSmall = Font("",Font.PLAIN, 10)
            val brand = "北极星"
            val brandPlace = "亚洲"
            fontMetrics = getFontMetrics(font)
            var bW = fontMetrics.stringWidth(brand)
            g2.font = font
            g2.drawString(brand, 0 - bW / 2, radius.toInt() / 2)
            fontMetrics = getFontMetrics(fontSmall)
            var bpW = fontMetrics.stringWidth(brandPlace)
            g2.font = fontSmall
            g2.drawString(brandPlace, 0 - bpW / 2, radius.toInt() / 2 + 15)


            var x1 = 0f
            var y1 = 0f
            var x2 = 0f
            var y2 = 0f

            var hourTheta = 2 * Math.PI / 12
            //draw hour hand
            //修线条粗细
            var basicStroke = BasicStroke(20.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = hourHandColor
            /* hourTheta * (minute / 60f)分针变化对时针的影响 2pi/12*(minute/60) */
            x2 = radius * 0.75f * Math.cos(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2).toFloat()
            y2 = radius * 0.75f * Math.sin(hourTheta * hour + hourTheta * (minute / 60f) - Math.PI / 2).toFloat()
            var hourLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(hourLine)

            //println("second:$second, ${theta * (second / 60f)}")

            //draw minute hand
            basicStroke = BasicStroke(9.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = minuteHandColor
            /* theta * (second / 60f)秒针变化对分针的影响 //2pi/60 * (second/60)*/
            x2 = radiusLong * 1.0f * Math.cos(theta * (minute) + theta * (second / 60f) - Math.PI / 2).toFloat()
            y2 = radiusLong * 1.0f * Math.sin(theta * (minute) + theta * (second / 60f) - Math.PI / 2).toFloat()
            var minuteLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(minuteLine)

            //draw second hand
            basicStroke = BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = radiusLong * 1.08f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
            y2 = radiusLong * 1.08f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
            var secondLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(secondLine);

            //draw second tail handle
            basicStroke = BasicStroke(13.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = radiusLong * 0.1f * Math.cos(theta * (second) - Math.PI / 2).toFloat()
            y2 = radiusLong * 0.1f * Math.sin(theta * (second) - Math.PI / 2).toFloat()
            secondLine = Line2D.Float(x1, y1, -x2, -y2)
            g2.draw(secondLine);

            // draw circle cover three hands
            var worh = 16f
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

    //转弧度
    fun toRadians(deg: Float): Float {
        return ((Math.PI / 180) * deg).toFloat();
    }

}

fun main(args: Array<String>) {
    var frame = ClockFrameKt3()
    frame.apply {
        setSize(500, 450)
        title = "Kotlin clock"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

}