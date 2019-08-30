import { Moment } from 'moment';

export const enum SelectionType {
    SELECTED = 'SELECTED',
    NOT_SELECTED = 'NOT_SELECTED'
}

export interface IQuotation {
    id?: number;
    quotationNo?: string;
    attachmentContentType?: string;
    attachment?: any;
    selectionStatus?: SelectionType;
    totalAmount?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    requisitionRequisitionNo?: string;
    requisitionId?: number;
    vendorCompanyName?: string;
    vendorId?: number;
}

export class Quotation implements IQuotation {
    constructor(
        public id?: number,
        public quotationNo?: string,
        public attachmentContentType?: string,
        public attachment?: any,
        public selectionStatus?: SelectionType,
        public totalAmount?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public requisitionRequisitionNo?: string,
        public requisitionId?: number,
        public vendorCompanyName?: string,
        public vendorId?: number
    ) {}
}
