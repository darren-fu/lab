package compress;

/**
 * 说明:
 * <p/>
 * Copyright: Copyright (c)
 * <p/>
 * Company:
 * <p/>
 *
 * @author darrenfu
 * @version 1.0.0
 * @date 2016/7/18
 */
public class StringSource {
    public static  String JSON_STR = "[\n" +
            "  {\n" +
            "    \"id\": \"1\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"售前管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"11\",\n" +
            "    \"parentId\": \"1\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"图片管理\",\n" +
            "    \"href\": \"/stream/image/depot\",\n" +
            "    \"icon\": \"picture\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"12\",\n" +
            "    \"parentId\": \"1\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"运费模板\",\n" +
            "    \"href\": \"/setting/freight\",\n" +
            "    \"icon\": \"moban\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"121\",\n" +
            "    \"parentId\": \"12\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"运费模板内部页面\",\n" +
            "    \"href\": \"/setting/freight/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"13\",\n" +
            "    \"parentId\": \"1\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"物流信息\",\n" +
            "    \"href\": \"/setting/express\",\n" +
            "    \"icon\": \"ship\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"131\",\n" +
            "    \"parentId\": \"13\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"物流信息内部页面\",\n" +
            "    \"href\": \"/setting/express/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"14\",\n" +
            "    \"parentId\": \"1\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"4\",\n" +
            "    \"name\": \"地址管理\",\n" +
            "    \"href\": \"/setting/delivery\",\n" +
            "    \"icon\": \"ditu\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"141\",\n" +
            "    \"parentId\": \"14\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"4\",\n" +
            "    \"name\": \"地址管理内部页面\",\n" +
            "    \"href\": \"/setting/delivery/*\",\n" +
            "    \"icon\": \"ditu\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"2\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"商品管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"21\",\n" +
            "    \"parentId\": \"2\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"商品列表\",\n" +
            "    \"href\": \"/supply/product/list\",\n" +
            "    \"icon\": \"gouwudai\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"211\",\n" +
            "    \"parentId\": \"21\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"更新商品基本信息\",\n" +
            "    \"href\": \"/wares/custom/update\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"212\",\n" +
            "    \"parentId\": \"21\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"添加商品详细介绍\",\n" +
            "    \"href\": \"/wares/custom/detail\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"22\",\n" +
            "    \"parentId\": \"2\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"添加商品\",\n" +
            "    \"href\": \"/wares/custom/beforeAdd\",\n" +
            "    \"icon\": \"gouwu\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"221\",\n" +
            "    \"parentId\": \"22\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"添加商品基本信息\",\n" +
            "    \"href\": \"/wares/custom/add\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"223\",\n" +
            "    \"parentId\": \"22\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"快捷上货\",\n" +
            "    \"href\": \"/wares/distribute/add\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"23\",\n" +
            "    \"parentId\": \"2\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"回收站\",\n" +
            "    \"href\": \"/recycled/index\",\n" +
            "    \"icon\": \"delete\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"3\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"订单管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"31\",\n" +
            "    \"parentId\": \"3\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"订单列表\",\n" +
            "    \"href\": \"/order/list\",\n" +
            "    \"icon\": \"dingdanguanli\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"311\",\n" +
            "    \"parentId\": \"31\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"订单列表内部页面\",\n" +
            "    \"href\": \"/order/list/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"32\",\n" +
            "    \"parentId\": \"3\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"退货退款申请\",\n" +
            "    \"href\": \"/afterSale/return/apply\",\n" +
            "    \"icon\": \"tuihuo\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"321\",\n" +
            "    \"parentId\": \"32\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"退货退款申请内部页面\",\n" +
            "    \"href\": \"/afterSale/*\",\n" +
            "    \"icon\": \"tuihuo\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"33\",\n" +
            "    \"parentId\": \"3\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"单据管理\",\n" +
            "    \"href\": \"/order/receipt\",\n" +
            "    \"icon\": \"jihua\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"331\",\n" +
            "    \"parentId\": \"33\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"单据管理内部页面\",\n" +
            "    \"href\": \"/order/returned/*|/order/refund/*|/order/receive/*|/order/delivery/*\",\n" +
            "    \"icon\": \"dingdanguanli\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"34\",\n" +
            "    \"parentId\": \"3\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"4\",\n" +
            "    \"name\": \"订单设置\",\n" +
            "    \"href\": \"/order/settings\",\n" +
            "    \"icon\": \"dingdan\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"4\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"4\",\n" +
            "    \"name\": \"店铺管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"41\",\n" +
            "    \"parentId\": \"4\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"基本信息\",\n" +
            "    \"href\": \"/sites/index\",\n" +
            "    \"icon\": \"jibenxinxi\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"42\",\n" +
            "    \"parentId\": \"4\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"店铺设置\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"settings\",\n" +
            "    \"display\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"43\",\n" +
            "    \"parentId\": \"4\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"品牌管理\",\n" +
            "    \"href\": \"/sites/brand\",\n" +
            "    \"icon\": \"brandgroup\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"44\",\n" +
            "    \"parentId\": \"4\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"4\",\n" +
            "    \"name\": \"类目管理\",\n" +
            "    \"href\": \"/wares/cateComm\",\n" +
            "    \"icon\": \"projectmanage\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"5\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"5\",\n" +
            "    \"name\": \"财务管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"51\",\n" +
            "    \"parentId\": \"5\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"我的账户\",\n" +
            "    \"href\": \"/funds/credit/list/show\",\n" +
            "    \"icon\": \"shimingrenzheng\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"511\",\n" +
            "    \"parentId\": \"51\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"我的账户提现\",\n" +
            "    \"href\": \"/cash(/auto){0,1}/apply/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"52\",\n" +
            "    \"parentId\": \"5\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"我的银行卡\",\n" +
            "    \"href\": \"/cash/bind/bank/list/show\",\n" +
            "    \"icon\": \"yinxingqia\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"53\",\n" +
            "    \"parentId\": \"5\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"3\",\n" +
            "    \"name\": \"我的支付密码\",\n" +
            "    \"href\": \"/pay/pwd/is/set\",\n" +
            "    \"icon\": \"suo\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"531\",\n" +
            "    \"parentId\": \"53\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"我的支付密码设置\",\n" +
            "    \"href\": \"/pay/pwd/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"6\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"6\",\n" +
            "    \"name\": \"系统管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"61\",\n" +
            "    \"parentId\": \"6\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"实名认证\",\n" +
            "    \"href\": \"/authentication/index\",\n" +
            "    \"icon\": \"shimingrenzheng\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"611\",\n" +
            "    \"parentId\": \"61\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"实名认证\",\n" +
            "    \"href\": \"/authentication/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"62\",\n" +
            "    \"parentId\": \"6\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"安全设置\",\n" +
            "    \"href\": \"/user/security\",\n" +
            "    \"icon\": \"passport\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"621\",\n" +
            "    \"parentId\": \"62\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"邮箱绑定\",\n" +
            "    \"href\": \"/user/security/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"7\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"7\",\n" +
            "    \"name\": \"消息管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"71\",\n" +
            "    \"parentId\": \"7\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"公告列表\",\n" +
            "    \"href\": \"/article/index\",\n" +
            "    \"icon\": \"xiaoxi\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"711\",\n" +
            "    \"parentId\": \"71\",\n" +
            "    \"level\": \"3\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"公告详情\",\n" +
            "    \"href\": \"/article/*\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"8\",\n" +
            "    \"parentId\": null,\n" +
            "    \"level\": \"1\",\n" +
            "    \"order\": \"8\",\n" +
            "    \"name\": \"统计管理\",\n" +
            "    \"href\": \"\",\n" +
            "    \"icon\": \"\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": \"81\",\n" +
            "    \"parentId\": \"8\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"1\",\n" +
            "    \"name\": \"商品销量统计\",\n" +
            "    \"href\": \"/statistics/sales/query\",\n" +
            "    \"icon\": \"xiaoliang\",\n" +
            "    \"display\": \"\"\n" +
            "  },\n" +
            "\n" +
            "  {\n" +
            "    \"id\": \"82\",\n" +
            "    \"parentId\": \"8\",\n" +
            "    \"level\": \"2\",\n" +
            "    \"order\": \"2\",\n" +
            "    \"name\": \"建站管理\",\n" +
            "    \"href\": \"http://x-site.qianmi2.com:3000/mysite/pc/index?sc=H4sIAAAAAAAAA0sxSgcAZBo5jgMAAAA=&token=\",\n" +
            "    \"icon\": \"xiaoliang\",\n" +
            "    \"display\": \"\"\n" +
            "  }\n" +
            "\n" +
            "]\n";

}
