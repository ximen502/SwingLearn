import java.awt.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
//从中间上部分别顺时针逆时针绘制的心形
class HeartFrameKt3 : JFrame() {
    init {
        val panel = HeartPanel()
        val jButton = JButton("refresh")
        add(panel, BorderLayout.CENTER)
        add(jButton, BorderLayout.SOUTH)
        var thread = Thread {
            while (true) {
                var refresh = true
                EventQueue.invokeLater {
                    refresh = panel.refresh()
                }
                Thread.sleep(25)
                if (!refresh) {
                    //println("exit")
                    //break;
                } else {
                    //println("go on")
                }
            }
        }
        thread.start()
        jButton.addActionListener {
            panel.clear()
        }
    }

    inner class HeartPanel : JPanel() {
        var flower:BufferedImage = ImageIO.read(File("flower.png"))

        var i = 0.0
        var j = 2 * Math.PI
        var listRight = mutableListOf<OkPoint>()
        var listLeft = mutableListOf<OkPoint>()
        // 单纯画线的话，除90比较好，如果画花的话最好用45这样花不会过密，显示比较完整
        val INC = Math.PI / 90
        override fun paintComponent(g: Graphics?) {
            super.paintComponent(g)
            var g2: Graphics2D = g as Graphics2D
            g2.translate(width / 2, height / 2)
            drawAxis(g)
            //修线条粗细
            g2.stroke = BasicStroke(3.0f)
            g2.color = Color.RED

            if (i <= Math.PI) {
                var x = getX(20, i.toFloat())
                var y = getY(20, i.toFloat())
                var p = OkPoint(x, y)
                listRight.add(p)
                i += INC
            }

            if (j > Math.PI) {
                var x = getX(20, j.toFloat())
                var y = getY(20, j.toFloat())
                var p = OkPoint(x, y)
                listLeft.add(p)
                j -= INC
            }

            for (i in listRight.indices) {
                if (i < listRight.size - 1) {
                    var p0 = listRight.get(i)
                    var p1 = listRight.get(i + 1)
                    g2.drawLine(p0.x.toInt(), p0.y.toInt(), p1.x.toInt(), p1.y.toInt())
                    val flowerX = p0.x.toInt() - flower.width / 2
                    val flowerY = p0.y.toInt() - flower.height / 2
                    g2.drawImage(flower, flowerX, flowerY, null)
                }
            }

            for (i in listLeft.indices) {
                if (i < listLeft.size - 1) {
                    var p0 = listLeft.get(i)
                    var p1 = listLeft.get(i + 1)
                    g2.drawLine(p0.x.toInt(), p0.y.toInt(), p1.x.toInt(), p1.y.toInt())
                    val flowerX = p0.x.toInt() - flower.width / 2
                    val flowerY = p0.y.toInt() - flower.height / 2
                    g2.drawImage(flower, flowerX, flowerY, null)
                }
            }

            g2.translate(-width / 2, -height / 2)
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

        fun refresh():Boolean {
            return if (j > Math.PI) {
                repaint()
                true
            } else {
                false
            }
        }

        fun clear() {
            i = 0.0
            j = 2 * Math.PI
            listRight.clear()
            listLeft.clear()
        }
    }

    inner class OkPoint(var x: Double, var y: Double)
}

fun main(args: Array<String>) {
    var frame = HeartFrameKt3()
    frame.apply {
        setSize(1500, 800)
        title = "Kotlin heart"
        setLocationRelativeTo(null) // Center the frame

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
    }
}