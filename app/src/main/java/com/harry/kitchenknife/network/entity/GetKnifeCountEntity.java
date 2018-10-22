package com.harry.kitchenknife.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/10/15.
 */
public class GetKnifeCountEntity {

    /**
     * code : 100
     * msg : 处理成功！
     * extend : {"commodityTypes":[{"commodityTypeId":2,"commodityTypeNumber":"2","commodityTypeTitle":"杀猪刀","commodityTypeSellingPrice":36.2,"commodityTypeRent":300,"commodityTypeRentalPrice":1,"count":4,"imageUrl":[{"id":86,"imagesUrl":"/upload/commodityType/2018-10-15/1539603779718.jpg","commodityTypeId":2},{"id":87,"imagesUrl":"/upload/commodityType/2018-10-15/1539603779722.jpg","commodityTypeId":2}]},{"commodityTypeId":3,"commodityTypeNumber":"大砍刀","commodityTypeTitle":"大砍刀","commodityTypeSellingPrice":20,"commodityTypeRent":20,"commodityTypeRentalPrice":5,"count":0,"imageUrl":[{"id":83,"imagesUrl":"/upload/commodityType/2018-10-13/1539402606844.jpg","commodityTypeId":3},{"id":84,"imagesUrl":"/upload/commodityType/2018-10-13/1539402606847.jpg","commodityTypeId":3},{"id":85,"imagesUrl":"/upload/commodityType/2018-10-13/1539402606867.jpg","commodityTypeId":3}]}]}
     * object : null
     */

    public int code;
    public String msg;
    public ExtendBean extend;
    public Object object;

    public static class ExtendBean {
        public List<CommodityTypesBean> commodityTypes;

        public static class CommodityTypesBean {
            /**
             * commodityTypeId : 2
             * commodityTypeNumber : 2
             * commodityTypeTitle : 杀猪刀
             * commodityTypeSellingPrice : 36.2
             * commodityTypeRent : 300
             * commodityTypeRentalPrice : 1
             * count : 4
             * imageUrl : [{"id":86,"imagesUrl":"/upload/commodityType/2018-10-15/1539603779718.jpg","commodityTypeId":2},{"id":87,"imagesUrl":"/upload/commodityType/2018-10-15/1539603779722.jpg","commodityTypeId":2}]
             */

            public int commodityTypeId;
            public String commodityTypeNumber;
            public String commodityTypeTitle;
            public double commodityTypeSellingPrice;
            public double commodityTypeRent;
            public double commodityTypeRentalPrice;
            public int count;
            public List<ImageUrlBean> imageUrl;

            public static class ImageUrlBean {
                /**
                 * id : 86
                 * imagesUrl : /upload/commodityType/2018-10-15/1539603779718.jpg
                 * commodityTypeId : 2
                 */

                public int id;
                public String imagesUrl;
                public int commodityTypeId;
            }
        }
    }
}
