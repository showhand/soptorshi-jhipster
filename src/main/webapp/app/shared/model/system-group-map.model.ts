import { Moment } from 'moment';

export const enum GroupType {
    ASSETS = 'ASSETS',
    LIABILITIES = 'LIABILITIES',
    INCOME = 'INCOME',
    EXPENSES = 'EXPENSES',
    BANK_ACCOUNTS = 'BANK_ACCOUNTS',
    CASH_IN_HAND = 'CASH_IN_HAND',
    SUNDRY_DEBTOR = 'SUNDRY_DEBTOR',
    SUNDRY_CREDITOR = 'SUNDRY_CREDITOR',
    CURRENT_LIABILITIES = 'CURRENT_LIABILITIES'
}

export interface ISystemGroupMap {
    id?: number;
    groupType?: GroupType;
    modifiedBy?: string;
    modifiedOn?: Moment;
    groupName?: string;
    groupId?: number;
}

export class SystemGroupMap implements ISystemGroupMap {
    constructor(
        public id?: number,
        public groupType?: GroupType,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public groupName?: string,
        public groupId?: number
    ) {}
}
