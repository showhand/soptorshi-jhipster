import {Moment} from 'moment';

export interface IStockOutItem {
    id?: number;
    containerTrackingId?: string;
    quantity?: number;
    stockOutBy?: string;
    stockOutDate?: Moment;
    receiverId?: string;
    receivingPlace?: string;
    remarks?: string;
    productCategoriesName?: string;
    productCategoriesId?: number;
    productsName?: string;
    productsId?: number;
    inventoryLocationsName?: string;
    inventoryLocationsId?: number;
    inventorySubLocationsName?: string;
    inventorySubLocationsId?: number;
    stockInItemsId?: number;
}

export class StockOutItem implements IStockOutItem {
    constructor(
        public id?: number,
        public containerTrackingId?: string,
        public quantity?: number,
        public stockOutBy?: string,
        public stockOutDate?: Moment,
        public receiverId?: string,
        public receivingPlace?: string,
        public remarks?: string,
        public productCategoriesName?: string,
        public productCategoriesId?: number,
        public productsName?: string,
        public productsId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number,
        public stockInItemsId?: number
    ) {}
}
