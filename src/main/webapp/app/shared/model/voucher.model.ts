export interface IVoucher {
    id?: number;
    name?: string;
    shortName?: string;
    modifiedOn?: string;
    modifiedBy?: number;
}

export class Voucher implements IVoucher {
    constructor(
        public id?: number,
        public name?: string,
        public shortName?: string,
        public modifiedOn?: string,
        public modifiedBy?: number
    ) {}
}
