import { Moment } from 'moment';

export interface IRequisitionVoucherRelation {
    id?: number;
    voucherNo?: string;
    amount?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    voucherName?: string;
    voucherId?: number;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
}

export class RequisitionVoucherRelation implements IRequisitionVoucherRelation {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public amount?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public voucherName?: string,
        public voucherId?: number,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number
    ) {}
}
