package com.harry.kitchenknife.network.entity;

import java.util.List;

/**
 * Created by Harry on 2018/10/15.
 */
public class MainEntity {

    /**
     * code : 100
     * msg : 处理成功！
     * extend : {"homes":{"equipmentRoles":[{"equipmentRoleId":1,"equipmentRoleName":"菜刀租赁","equipmentRoleImage":"dsgssgsgs","yn":null},{"equipmentRoleId":2,"equipmentRoleName":"菜刀购买","equipmentRoleImage":"fdasfafafaf","yn":null},{"equipmentRoleId":3,"equipmentRoleName":"菜刀回收","equipmentRoleImage":"sdfdsfsfsf","yn":null}],"video":{"videoId":3,"videoName":"加藤鹰","videoUrl":"dsdhdf","videoState":1}}}
     * object : null
     */

    public int code;
    public String msg;
    public ExtendBean extend;
    public Object object;

    public static class ExtendBean {
        /**
         * homes : {"equipmentRoles":[{"equipmentRoleId":1,"equipmentRoleName":"菜刀租赁","equipmentRoleImage":"dsgssgsgs","yn":null},{"equipmentRoleId":2,"equipmentRoleName":"菜刀购买","equipmentRoleImage":"fdasfafafaf","yn":null},{"equipmentRoleId":3,"equipmentRoleName":"菜刀回收","equipmentRoleImage":"sdfdsfsfsf","yn":null}],"video":{"videoId":3,"videoName":"加藤鹰","videoUrl":"dsdhdf","videoState":1}}
         */

        public HomesBean homes;

        public static class HomesBean {
            /**
             * equipmentRoles : [{"equipmentRoleId":1,"equipmentRoleName":"菜刀租赁","equipmentRoleImage":"dsgssgsgs","yn":null},{"equipmentRoleId":2,"equipmentRoleName":"菜刀购买","equipmentRoleImage":"fdasfafafaf","yn":null},{"equipmentRoleId":3,"equipmentRoleName":"菜刀回收","equipmentRoleImage":"sdfdsfsfsf","yn":null}]
             * video : {"videoId":3,"videoName":"加藤鹰","videoUrl":"dsdhdf","videoState":1}
             */

            public VideoBean video;
            public List<EquipmentRolesBean> equipmentRoles;

            public static class VideoBean {
                /**
                 * videoId : 3
                 * videoName : 加藤鹰
                 * videoUrl : dsdhdf
                 * videoState : 1
                 */

                public int videoId;
                public String videoName;
                public String videoUrl;
                public int videoState;
            }

            public static class EquipmentRolesBean {
                /**
                 * equipmentRoleId : 1
                 * equipmentRoleName : 菜刀租赁
                 * equipmentRoleImage : dsgssgsgs
                 * yn : null
                 */

                public int equipmentRoleId;
                public String equipmentRoleName;
                public String equipmentRoleImage;
                public Object yn;
            }
        }
    }
}
