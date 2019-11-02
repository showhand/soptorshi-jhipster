import { Moment } from 'moment';

export interface IContraVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    conversionFactor?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    currencyNotation?: string;
    currencyId?: number;
}

export class ContraVoucher implements IContraVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public conversionFactor?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
