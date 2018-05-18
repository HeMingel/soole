package com.songpo.searched.service;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.songpo.searched.domain.BusinessMessage;
import com.songpo.searched.entity.*;
import com.songpo.searched.mapper.CmProductCommentMapper;
import com.songpo.searched.mapper.SlSystemConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CmProductCommentService {

    public static final Logger log = LoggerFactory.getLogger(CmProductCommentService.class);
    private static final String BASE_FILE_DOMAIN = "BASE_FILE_DOMAIN";
    private static List<String> IMAGE_TYPE_ARRAY = Arrays.asList("image/bmp", "image/jpg", "image/jpeg", "image/png");
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCommentService productCommentService;
    @Autowired
    private ProductCommentImageService productCommentImageService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private FileService fileService;

    @Autowired
    private CmProductCommentMapper mapper;
    @Autowired
    private SlSystemConfigMapper configMapper;

    /**
     * 查询商品评论
     *
     * @param goodsId 商品Id
     * @param status  好中差 有图 状态
     * @param page    页码
     * @param size    容量
     * @return 商品评论
     */
    public BusinessMessage findGoodsCommentsByGoodsId(String goodsId, Integer status, Integer page, Integer size) {
        log.debug("查询 商品Id:{},评论好中差:{},页数:{},条数:{}", goodsId, status, page, size);
        BusinessMessage<PageInfo> businessMessage = new BusinessMessage<>();
        businessMessage.setSuccess(false);
        try {
            SlSystemConfig baseDomainConfig = this.configMapper.selectOne(new SlSystemConfig() {{
                setName(BASE_FILE_DOMAIN);
            }});
            String domain = "";
            if (null != baseDomainConfig && !org.springframework.util.StringUtils.isEmpty(baseDomainConfig.getContent())) {
                domain = baseDomainConfig.getContent();
                if (!domain.endsWith("/")) {
                    domain = domain + "/";
                }
            }
            PageHelper.startPage(page == null || page == 0 ? 1 : page, size == null ? 10 : size);
            List<Map<String, Object>> mapComments = this.mapper.goodsComments(goodsId, status);
            if (mapComments.size() > 0) {
                List<Object> list = new ArrayList<>();
                for (Map<String, Object> mapComment : mapComments) {
                    //如果有图片
                    if (mapComment.get("isImage") != null && mapComment.get("isImage").equals(1)) {
                        //查询图片
                        List<Map<String, Object>> commentImages = this.mapper.commentImages(mapComment.get("id").toString());
                        if (commentImages != null && commentImages.size() > 0) {
                            for (Map<String, Object> map : commentImages) {
                                if (map.get("image_url") != null && StringUtils.isNotBlank(map.get("image_url").toString())) {
                                    map.put("image_url", domain + map.get("image_url"));
                                }
                            }
                        }
                        mapComment.put("image", commentImages);
                    }
                    list.add(mapComment);
                }
                businessMessage.setData(new PageInfo<>(list));
                businessMessage.setMsg("查询成功");
                businessMessage.setSuccess(true);
            } else {
                businessMessage.setMsg("查询无数据!");
            }
        } catch (Exception e) {
            businessMessage.setMsg("商品评论查询异常");
            log.error("查询商品评论异常", e);
        }

        return businessMessage;
    }

    /**
     * 新增商品评论
     *
     * @param orderDetailId 评论的订单明细ID
     * @param content       评论内容
     * @param status        评论状态:1好2中3差
     * @param imageList     评论图片
     */
    public BusinessMessage insertGoodsComments(String orderDetailId, String content, Integer status, List<MultipartFile> imageList) {
        log.debug("评论 订单明细ID:{},评论好中差:{},图片数量:{}", orderDetailId, status, imageList.size());
        BusinessMessage businessMessage = new BusinessMessage();
        SlUser slUser = loginUserService.getCurrentLoginUser();
        if (slUser == null || StringUtils.isBlank(slUser.getId())) {
            log.debug("获取当前登录用户信息失败");
            businessMessage.setMsg("获取当前登录用户信息失败");
            return businessMessage;
        }
        SlOrderDetail orderDetail = this.orderDetailService.selectByPrimaryKey(orderDetailId);
        if (orderDetail == null || StringUtils.isBlank(orderDetail.getId())) {
            log.debug("要评论的订单不存在");
            businessMessage.setMsg("要评论的订单不存在");
            return businessMessage;
        }
        if (orderDetail.getShippingState() < 5) {
            log.debug("订单未完成，无法评价");
            businessMessage.setMsg("订单未完成，无法评价");
            return businessMessage;
        } else if (orderDetail.getShippingState() == 6) {
            log.debug("订单已评价，无需继续评价");
            businessMessage.setMsg("订单已评价，无需继续评价");
            return businessMessage;
        } else if (orderDetail.getShippingState() == 7) {
            log.debug("订单已申请售后，无法评价");
            businessMessage.setMsg("订单已申请售后，无法评价");
            return businessMessage;
        }
        if (StringUtils.isBlank(orderDetail.getProductId())) {
            log.debug("要评论的订单信息错误，找不到商品ID");
            businessMessage.setMsg("要评论的订单信息错误，找不到商品ID");
            return businessMessage;
        }

        SlProduct product = this.productService.selectByPrimaryKey(orderDetail.getProductId());
        /****** 商品存在，继续评论，商品不存在也不需要报错 *****/
        if (product != null && StringUtils.isNotBlank(product.getId())) {
            /******************* 处理评论 *************************/
            SlProductComment productComment = new SlProductComment();
            // 评论id
            productComment.setId(UUID.randomUUID().toString());
            productComment.setProductId(orderDetail.getProductId());
            productComment.setCommentatorId(slUser.getId());
            productComment.setContent(content);
            productComment.setStatus(status);
            productComment.setIsimage(0);
            if (imageList != null && imageList.size() > 0) {
                productComment.setIsimage(1);
            }
            productComment.setCreator(slUser.getUsername().toString());
            productComment.setModifier(productComment.getCreator());
            productComment.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            productComment.setModificationTime(productComment.getCreateTime());

            /*********************** 处理评论图片 ****************************/
            if (imageList != null && imageList.size() > 0) {
                SlProductCommentImage commentImage;
                for (MultipartFile file : imageList) {
                    JSONObject jsonObject = fileService.newUpload(null, file);
                    if (jsonObject != null && StringUtils.isNotBlank(jsonObject.getString("fileName"))) {
                        commentImage = new SlProductCommentImage();
                        commentImage.setId(UUID.randomUUID().toString());
                        commentImage.setProductCommentId(productComment.getId());
                        commentImage.setImageUrl(jsonObject.getString("fileName"));

                        productCommentImageService.insertSelective(commentImage);
                    }
                }
            }
            //评论入库
            productCommentService.insertSelective(productComment);
        }
        //更新商品评论数量
        product.setCommentNum(product.getCommentNum() + 1);
        product.setCreatedAt(null);
        product.setUpdatedAt(null);
        productService.updateByPrimaryKeySelective(product);
        //更新订单状态
        orderDetail.setShippingState(6);
        orderDetail.setCreatedAt(null);
        orderDetail.setUpdatedAt(null);
        orderDetailService.updateByPrimaryKeySelective(orderDetail);

        businessMessage.setMsg("评论成功");
        businessMessage.setSuccess(true);
        return businessMessage;
    }
}
