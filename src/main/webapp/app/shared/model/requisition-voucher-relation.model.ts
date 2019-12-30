import { Moment } from 'moment';

export const enum VoucherType {
    SELLING = 'SELLING',
    BUYING = 'BUYING'
}

export interface IRequisitionVoucherRelation {
    id?: number;
    voucherType?: VoucherType;
    voucherNo?: string;
    amount?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
}

export class RequisitionVoucherRelation implements IRequisitionVoucherRelation {
    constructor(
        public id?: number,
        public voucherType?: VoucherType,
        public voucherNo?: string,
        public amount?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number
    ) {}
}
