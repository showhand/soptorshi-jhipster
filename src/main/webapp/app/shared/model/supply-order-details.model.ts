import { Moment } from 'moment';

export interface ISupplyOrderDetails {
    id?: number;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    quantity?: number;
    price?: number;
    supplyOrderOrderNo?: string;
    supplyOrderId?: number;
    productCategoryName?: string;
    productCategoryId?: number;
    productName?: string;
    productId?: number;
}

export class SupplyOrderDetails implements ISupplyOrderDetails {
    constructor(
        public id?: number,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public quantity?: number,
        public price?: number,
        public supplyOrderOrderNo?: string,
        public supplyOrderId?: number,
        public productCategoryName?: string,
        public productCategoryId?: number,
        public productName?: string,
        public productId?: number
    ) {}
}
