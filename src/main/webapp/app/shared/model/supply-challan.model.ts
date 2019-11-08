import { Moment } from 'moment';

export interface ISupplyChallan {
    id?: number;
    challanNo?: string;
    dateOfChallan?: Moment;
    remarks?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    supplyOrderOrderNo?: string;
    supplyOrderId?: number;
}

export class SupplyChallan implements ISupplyChallan {
    constructor(
        public id?: number,
        public challanNo?: string,
        public dateOfChallan?: Moment,
        public remarks?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public supplyOrderOrderNo?: string,
        public supplyOrderId?: number
    ) {}
}
