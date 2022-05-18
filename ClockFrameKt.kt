import java.awt.*
import java.awt.event.ActionListener
import java.awt.geom.Arc2D
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.awt.geom.RoundRectangle2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

class ClockFrameKt : JFrame() {
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

            var radius = 150f
            var radiusSmall = 164f

            g2.color = markColor
            var rect = RoundRectangle2D.Float(150f, -5f, 35f, 10f, 8f, 8f)
            var rectSmall = RoundRectangle2D.Float(150f + 14f, -2.5f, 20f, 5f, 5f, 5f)

            val theta = 2 * Math.PI / 60

            var angle = 0.0
            var c = 0
            while (angle < 2 * Math.PI) {
                if (c % 5 == 0) {
                    g2.fill(rect)
                } else {
                    g2.fill(rectSmall)
                }
                g2.rotate(theta)
                angle += theta
                c++
            }

            var numRadius = radius * 0.9f
            //draw numbers
            var nums = intArrayOf(3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2)
            var numTheta = 2 * Math.PI / 12
            for (i in 0..11) {
                var x1 = Math.cos(numTheta * i) * numRadius
                var y1 = Math.sin(numTheta * i) * numRadius
                //println("x1:$x1, y1:$y1")
                g2.rotate(numTheta * -0.2)
                g2.drawString(nums[i].toString(), x1.toFloat(), y1.toFloat())
                g2.rotate(numTheta * 0.2)
            }


            var x1 = 0f
            var y1 = 0f
            var x2 = 0f
            var y2 = 0f

            //draw hour hand
            //修线条粗细
            var basicStroke = BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = hourHandColor
            var hTheta = 2 * Math.PI / 12
            x2 = (radiusSmall * 1 * Math.cos(hTheta * (hour)/* - Math.PI / 2*/)).toFloat()
            y2 = (radiusSmall * 1 * Math.sin(hTheta * (hour)/* - Math.PI / 2*/)).toFloat()
            var hourLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(hourLine)
            println("h x2:$x2, y2:$y2")

            //draw minute hand
            basicStroke = BasicStroke(9.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = minuteHandColor
            x2 = radiusSmall * 1.0f * Math.cos(theta * (minute - 1) - Math.PI / 2).toFloat()
            y2 = radiusSmall * 1.0f * Math.sin(theta * (minute - 1) - Math.PI / 2).toFloat()
            var minuteLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(minuteLine)
            println("m x2:$x2, y2:$y2")

            //draw second hand
            basicStroke = BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = (radiusSmall * 1.1 * Math.cos(theta * (second - 1) - Math.PI / 2)).toFloat()
            y2 = (radiusSmall * 1.1 * Math.sin(theta * (second - 1) - Math.PI / 2)).toFloat()
            var secondLine = Line2D.Float(x1, y1, x2, y2)
            g2.draw(secondLine);
            println("s x2:$x2, y2:$y2")

            //draw second handle
            basicStroke = BasicStroke(13.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            g2.stroke = basicStroke
            g2.color = secondHandColor
            x2 = radiusSmall * 0.1f * Math.cos(theta * (second - 1) - Math.PI / 2).toFloat()
            y2 = radiusSmall * 0.1f * Math.sin(theta * (second - 1) - Math.PI / 2).toFloat()
            secondLine = Line2D.Float(x1, y1, -x2, -y2)
            g2.draw(secondLine);

            // draw circle cover three hands
            var worh = 16f
            var circle = Ellipse2D.Float(0f - worh / 2f, 0f - worh / 2f, worh, worh)
            g2.draw(circle)


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

        override fun getPreferredSize(): Dimension {
            return Dimension(250, 250)
        }

        private fun setCurrentTime(hour: Int, minute: Int, second: Int) {
            this.hour = hour
            this.minute = minute
            this.second = second
            println("hour:${hour % 12}, minute:$minute, second:$second")
            repaint()
        }

        fun start() {
            var timer = Timer(1000) {
                var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                var minute = Calendar.getInstance().get(Calendar.MINUTE)
                var second = Calendar.getInstance().get(Calendar.SECOND)
//                setCurrentTime(hour % 12, minute, second)
                setCurrentTime(12, 0, 0)
            }
            timer.start()
        }
    }

}

fun main(args: Array<String>) {
    var frame = ClockFrameKt()
    frame.apply {
        setSize(1500, 800)
        title = "Kotlin clock"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }

}