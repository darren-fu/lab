package testStock;

import java.util.List;

/**
 * author: fuliang
 * date: 2017/9/29
 */
public class SkuStock {

    private String sku;

    private String size;


    private List<SkuWarehouseStock> stocks;


    

    class SkuWarehouseStock {

        private String warehouseCode;

        private Integer usableStock;

        private Integer tempLock;

        private Integer realLock;

        private Integer activityStock;
    }


    public Integer getUsableStock() {
        return 0;
    }

    public boolean lockTempStock(int number) {


        return true;
    }

    public boolean lockRealStock(int number) {
        return true;
    }


    interface StockOperation {

        Integer getUsableStock();

        Boolean lockTempStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        Boolean forceLockTempStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        Boolean lockRealStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);

        Boolean forceLockRealStock(int lockStockNumber, String businessSerial, List<String> businessSubSerial);


    }
}
