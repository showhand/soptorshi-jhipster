import { Moment } from 'moment';

export const enum ItemUnit {
    KG = 'KG',
    PCS = 'PCS',
    LITER = 'LITER',
    DOZEN = 'DOZEN',
    OTHERS = 'OTHERS'
}

export interface IStockStatus {
    id?: number;
    containerTrackingId?: string;
    totalQuantity?: number;
    unit?: ItemUnit;
    availableQuantity?: number;
    totalPrice?: number;
    availablePrice?: number;
    stockInBy?: string;
    stockInDate?: Moment;
    stockInItemsId?: number;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
}

export class StockStatus implements IStockStatus {
    constructor(
        public id?: number,
        public containerTrackingId?: string,
        public totalQuantity?: number,
        public unit?: ItemUnit,
        public availableQuantity?: number,
        public totalPrice?: number,
        public availablePrice?: number,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public stockInItemsId?: number,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number
    ) {}
}
