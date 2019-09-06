import { Moment } from 'moment';

export const enum ReservedFlag {
    RESERVED = 'RESERVED',
    NOT_RESERVED = 'NOT_RESERVED'
}

export interface IMstAccount {
    id?: number;
    code?: string;
    name?: string;
    reservedFlag?: ReservedFlag;
    modifiedBy?: string;
    modifiedOn?: Moment;
    groupName?: string;
    groupId?: number;
}

export class MstAccount implements IMstAccount {
    constructor(
        public id?: number,
        public code?: string,
        public name?: string,
        public reservedFlag?: ReservedFlag,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public groupName?: string,
        public groupId?: number
    ) {}
}
