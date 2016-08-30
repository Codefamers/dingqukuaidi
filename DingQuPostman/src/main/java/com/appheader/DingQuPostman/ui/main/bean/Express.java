package com.appheader.DingQuPostman.ui.main.bean;

import com.appheader.DingQuPostman.common.utils.json.annotation.JSONEntity;
import com.appheader.DingQuPostman.common.utils.json.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/7/test_two 0013.
 */
@JSONEntity
public class Express implements Serializable {
    @JSONField
    private String success;
    @JSONField
    private Data data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getHasMore() {
        return hasMore;
    }

    public void setHasMore(String hasMore) {
        this.hasMore = hasMore;
    }

    @JSONField

    private String hasMore;
    @JSONField
    private String count;

    @JSONEntity
    public static class Data implements Serializable {
        @JSONField
        private List list;

        @JSONEntity
        public static class list implements Serializable {
            @JSONField
            private int id;
            @JSONField
            private String express_company_name;
            @JSONField
            private String express_number;
            @JSONField
            private String postman_id;
            @JSONField
            private String merchant_id;
            @JSONField
            private String receiver_phone;
            @JSONField
            private String status;
            @JSONField
            private String create_time;
            @JSONField
            private String deposit_time;
            @JSONField
            private String withdraw_time;
            @JSONField
            private String deposit_money;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getExpress_company_name() {
                return express_company_name;
            }

            public void setExpress_company_name(String express_company_name) {
                this.express_company_name = express_company_name;
            }

            public String getExpress_number() {
                return express_number;
            }

            public void setExpress_number(String express_number) {
                this.express_number = express_number;
            }

            public String getPostman_id() {
                return postman_id;
            }

            public void setPostman_id(String postman_id) {
                this.postman_id = postman_id;
            }

            public String getMerchant_id() {
                return merchant_id;
            }

            public void setMerchant_id(String merchant_id) {
                this.merchant_id = merchant_id;
            }

            public String getReceiver_phone() {
                return receiver_phone;
            }

            public void setReceiver_phone(String receiver_phone) {
                this.receiver_phone = receiver_phone;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getDeposit_time() {
                return deposit_time;
            }

            public void setDeposit_time(String deposit_time) {
                this.deposit_time = deposit_time;
            }

            public String getWithdraw_time() {
                return withdraw_time;
            }

            public void setWithdraw_time(String withdraw_time) {
                this.withdraw_time = withdraw_time;
            }

            public String getDeposit_money() {
                return deposit_money;
            }

            public void setDeposit_money(String deposit_money) {
                this.deposit_money = deposit_money;
            }
        }
    }
}
