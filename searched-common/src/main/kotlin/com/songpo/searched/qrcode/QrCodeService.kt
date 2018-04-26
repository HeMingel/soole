package com.songpo.searched.qrcode

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import mu.KotlinLogging
import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

/**
 * 二维码生成服务
 */
@Service
class QrCodeService {

    val log = KotlinLogging.logger {}

    /**
     * 默认格式：二维码
     */
    val format: BarcodeFormat = BarcodeFormat.QR_CODE

    /**
     * 默认宽度
     */
    val defaultWidth: Int = 400

    /**
     * 默认高度
     */
    val defaultHeight: Int = 400

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param logoImage 图标
     * @param backgroundImage 背景图片，二维码背景图片
     * @param width 二维码宽度
     * @param height 二维码高度
     * @return 响应信息
     */
    fun create(content: String, logoImage: BufferedImage?, backgroundImage: BufferedImage?, width: Int?, height: Int?): ByteArray? {
        // 初始化参数
        val hints = mapOf(
                Pair(EncodeHintType.CHARACTER_SET, "UTF-8"),
                Pair(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H),
                Pair(EncodeHintType.MARGIN, 1)
        )

        // 初始化bitMatrix
        val bitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width ?: defaultWidth, height
                ?: defaultHeight, hints)

        // 获取二维码宽度
        val tempWidth = bitMatrix.width

        // 获取二维码高度
        val tempHeight = bitMatrix.height

        // 初始化图片
        var qrCodeImage = BufferedImage(tempWidth, tempHeight, BufferedImage.TYPE_INT_RGB)

        // 循环画出二维码
        for (x in 0 until tempWidth) {
            for (y in 0 until tempHeight) {
                qrCodeImage.setRGB(x, y, if (bitMatrix.get(x, y)) Color.BLACK.rgb else Color.WHITE.rgb)
            }
        }

        if (null != logoImage) {
            qrCodeImage = addLogo(qrCodeImage, logoImage)
        }
        val baos = ByteArrayOutputStream()

        ImageIO.write(qrCodeImage, "PNG", baos)

//        ImageIO.write(qrCodeImage, "PNG", FileOutputStream("test.png"))
        return baos.toByteArray()
    }

    fun addLogo(qrCodeImage: BufferedImage, logoImage: BufferedImage): BufferedImage {
        try {
            val graphics = qrCodeImage.createGraphics()

            // 二维码
            val qrCodeX = qrCodeImage.width / 2
            val qrCodeY = qrCodeImage.height / 2

            val tmpLogo = Thumbnails.of(logoImage).size(qrCodeImage.width / 4, qrCodeImage.height / 4).asBufferedImage()

            val logoX = tmpLogo.width
            val logoY = tmpLogo.height

            graphics.drawImage(tmpLogo, qrCodeX - logoX / 2, qrCodeY - logoY / 2, logoX, logoY, null)

            // logo内框位置
//            graphics.drawRoundRect(qrCodeX - logoX / 2, qrCodeY - logoY / 2, logoX, logoY, 1, 1)
//            val sharp = RoundRectangle2D.Float(qrCodeX.toFloat() - logoX / 2, qrCodeY.toFloat() - logoY / 2, logoX.toFloat(), logoY.toFloat(), 6f, 6f)
//            graphics.draw(sharp)

            // logo边框宽度
//            graphics.stroke = BasicStroke(1f)
            // logo边框颜色
//            graphics.color = Color.PINK
            // 图片边框位置
//            graphics.drawRect(qrCodeX - logoX / 2, qrCodeY - logoY / 2, logoX, logoY)

            graphics.dispose()

            logoImage.flush()

            qrCodeImage.flush()

        } catch (e: Exception) {
            log.error { "添加Logo失败，$e" }
        }
        return qrCodeImage
    }

}
