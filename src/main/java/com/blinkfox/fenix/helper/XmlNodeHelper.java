package com.blinkfox.fenix.helper;

import com.blinkfox.fenix.config.FenixConfig;
import com.blinkfox.fenix.config.FenixConfigManager;
import com.blinkfox.fenix.consts.Const;
import com.blinkfox.fenix.consts.XpathConst;
import com.blinkfox.fenix.exception.ConfigNotFoundException;
import com.blinkfox.fenix.exception.FieldEmptyException;
import com.blinkfox.fenix.exception.NodeNotFoundException;
import com.blinkfox.fenix.exception.XmlParseException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.UrlResource;
import org.springframework.util.CollectionUtils;

/**
 * XML 文件和 XML 标签节点相关操作的工具类.
 *
 * @author blinkfox on 2019-08-04.
 * @since v1.0.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XmlNodeHelper {

    /**
     * {@link FenixConfigManager} 对象的引用.
     */
    private static final FenixConfigManager fenixConfigManager = FenixConfigManager.getInstance();

    /**
     * 获取 namespace 文档中的指定的 fenixId 的节点对应的 Node 节点，如果是 debug 模式，则实时获取；否则从缓存中获取.
     *
     * <p>注意：v2.4.1 版本开始了 debug 模式的功能，当开启了 debug 模式之后，
     * 将实时根据已知的 namespace 去读取之前缓存过的 XML 文件中的内容.</p>
     *
     * @param namespace XML 文件对应命名空间
     * @param fenixId   fenixId
     * @return dom4j的Node节点
     */
    public static Node getNodeBySpaceAndId(String namespace, String fenixId) {
        if (fenixConfigManager.getFenixConfig().isDebug()) {
            Set<URL> urlSet = FenixConfig.getXmlUrlMap().get(namespace);
            if (CollectionUtils.isEmpty(urlSet)) {
                throw new ConfigNotFoundException("【Fenix 异常提示】在 debug 模式下，未找到命名空间为【" + namespace + "】的 XML 文件，请检查！");
            }

            Document doc;
            Node node = null;
            for (URL url : urlSet) {
                // 实时读取 XML 文件中指定 fenixId 的内容节点.
                try (InputStream in = new UrlResource(url).getInputStream()) {
                    doc = new SAXReader().read(in);
                } catch (Exception e) {
                    throw new XmlParseException("【Fenix 异常提示】读取或解析 XML 文件失败，读取到的 XML 路径是:【" + url.getPath() + "】.", e);
                }

                try {
                    Namespace xmlNamespace = doc.getRootElement().getNamespace();
                    XPath xpath;
                    if (!"".equals(xmlNamespace.getURI())) {
                        Map<String, String> namespaceUriMap = new HashMap<>(1);
                        namespaceUriMap.put("namespace", xmlNamespace.getURI());
                        xpath = DocumentHelper.createXPath(XpathConst.FENIX_TAG_WITH_NAMESPACE
                                + "[@id='" + fenixId + "']");
                        xpath.setNamespaceURIs(namespaceUriMap);
                    } else {
                        xpath = DocumentHelper.createXPath("/fenixs/fenix[@id='" + fenixId + "']");
                    }
                    node = xpath.selectSingleNode(doc);
                    if (Objects.nonNull(node)) {
                        break;
                    }
                } catch (Exception e) {
                    throw new NodeNotFoundException("【Fenix 异常提示】在 XML 文件【" + url.getPath()
                            + "】中未找到 ID 为【" + fenixId + "】的 Fenix 节点.");
                }
            }
            return node;
        } else {
            return FenixConfig.getFenixs().get(StringHelper.concat(namespace, Const.DOT, fenixId));
        }
    }

    /**
     * 获取 XML 节点的文本值，如果对象是空的，则转为空字符串.
     *
     * @param node dom4j 节点
     * @return 返回节点文本值
     */
    public static String getNodeText(Node node) {
        return node == null ? "" : node.getText();
    }

    /**
     * 获取节点文本的字符串值.
     *
     * @param node     dom4j 节点
     * @param attrName 节点属性
     * @return 返回节点文本值
     */
    public static String getNodeAttrText(Node node, String attrName) {
        return XmlNodeHelper.getNodeText(node.selectSingleNode(attrName));
    }

    /**
     * 获取和检查节点文本，会检查节点是否为空，如果节点为空，则抛出异常.
     *
     * <p>注：该方法需要判断必填的参数是否为空，为空的话，需要抛出 {@link FieldEmptyException} 异常.</p>
     *
     * @param node     dom4j 节点
     * @param nodeName 节点名称
     * @return 返回节点文本值
     */
    public static String getAndCheckNodeText(Node node, String nodeName) {
        String text = XmlNodeHelper.getNodeText(node.selectSingleNode(nodeName));
        if (StringHelper.isBlank(text)) {
            throw new FieldEmptyException("【Fenix 异常】【" + node.getName() + "】节点中填写的属性不存在或者属性内容是空的！");
        }
        return text;
    }

    /**
     * 检查和获取开始和结束文本的内容，返回一个数组.
     *
     * <p>会检查这两个节点是否为空，如果都为空，则抛出 {@link FieldEmptyException} 异常.</p>
     *
     * @param node dom4j 节点
     * @return 返回开始和结束文本的二元数组
     */
    public static String[] getRangeCheckNodeText(Node node) {
        String startText = XmlNodeHelper.getNodeText(node.selectSingleNode(XpathConst.ATTR_START));
        String endText = XmlNodeHelper.getNodeText(node.selectSingleNode(XpathConst.ATTR_ENT));
        if (StringHelper.isBlank(startText) && StringHelper.isBlank(endText)) {
            throw new FieldEmptyException("【Fenix 异常】【" + node.getName() + "】标签中填写的【start】和【end】字段值都是空的！");
        }
        return new String[]{startText, endText};
    }

}
