import { Moment } from 'moment';

export interface IProductPrice {
    id?: number;
    price?: number;
    priceDate?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
    productName?: string;
    productId?: number;
}

export class ProductPrice implements IProductPrice {
    constructor(
        public id?: number,
        public price?: number,
        public priceDate?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public productName?: string,
        public productId?: number
    ) {}
}
