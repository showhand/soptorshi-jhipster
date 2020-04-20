import { Moment } from 'moment';

export const enum PaymentType {
    LC = 'LC',
    TT = 'TT',
    CASH = 'CASH',
    CHEQUE = 'CHEQUE',
    OTHERS = 'OTHERS'
}

export const enum CommercialPaymentStatus {
    WAITING_FOR_PAYMENT_CONFIRMATION = 'WAITING_FOR_PAYMENT_CONFIRMATION',
    PAYMENT_CONFIRMED = 'PAYMENT_CONFIRMED'
}

export interface ICommercialPaymentInfo {
    id?: number;
    paymentType?: PaymentType;
    totalAmountToPay?: number;
    totalAmountPaid?: number;
    remainingAmountToPay?: number;
    paymentStatus?: CommercialPaymentStatus;
    createdOn?: Moment;
    createdBy?: string;
    updatedOn?: Moment;
    updatedBy?: string;
    commercialPiProformaNo?: string;
    commercialPiId?: number;
}

export class CommercialPaymentInfo implements ICommercialPaymentInfo {
    constructor(
        public id?: number,
        public paymentType?: PaymentType,
        public totalAmountToPay?: number,
        public totalAmountPaid?: number,
        public remainingAmountToPay?: number,
        public paymentStatus?: CommercialPaymentStatus,
        public createdOn?: Moment,
        public createdBy?: string,
        public updatedOn?: Moment,
        public updatedBy?: string,
        public commercialPiProformaNo?: string,
        public commercialPiId?: number
    ) {}
}
