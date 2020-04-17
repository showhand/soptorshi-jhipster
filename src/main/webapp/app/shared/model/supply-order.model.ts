import { Moment } from 'moment';

export const enum SupplyOrderStatus {
    ORDER_RECEIVED = 'ORDER_RECEIVED',
    PROCESSING_ORDER = 'PROCESSING_ORDER',
    ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION = 'ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION',
    ORDER_CLOSE = 'ORDER_CLOSE'
}

export interface ISupplyOrder {
    id?: number;
    orderNo?: string;
    dateOfOrder?: Moment;
    offer?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    offerAmount?: number;
    deliveryDate?: Moment;
    supplyOrderStatus?: SupplyOrderStatus;
    supplyZoneZoneName?: string;
    supplyZoneId?: number;
    supplyAreaAreaName?: string;
    supplyAreaId?: number;
    supplySalesRepresentativeSalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
    supplyAreaManagerId?: number;
}

export class SupplyOrder implements ISupplyOrder {
    constructor(
        public id?: number,
        public orderNo?: string,
        public dateOfOrder?: Moment,
        public offer?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public offerAmount?: number,
        public deliveryDate?: Moment,
        public supplyOrderStatus?: SupplyOrderStatus,
        public supplyZoneZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaAreaName?: string,
        public supplyAreaId?: number,
        public supplySalesRepresentativeSalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number,
        public supplyAreaManagerId?: number
    ) {}
}
