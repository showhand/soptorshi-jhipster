import { Moment } from 'moment';

export interface ISupplyMoneyCollection {
    id?: number;
    totalAmount?: number;
    receivedAmount?: number;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyZoneName?: string;
    supplyZoneId?: number;
    supplyAreaName?: string;
    supplyAreaId?: number;
    supplyAreaManagerId?: number;
    supplySalesRepresentativeName?: string;
    supplySalesRepresentativeId?: number;
}

export class SupplyMoneyCollection implements ISupplyMoneyCollection {
    constructor(
        public id?: number,
        public totalAmount?: number,
        public receivedAmount?: number,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyZoneName?: string,
        public supplyZoneId?: number,
        public supplyAreaName?: string,
        public supplyAreaId?: number,
        public supplyAreaManagerId?: number,
        public supplySalesRepresentativeName?: string,
        public supplySalesRepresentativeId?: number
    ) {}
}
