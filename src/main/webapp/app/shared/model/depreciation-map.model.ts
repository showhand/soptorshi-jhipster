import { Moment } from 'moment';

export interface IDepreciationMap {
    id?: number;
    depreciationAccountId?: number;
    depreciationAccountName?: string;
    accountId?: number;
    accountName?: string;
    createdBy?: string;
    createdOn?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class DepreciationMap implements IDepreciationMap {
    constructor(
        public id?: number,
        public depreciationAccountId?: number,
        public depreciationAccountName?: string,
        public accountId?: number,
        public accountName?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
