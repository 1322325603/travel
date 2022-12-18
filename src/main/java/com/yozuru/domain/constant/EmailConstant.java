package com.yozuru.domain.constant;

/**
 * @author Yozuru
 */

public class EmailConstant {
    /**
     * 发送方的邮箱
     */
    public static final String SENDER_EMAIL = "3503726156@qq.com(黑马旅游网)";
    /**
     * 激活邮件的主题
     */
    public static final String ACTIVATION_EMAIL_SUBJECT ="【黑马旅游网】用户邮箱认证" ;
    /**
     * 激活链接的前缀
     */
    public static final String ACTIVATION_LINK_PRE ="http://localhost/activation/?code=" ;
    /**
     * 激活邮件的内容1
     */
    public static final String ACTIVATION_EMAIL_CONTENT_PRE_NAME = "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "</head>\n" +
            "<body>\n" +
            "<div style=\"width:700px;background-color:#fff;margin:0 auto;border: 1px solid #ccc;\">\n" +
            "    <div style=\"height:64px;margin:0;padding:0;width:100%;\">\n" +
            "        <a href=\"https://localhost/index.html\"\n" +
            "           style=\"display:block;padding: 12px 30px;text-decoration: none;font-size: 24px;letter-spacing: 3px;border-bottom: 1px solid #ccc;\"\n" +
            "           rel=\"noopener\" target=\"_blank\">\n" +
            "            黑马旅游网\n" +
            "        </a>\n" +
            "    </div>\n" +
            "    <table>\n" +
            "        <tbody>\n" +
            "        <tr height=\"40\">\n" +
            "            <td style=\"padding-left:25px;padding-right:25px;font-size:18px;font-family:'微软雅黑','黑体',arial;\">\n" +
            "                尊敬的" ;
    /**
     * 激活邮件的内容2
     */
    public static final String ACTIVATION_EMAIL_CONTENT_PRE_LINK ="：\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td style=\"padding-left:55px;padding-right:55px;font-family:'微软雅黑','黑体',arial;font-size:14px;line-height:18px;\">\n" +
            "                我们收到了您 绑定邮箱 的申请。\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td style=\"padding-left:55px;padding-right:55px;font-family:'微软雅黑','黑体',arial;font-size:14px;line-height:18px;\">\n" +
            "                确认无误后，请点击如下链接确认操作(有效期为 1 小时):\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td></td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td style=\"padding-left:55px;padding-right:55px;font-family:'微软雅黑','黑体',arial;font-size:16px;line-height:18px;\">\n" +
            "                <a href=\"" ;
    /**
     * 激活邮件的内容3
     */
    public static final String ACTIVATION_EMAIL_CONTENT_FINAL ="\"\n" +
            "                   rel=\"noopener\" target=\"_blank\">如果是您本人操作，请点击我！</a>\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td></td>\n" +
            "        </tr>\n" +
            "        <tr height=\"20\">\n" +
            "            <td style=\"padding-left:55px;padding-right:55px;font-family:'微软雅黑','黑体',arial;font-size:14px;\">\n" +
            "                若非本人操作请无视本邮件，祝您生活愉快！\n" +
            "            </td>\n" +
            "        </tr>\n" +
            "        <tr height=\"50\">\n" +
            "            <td></td>\n" +
            "        </tr>\n" +
            "        </tbody>\n" +
            "    </table>\n" +
            "</div>\n" +
            "</div>\n" +
            "</body>\n" +
            "</html>" ;
}
