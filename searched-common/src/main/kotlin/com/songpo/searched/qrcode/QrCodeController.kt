package com.songpo.searched.qrcode

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.util.*
import javax.imageio.ImageIO

@Api(description = "二维码服务")
@CrossOrigin
@RestController
@RequestMapping("/api/common/v1/qrcode")
class QrCodeController(private val qrCodeService: QrCodeService) {

    val log = KotlinLogging.logger {}

    /**
     * 生成二维码
     *
     * @param content 内容
     * @param logo 图标
     * @param backgroundImage 背景图片，二维码背景图片
     * @param size 二维码大小，指定生成的二维码大小
     * @return 响应信息
     */
    @ApiOperation(value = "生成二维码", notes = "生成二维码")
    @ApiImplicitParams(value = [
        ApiImplicitParam(name = "content", value = "内容", paramType = "form", required = true),
        ApiImplicitParam(name = "width", value = "二维码宽度", paramType = "form"),
        ApiImplicitParam(name = "height", value = "二维码高度", paramType = "form"),
        ApiImplicitParam(name = "logo", value = "图标图片", paramType = "form", dataType = "file"),
        ApiImplicitParam(name = "back", value = "背景图片，二维码背景图片", paramType = "form", dataType = "file")
    ])
    @PostMapping
    fun create(content: String, logo: MultipartFile?, back: MultipartFile?, width: Int?, height: Int?): ResponseEntity<ByteArray> {
        try {
            if (content.isNotEmpty()) {
                var logoImage: BufferedImage? = null
                if (null != logo && !logo.isEmpty) {
                    logoImage = ImageIO.read(logo.inputStream)
                }
                var backImage: BufferedImage? = null
                if (null != back && !back.isEmpty) {
                    backImage = ImageIO.read(back.inputStream)
                }
                val bytes = this.qrCodeService.create(content, logoImage, backImage, width, height)
                if (null != bytes && bytes.isNotEmpty()) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_PNG)
                            .contentLength(bytes.size.toLong())
                            .header("Content-Disposition", "attachment; filename=${UUID.randomUUID()}.png")
                            .body(bytes)
                }
            } else {
                log.warn { "二维码内容不能为空" }
            }
        } catch (e: Exception) {
            log.error { "生成二维码失败，$e" }
        }
        return ResponseEntity.ok(ByteArray(0))
    }
}