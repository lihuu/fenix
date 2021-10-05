package com.blinkfox.fenix.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * XPATH 语法相关的常量类.
 *
 * @author blinkfox on 2019-08-04.
 * @since v1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XpathConst {

    /**
     * FENIX XML 标签中的根节点名称.
     */
    public static final String FENIX_ROOT_NAME = "fenixs";

    /**
     * 查找 FENIX 标签的 XPATH 常量.
     */
    public static final String FENIX_TAG = "fenixs/fenix";

    /**
     * 查找带有 xml xmlns 标签的 XPATH 常量.
     */
    public static final String FENIX_TAG_WITH_NAMESPACE = "//namespace:fenixs/namespace:fenix";

    /**
     * 查找 XML 标签的命名空间常量.
     */
    public static final String ATTR_NAMESPACE = "attribute::namespace";

    /**
     * 查找标签子节点的 XPATH 常量.
     */
    public static final String ATTR_CHILD = "child::node()";

    /**
     * 用于表示 “ID” 的属性 XPATH 常量.
     */
    public static final String ATTR_ID = "attribute::id";

    /**
     * 用于表示 {@code resultType} 的属性 XPATH 常量.
     *
     * @since v1.1.0
     */
    public static final String ATTR_RESULT_TYPE = "attribute::resultType";

    /**
     * 用于表示 “removeIfExist” 的属性 XPATH 常量.
     */
    public static final String ATTR_REMOVE = "attribute::removeIfExist";

    /**
     * 用于表示 “匹配” 的属性 XPATH 常量.
     */
    public static final String ATTR_MATCH = "attribute::match";

    /**
     * 用于表示 “field” 的属性 XPATH 常量.
     */
    public static final String ATTR_FIELD = "attribute::field";

    /**
     * 用于表示 “name” 的属性 XPATH 常量，用于表示 JPA 命名参数时的自定义名称的属性.
     *
     * @since v2.3.0 on 2020-05-06
     */
    public static final String ATTR_NAME = "attribute::name";

    /**
     * 用于表示开始边界的 “name” 的属性 XPATH 常量，用于表示 JPA 命名参数时的自定义名称的属性.
     *
     * @since v2.3.0 on 2020-05-06
     */
    public static final String ATTR_START_NAME = "attribute::startName";

    /**
     * 用于表示结束边界的 “name” 的属性 XPATH 常量，用于表示 JPA 命名参数时的自定义名称的属性.
     *
     * @since v2.3.0 on 2020-05-06
     */
    public static final String ATTR_END_NAME = "attribute::endName";

    /**
     * 用于表示 “value” 的属性 XPATH 常量.
     */
    public static final String ATTR_VALUE = "attribute::value";

    /**
     * 用于表示 “pattern” 的属性 XPATH 常量.
     */
    public static final String ATTR_PATTERN = "attribute::pattern";

    /**
     * 用于表示 “start” 的属性 XPATH 常量.
     */
    public static final String ATTR_START = "attribute::start";

    /**
     * 用于表示 “end” 的属性 XPATH 常量.
     */
    public static final String ATTR_ENT = "attribute::end";

    /**
     * 用于表示 “namespace” 的属性 XPATH 常量.
     */
    public static final String ATTR_NAME_SPACE = "attribute::namespace";

    /**
     * 用于表示 “fenixId” 的属性 XPATH 常量.
     */
    public static final String ATTR_FENIX_ID = "attribute::fenixId";

    /**
     * 用于表示 “when” 的属性 XPATH 常量.
     */
    public static final String ATTR_WHEN = "attribute::when";

    /**
     * 用于表示 “then” 的属性 XPATH 常量.
     */
    public static final String ATTR_THEN = "attribute::then";

    /**
     * 用于表示 “else” 的属性 XPATH 常量.
     */
    public static final String ATTR_ELSE = "attribute::else";

}
