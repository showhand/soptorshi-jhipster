import { Moment } from 'moment';

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export interface IStockStatus {
    id?: number;
    containerTrackingId?: string;
    totalQuantity?: number;
    unit?: UnitOfMeasurements;
    availableQuantity?: number;
    totalPrice?: number;
    availablePrice?: number;
    stockInBy?: string;
    stockInDate?: Moment;
    stockInItemId?: number;
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
        public unit?: UnitOfMeasurements,
        public availableQuantity?: number,
        public totalPrice?: number,
        public availablePrice?: number,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public stockInItemId?: number,
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
