import { Moment } from 'moment';

export const enum UnitOfMeasurements {
    PCS = 'PCS',
    KG = 'KG',
    TON = 'TON',
    GRAM = 'GRAM'
}

export const enum ContainerCategory {
    BOTTLE = 'BOTTLE',
    DRUM = 'DRUM',
    PACKET = 'PACKET',
    SACK = 'SACK',
    OTHERS = 'OTHERS'
}

export const enum ProductType {
    FINISHED_PRODUCT = 'FINISHED_PRODUCT',
    BY_PRODUCT = 'BY_PRODUCT'
}

export interface IStockInItem {
    id?: number;
    quantity?: number;
    unit?: UnitOfMeasurements;
    price?: number;
    containerCategory?: ContainerCategory;
    containerTrackingId?: string;
    mfgDate?: Moment;
    expiryDate?: Moment;
    typeOfProduct?: ProductType;
    stockInBy?: string;
    stockInDate?: Moment;
    remarks?: string;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
    vendorCompanyName?: string;
    vendorId?: number;
    stockInProcessesId?: number;
    requisitionsRequisitionNo?: string;
    requisitionsId?: number;
}

export class StockInItem implements IStockInItem {
    constructor(
        public id?: number,
        public quantity?: number,
        public unit?: UnitOfMeasurements,
        public price?: number,
        public containerCategory?: ContainerCategory,
        public containerTrackingId?: string,
        public mfgDate?: Moment,
        public expiryDate?: Moment,
        public typeOfProduct?: ProductType,
        public stockInBy?: string,
        public stockInDate?: Moment,
        public remarks?: string,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number,
        public vendorCompanyName?: string,
        public vendorId?: number,
        public stockInProcessesId?: number,
        public requisitionsRequisitionNo?: string,
        public requisitionsId?: number
    ) {}
}
