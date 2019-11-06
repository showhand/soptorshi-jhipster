import { Moment } from 'moment';

export interface ISupplyOrderDetails {
    id?: number;
    productName?: string;
    productVolume?: number;
    totalPrice?: number;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyOrderOrderNo?: string;
    supplyOrderId?: number;
}

export class SupplyOrderDetails implements ISupplyOrderDetails {
    constructor(
        public id?: number,
        public productName?: string,
        public productVolume?: number,
        public totalPrice?: number,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyOrderOrderNo?: string,
        public supplyOrderId?: number
    ) {}
}
