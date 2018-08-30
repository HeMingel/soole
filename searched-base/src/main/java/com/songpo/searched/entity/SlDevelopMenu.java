package com.songpo.searched.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sl_develop_menu")
public class SlDevelopMenu implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 栏目名称
     */
    private String classname;

    /**
     * 栏目标识
     */
    private String classvar;

    /**
     * 上级栏目标识
     */
    private String parent;

    /**
     * 顶级栏目标识
     */
    private String top;

    /**
     * 图标
     */
    private String icon;

    /**
     * 默认导航面板
     */
    private Boolean isdefault;

    /**
     * 自定义排序
     */
    private Short myorder;

    /**
     * 是否显示
     */
    private Boolean isshow;

    /**
     * 附加参数
     */
    private String params;

    /**
     * 是否关闭
     */
    private Boolean isclose;

    /**
     * 追加方法
     */
    private String pattern;

    /**
     * 定义上级
     */
    @Column(name = "higher_up")
    private String higherUp;

    /**
     * 没有子栏目
     */
    @Column(name = "no_child")
    private Boolean noChild;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 最后更新时间
     */
    @Column(name = "updated_at")
    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取栏目名称
     *
     * @return classname - 栏目名称
     */
    public String getClassname() {
        return classname;
    }

    /**
     * 设置栏目名称
     *
     * @param classname 栏目名称
     */
    public void setClassname(String classname) {
        this.classname = classname == null ? null : classname.trim();
    }

    /**
     * 获取栏目标识
     *
     * @return classvar - 栏目标识
     */
    public String getClassvar() {
        return classvar;
    }

    /**
     * 设置栏目标识
     *
     * @param classvar 栏目标识
     */
    public void setClassvar(String classvar) {
        this.classvar = classvar == null ? null : classvar.trim();
    }

    /**
     * 获取上级栏目标识
     *
     * @return parent - 上级栏目标识
     */
    public String getParent() {
        return parent;
    }

    /**
     * 设置上级栏目标识
     *
     * @param parent 上级栏目标识
     */
    public void setParent(String parent) {
        this.parent = parent == null ? null : parent.trim();
    }

    /**
     * 获取顶级栏目标识
     *
     * @return top - 顶级栏目标识
     */
    public String getTop() {
        return top;
    }

    /**
     * 设置顶级栏目标识
     *
     * @param top 顶级栏目标识
     */
    public void setTop(String top) {
        this.top = top == null ? null : top.trim();
    }

    /**
     * 获取图标
     *
     * @return icon - 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置图标
     *
     * @param icon 图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取默认导航面板
     *
     * @return isdefault - 默认导航面板
     */
    public Boolean getIsdefault() {
        return isdefault;
    }

    /**
     * 设置默认导航面板
     *
     * @param isdefault 默认导航面板
     */
    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    /**
     * 获取自定义排序
     *
     * @return myorder - 自定义排序
     */
    public Short getMyorder() {
        return myorder;
    }

    /**
     * 设置自定义排序
     *
     * @param myorder 自定义排序
     */
    public void setMyorder(Short myorder) {
        this.myorder = myorder;
    }

    /**
     * 获取是否显示
     *
     * @return isshow - 是否显示
     */
    public Boolean getIsshow() {
        return isshow;
    }

    /**
     * 设置是否显示
     *
     * @param isshow 是否显示
     */
    public void setIsshow(Boolean isshow) {
        this.isshow = isshow;
    }

    /**
     * 获取附加参数
     *
     * @return params - 附加参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置附加参数
     *
     * @param params 附加参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 获取是否关闭
     *
     * @return isclose - 是否关闭
     */
    public Boolean getIsclose() {
        return isclose;
    }

    /**
     * 设置是否关闭
     *
     * @param isclose 是否关闭
     */
    public void setIsclose(Boolean isclose) {
        this.isclose = isclose;
    }

    /**
     * 获取追加方法
     *
     * @return pattern - 追加方法
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * 设置追加方法
     *
     * @param pattern 追加方法
     */
    public void setPattern(String pattern) {
        this.pattern = pattern == null ? null : pattern.trim();
    }

    /**
     * 获取定义上级
     *
     * @return higher_up - 定义上级
     */
    public String getHigherUp() {
        return higherUp;
    }

    /**
     * 设置定义上级
     *
     * @param higherUp 定义上级
     */
    public void setHigherUp(String higherUp) {
        this.higherUp = higherUp == null ? null : higherUp.trim();
    }

    /**
     * 获取没有子栏目
     *
     * @return no_child - 没有子栏目
     */
    public Boolean getNoChild() {
        return noChild;
    }

    /**
     * 设置没有子栏目
     *
     * @param noChild 没有子栏目
     */
    public void setNoChild(Boolean noChild) {
        this.noChild = noChild;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return updated_at - 最后更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updatedAt 最后更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", classname=").append(classname);
        sb.append(", classvar=").append(classvar);
        sb.append(", parent=").append(parent);
        sb.append(", top=").append(top);
        sb.append(", icon=").append(icon);
        sb.append(", isdefault=").append(isdefault);
        sb.append(", myorder=").append(myorder);
        sb.append(", isshow=").append(isshow);
        sb.append(", params=").append(params);
        sb.append(", isclose=").append(isclose);
        sb.append(", pattern=").append(pattern);
        sb.append(", higherUp=").append(higherUp);
        sb.append(", noChild=").append(noChild);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}