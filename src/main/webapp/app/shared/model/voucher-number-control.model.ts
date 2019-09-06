export const enum VoucherResetBasis {
    YEARLY = 'YEARLY',
    MONTHLY = 'MONTHLY',
    WEEKLY = 'WEEKLY',
    DAILY = 'DAILY',
    CARRY_FORWARD = 'CARRY_FORWARD'
}

export interface IVoucherNumberControl {
    id?: number;
    resetBasis?: VoucherResetBasis;
    startVoucherNo?: number;
    voucherLimit?: number;
    modifiedOn?: string;
    modifiedBy?: number;
    financialAccountYearDurationStr?: string;
    financialAccountYearId?: number;
    voucherName?: string;
    voucherId?: number;
}

export class VoucherNumberControl implements IVoucherNumberControl {
    constructor(
        public id?: number,
        public resetBasis?: VoucherResetBasis,
        public startVoucherNo?: number,
        public voucherLimit?: number,
        public modifiedOn?: string,
        public modifiedBy?: number,
        public financialAccountYearDurationStr?: string,
        public financialAccountYearId?: number,
        public voucherName?: string,
        public voucherId?: number
    ) {}
}
