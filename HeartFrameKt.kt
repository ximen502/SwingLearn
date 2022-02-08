import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

class HeartFrameKt : JFrame() {
    init {
        add(HeartPanel())
    }

    inner class HeartPanel : JPanel() {

        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            var g2: Graphics2D = g as Graphics2D
            g2.translate(width / 2, height / 2)
            drawAxis(g)
            //修线条粗细
            g2.stroke = BasicStroke(3.0f)
            g2.color = Color.RED
            var list = mutableListOf<OkPoint>()
            var i = 0.0
            val inc = Math.PI / 45
            while (i <= 2 * Math.PI) {
                var x = getX(20, i.toFloat())
                var y = getY(20, i.toFloat())
                var p = OkPoint(x, y)
                list.add(p)
                i += inc
            }

            for (i in list.indices) {
                if (i < list.size - 1) {
                    var p0 = list.get(i)
                    var p1 = list.get(i + 1)
                    g2.drawLine(p0.x.toInt(), p0.y.toInt(), p1.x.toInt(), p1.y.toInt())
                }
            }
        }

        fun getX(zoom: Int, theta: Float): Double {
            return zoom * (16 * Math.pow(Math.sin(theta.toDouble()), 3.0))
        }

        fun getY(zoom: Int, theta: Float): Double {
            return (-zoom
                    * (13 * Math.cos(theta.toDouble()) - 5 * Math.cos((2 * theta).toDouble()) - (2
                    * Math.cos((3 * theta).toDouble())) - Math.cos((4 * theta).toDouble())))
        }

        fun drawAxis(g: Graphics?) {
            var g2: Graphics2D = g as Graphics2D
            g2.stroke = BasicStroke(1.0f)
            g2.color = Color.BLACK
            g2.drawLine(-width / 2, 0, width / 2, 0)
            g2.drawLine(0, -height / 2, 0, height / 2)
            //unit=10,vertical line,x1,y1,x2,y2
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
    }

    inner class OkPoint(var x: Double, var y: Double)
}

fun main(args: Array<String>) {
    var frame = HeartFrameKt()
    frame.apply {
        setSize(1500, 800)
        title = "Kotlin heart"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}