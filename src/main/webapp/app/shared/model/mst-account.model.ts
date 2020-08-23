import { Moment } from 'moment';

export const enum BalanceType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT'
}

export const enum ReservedFlag {
    RESERVED = 'RESERVED',
    NOT_RESERVED = 'NOT_RESERVED'
}

export const enum DepreciationType {
    MONTHLY = 'MONTHLY',
    YEARLY = 'YEARLY'
}

export interface IMstAccount {
    id?: number;
    code?: string;
    name?: string;
    yearOpenBalance?: number;
    yearOpenBalanceType?: BalanceType;
    yearCloseBalance?: number;
    reservedFlag?: ReservedFlag;
    modifiedBy?: string;
    modifiedOn?: Moment;
    depreciationRate?: number;
    depreciationType?: DepreciationType;
    groupName?: string;
    groupId?: number;
}

export class MstAccount implements IMstAccount {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public yearOpenBalance?: number,
        public yearOpenBalanceType?: BalanceType,
        public yearCloseBalance?: number,
        public reservedFlag?: ReservedFlag,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public depreciationRate?: number,
        public depreciationType?: DepreciationType,
        public groupName?: string,
        public groupId?: number
    ) {}
}
