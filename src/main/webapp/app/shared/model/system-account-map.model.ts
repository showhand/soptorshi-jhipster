import { Moment } from 'moment';

export const enum AccountType {
    OPENING_BALANCE_ADJUSTMENT_ACCOUNT = 'OPENING_BALANCE_ADJUSTMENT_ACCOUNT',
    PROVIDENT_FUND_ACCOUNT = 'PROVIDENT_FUND_ACCOUNT'
}

export interface ISystemAccountMap {
    id?: number;
    accountType?: AccountType;
    modifiedBy?: string;
    modifiedOn?: Moment;
    accountName?: string;
    accountId?: number;
}

export class SystemAccountMap implements ISystemAccountMap {
    constructor(
        public id?: number,
        public accountType?: AccountType,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public accountName?: string,
        public accountId?: number
    ) {}
}
