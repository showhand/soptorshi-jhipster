import { Moment } from 'moment';

export interface IStockOutItem {
    id?: number;
    containerTrackingId?: string;
    quantity?: number;
    stockOutBy?: string;
    stockOutDate?: Moment;
    receiverId?: string;
    remarks?: string;
    itemCategoriesName?: string;
    itemCategoriesId?: number;
    itemSubCategoriesName?: string;
    itemSubCategoriesId?: number;
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
        public remarks?: string,
        public itemCategoriesName?: string,
        public itemCategoriesId?: number,
        public itemSubCategoriesName?: string,
        public itemSubCategoriesId?: number,
        public inventoryLocationsName?: string,
        public inventoryLocationsId?: number,
        public inventorySubLocationsName?: string,
        public inventorySubLocationsId?: number,
        public stockInItemsId?: number
    ) {}
}
