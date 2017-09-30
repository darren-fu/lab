package testStock;

import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * author: fuliang
 * date: 2017/9/29
 */
public class SkuStock {

    private String sku;

    private String size;


    private List<SkuWarehouseStock> stocks;


    /**
     * The type Sku warehouse stock.
     */
    class SkuWarehouseStock {

        private String warehouseCode;

        private Integer usableStock;

        private Integer tempLock;

        private Integer realLock;

        private Integer activityStock;
    }


    class StockAllotCommand {


    }


    /**
     * 锁定库存命令
     */
    class StockTempLockCommand {
        //SKU
        private String sku;

        //尺码
        private String size;

        //锁定库存数量
        private Integer lockStockNumber;

        //业务流水号 order编号
        private String businessSerial;

        //业务子流水号 order_goods_id
        private List<String> businessSubSerial;

        //锁定类型 临时/正式
        private LockTypeEnum lockType;

        //是否强制锁定
        private boolean forceLock = false;

        //使用的仓库
        private List<String> warehouseList;

        //排除的仓库
        private List<String> excludeWarehouseList;

    }

    /**
     * 锁定库存命令
     */
    class StockRealLockCommand {
        //SKU
        private String sku;

        //尺码
        private String size;

        //锁定库存数量
        private Integer lockStockNumber;

        //业务流水号 order编号
        private String businessSerial;

        //业务子流水号 order_goods_id
        private List<String> businessSubSerial;

        //锁定类型 临时/正式
        private LockTypeEnum lockType;

        //是否强制锁定
        private boolean forceLock = false;

        //使用的仓库
        private List<String> warehouseList;

        //排除的仓库
        private List<String> excludeWarehouseList;

    }

    /**
     * 释放库存锁定命令
     */
    class StockLockReleaseCommand {

        private String sku;

        private String size;

        private String warehouseCode;

        private Integer needStockNum;
    }

    /**
     * 库存扣减命令
     */
    class StockLockDeductCommand {

    }

    /**
     * 出库命令
     */
    class StockDeliverCommand {

    }

    /**
     * 库存增加命令
     */
    class StockIncrCommand {
    }

    enum LockTypeEnum {
        TEMP(), REAL();
    }

    enum StockOperationEnum {

        STORAGE("入库"),
        OOS_STORAGE("缺货补齐"),
        O_TEMP_LOCK("订单临时锁"),
        O_REAL_LOCK("订单正式锁"),
        O_DEDUCT_STOCK("库存扣减"),
        FORCE_DELIVER("强制出库"),
        LEND_DELIVER("借衣出库"),;

        private String text;

        StockOperationEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


    }

    public static void main(String[] args) {
        String name = StockOperationEnum.O_TEMP_LOCK.name();

        System.out.println(name);


        System.out.println(ArrayUtils.toString(StockOperationEnum.values()));
    }

    /**
     * Gets usable stock.
     *
     * @return the usable stock
     */
    public Integer getUsableStock() {
        return 0;
    }

    /**
     * Lock temp stock boolean.
     *
     * @param number the number
     * @return the boolean
     */
    public boolean lockTempStock(int number) {


        return true;
    }

    /**
     * Lock real stock boolean.
     *
     * @param number the number
     * @return the boolean
     */
    public boolean lockRealStock(int number) {
        return true;
    }


    /**
     * The interface Stock operation.
     */
    interface StockOperation {

        /**
         * Gets usable stock.
         *
         * @return the usable stock
         */
        Integer getUsableStock();

        /**
         * Lock temp stock boolean.
         *
         * @param lockStockNumber   the lock stock number
         * @param businessSerial    the business serial
         * @param businessSubSerial the business sub serial
         * @return the boolean
         */
        Boolean lockTempStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        /**
         * Force lock temp stock boolean.
         *
         * @param lockStockNumber   the lock stock number
         * @param businessSerial    the business serial
         * @param businessSubSerial the business sub serial
         * @return the boolean
         */
        Boolean forceLockTempStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        /**
         * Lock real stock boolean.
         *
         * @param lockStockNumber   the lock stock number
         * @param businessSerial    the business serial
         * @param businessSubSerial the business sub serial
         * @return the boolean
         */
        Boolean lockRealStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        /**
         * Force lock real stock boolean.
         *
         * @param lockStockNumber   the lock stock number
         * @param businessSerial    the business serial
         * @param businessSubSerial the business sub serial
         * @return the boolean
         */
        Boolean forceLockRealStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);


        Boolean realeaseLock(String businessSerial);

        Boolean realeaseLock(String businessSerial, List<String> businessSubSerial);


        Boolean deductStock();


    }
}
