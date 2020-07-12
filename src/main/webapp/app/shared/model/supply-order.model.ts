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
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    deliveryDate?: Moment;
    status?: SupplyOrderStatus;
    areaWiseAccumulationRefNo?: string;
    remarks?: string;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyZoneManagerId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplySalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
    supplyAreaManagerId?: number;
    supplyShopName?: string;
    supplyShopId?: number;
}

export class SupplyOrder implements ISupplyOrder {
    constructor(
        public id?: number,
        public orderNo?: string,
        public dateOfOrder?: Moment,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public deliveryDate?: Moment,
        public status?: SupplyOrderStatus,
        public areaWiseAccumulationRefNo?: string,
        public remarks?: string,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyZoneManagerId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplySalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number,
        public supplyAreaManagerId?: number,
        public supplyShopName?: string,
        public supplyShopId?: number
    ) {}
}
