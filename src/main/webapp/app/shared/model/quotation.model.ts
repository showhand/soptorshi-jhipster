import { Moment } from 'moment';

export interface IQuotation {
    id?: number;
    quotationNo?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
}

export class Quotation implements IQuotation {
    constructor(
        public id?: number,
        public quotationNo?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number
    ) {}
}
